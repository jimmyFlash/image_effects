package com.jimmy.imagefilters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.jimmy.imagefilters.model.Image
import com.jimmy.imagefilters.utils.*
import kotlinx.android.synthetic.main.fragment_tutorial.*
import kotlinx.coroutines.*
import java.net.URL

class ImageEffectFragment : Fragment() {


  companion object {

    const val EFFECT_KEY = "EFFECT"
    const val TIMEOUT_DELAY = 15000L

    fun newInstance(image: Image): ImageEffectFragment {
      val fragmentHome = ImageEffectFragment()
      val args = Bundle()
      args.putParcelable(EFFECT_KEY, image)
      fragmentHome.arguments = args
      return fragmentHome
    }
  }

  /*
    a Job represents a piece of work that needs to be done. Additionally,
    every Job can be cancelled, finishing its execution. Because of that,
    it has a lifecycle and can also have nested children.
    Coroutine builders like launch() and async() return jobs as a result.
   */
  private val parentJob = Job()

  private var effectId : Int = 0


  /**
   * a CoroutineExceptionHandler to log exceptions. Additionally, it creates a coroutine
   * on the main thread to show error messages on the UI.
   * You also log your exceptions in a separate coroutine, which will live with your app’s lifecycle.
   * This is useful if you need to log your exceptions to tools like Crashlytics.
   * Since GlobalScope won’t be destroyed with the UI, you can log exceptions in it,
   * so you don’t lose the logs.
   *
   * Remember, if you’re using async(), always try to call await() from a try-catch block or a
   * scope where you’ve installed a CoroutineExceptionHandler.
   */
  private val coroutineExceptionHandler: CoroutineExceptionHandler =
    CoroutineExceptionHandler { _, throwable ->
      coroutineScope.launch(Dispatchers.Main) {
        handleErrorsForUI(R.string.error_message)
      }

      GlobalScope.launch { println("Caught $throwable") }
    }

  private fun handleErrorsForUI(@StringRes errorMsg:Int){
    errorMessage.visibility = View.VISIBLE
    progressBar.visibility = View.INVISIBLE
    errorMessage.text = getString(errorMsg)
  }

  /*
     define the scope when the coroutine runs
     The plus() operator helps you create a Set of CoroutineContext elements,
     which you associate with the coroutines in a particular scope.
     The contexts and their elements are a set of rules each Kotlin Coroutine has to adhere to.

     This set of elements can have information about:

     Dispatchers: which dispatch coroutines in a particular thread pool and executor.
     CoroutineExceptionHandler: which let you handle thrown exceptions.
     Parent Job: which you can use to cancel all Kotlin Coroutines within the scope.
  */
  private val coroutineScope = CoroutineScope(Dispatchers.Main +
          parentJob + coroutineExceptionHandler)





  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val effect = arguments?.getParcelable(EFFECT_KEY) as Image
    val view = inflater.inflate(R.layout.fragment_tutorial, container, false)
    view.findViewById<TextView>(R.id.tutorialName).text = effect.name
    view.findViewById<TextView>(R.id.tutorialDesc).text = effect.description
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val tutorial = arguments?.getParcelable(EFFECT_KEY) as Image
    effectId = tutorial.effect

    if (Connectivity.isConnected(activity!!)) {
        /*
        launch a coroutine on the main thread.
        But the originalBitmap is computed in a worker thread pool,
        so it doesn’t freeze the UI. Once you call await(),
        it will suspend launch(), until the image value is returned.
        */
        coroutineScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            //val originalBitmap = getOriginalBitmapAsync(tutorial).await()
            //val snowFilterBitmap = loadSnowFilterAsync(originalBitmap).await()
            try {
               withTimeout(TIMEOUT_DELAY) {
                 val originalBitmap = getOriginalBitmapAsync(tutorial)

                 //1 applying the filter to a loaded image
                 val snowFilterBitmap = loadSnowFilterAsync(originalBitmap)

                 //2 passing it to loadImage(). this convert your async operations into natural, sequential, method calls.
                 loadImage(snowFilterBitmap)
               }
            } catch (e: TimeoutCancellationException ) {
              handleErrorsForUI(R.string.error_message_timeout)
            }
        }
    }else{
      handleErrorsForUI(R.string.error_not_connected)
    }
  }


  /**
   * mark the functions with suspend. This tells the caller it’s using a coroutine,
   * and it might take some time to finish
   */
  private suspend fun getOriginalBitmapAsync(image: Image): Bitmap =
    withContext(Dispatchers.IO) {
      URL(image.url).openStream().use {
        return@withContext BitmapFactory.decodeStream(it)
      }
    }

  private suspend fun loadSnowFilterAsync(originalBitmap: Bitmap): Bitmap =
    withContext(Dispatchers.Default) {

      when(effectId){
        1 ->   SepiaFilter.applySepiaFilter(originalBitmap)
        2 ->   BrightFilter.applyBrightFilter(originalBitmap)
        3 ->   DarkFilter.applyDarkFilter(originalBitmap)
        4 ->   GrayFilter.applyGrayFilter(originalBitmap)
        5 ->   InvertFilter.applyInvertFilter(originalBitmap)
        else ->  SnowFilter.applySnowEffect(originalBitmap)
      }

    }


  private fun loadImage(snowFilterBitmap: Bitmap){
    progressBar.visibility = View.GONE
    snowFilterImage?.setImageBitmap(snowFilterBitmap)
  }

  override fun onDestroy() {
    super.onDestroy()
    parentJob.cancel()
  }


  /* Working but using async is expensive for just calling in it once per method
   async() is better used if you have multiple requests.
   It’s really useful for parallelism, as you can run a few operations,
   without blocking or suspending, at the same time. Just create multiple async() blocks!

// 1 returns a Deferred Bitmap value. This emphasizes that the result may not be immediately available
 private fun getOriginalBitmapAsync(tutorial: Tutorial): Deferred<Bitmap> =
   // 2  async() to create a coroutine in an input-output optimized Dispatcher.
   // This will offload work from the main thread, to avoid freezing the UI.

   //Dispatchers.IO: networking or reading and writing from files.
   //In short – any input and output, as the name states
   coroutineScope.async(Dispatchers.IO) {
     // 3 Opens a connection stream from the image’s URL and uses it to create a Bitmap, finally returning it.
     URL(tutorial.url).openStream().use {
       return@async BitmapFactory.decodeStream(it)
     }
   }

 private fun loadSnowFilterAsync(originalBitmap: Bitmap): Deferred<Bitmap> =
   //Dispatchers.Default: CPU-intensive work, such as sorting large lists,
   //doing complex calculations and similar. A shared pool of threads on the JVM backs it.
   coroutineScope.async(Dispatchers.Default) {
     SnowFilter.applySnowEffect(originalBitmap)
   }
 */


}
