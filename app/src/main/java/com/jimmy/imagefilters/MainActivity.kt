package com.jimmy.imagefilters

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.jimmy.imagefilters.model.Image
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private var imageEffectPagerAdapter: ImageEffectPagerAdapter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.snow_title)))
    tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.sepia_name)))
    tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.brighten_name)))
    tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.darken_name)))
    tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.gray_name)))
    tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.invert_name)))

    imageEffectPagerAdapter = ImageEffectPagerAdapter(getImageEffectData(), supportFragmentManager)

    viewPager.adapter = imageEffectPagerAdapter
    viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

    tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
      override fun onTabSelected(tab: TabLayout.Tab) {
        viewPager.currentItem = tab.position
      }

      override fun onTabUnselected(tab: TabLayout.Tab) {}

      override fun onTabReselected(tab: TabLayout.Tab) {}
    })
  }

  private fun getImageEffectData(): List<Image> {
    val imageList = arrayListOf<Image>()
    imageList.add(Image(getString(R.string.snow_title), getString(R.string.snow_url),
        getString(R.string.snow_desc), imageList.size ))

    Log.e("getImageEffectData", "tutorialList ${imageList.size}")

    imageList.add(Image(getString(R.string.sepia_name), getString(R.string.sepia_url),
        getString(R.string.sepia_desc), imageList.size))

    Log.e("getImageEffectData", "tutorialList ${imageList.size}")

    imageList.add(Image(getString(R.string.brighten_name), getString(R.string.brighten_url),
        getString(R.string.brighten_desc), imageList.size ))
    Log.e("getImageEffectData", "tutorialList ${imageList.size}")

    imageList.add(Image(getString(R.string.darken_name), getString(R.string.darken_url),
        getString(R.string.darken_desc), imageList.size))

    Log.e("getImageEffectData", "tutorialList ${imageList.size}")
    imageList.add(Image(getString(R.string.gray_name), getString(R.string.gray_url),
        getString(R.string.gray_desc), imageList.size))
    Log.e("getImageEffectData", "tutorialList ${imageList.size}")

    imageList.add(Image(getString(R.string.invert_name), getString(R.string.invert_url),
        getString(R.string.invert_desc), imageList.size))
    Log.e("getImageEffectData", "tutorialList ${imageList.size}")

    return imageList
  }
}
