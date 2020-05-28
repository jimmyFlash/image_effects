package com.jimmy.imagefilters.utils

import android.graphics.Bitmap

object SepiaFilter {

  private const val COLOR_MAX = 0xff
  private const val DEPTH = 20


  fun applySepiaFilter(source: Bitmap): Bitmap {
    // get image size
    val width = source.width
    val height = source.height
    val pixels = IntArray(width * height)

    var r: Int
    var g: Int
    var b: Int
    // get pixel array from source
    source.getPixels(pixels, 0, width, 0, 0, width, height)

    // output bitmap
    val bitmapOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    for (y in pixels.indices) {
      val pixel = pixels[y]

      // get color
      r = pixel shr 16 and 0xFF
      g = pixel shr 8 and 0xFF
      b = pixel and 0xFF

      r =(r + g + b) / 3
      g = (r + g + b) / 3
      b = (r + g + b) / 3
      r += (DEPTH * 2)
      g += DEPTH

      if (r > COLOR_MAX) r = COLOR_MAX
      if (g > COLOR_MAX) g = COLOR_MAX

      pixels[y] = 0xFF shl 24 or (r shl 16) or (g shl 8) or b

/*
      r = Color.red(pixel)
      g = Color.green(pixel)
      b = Color.blue(pixel)
      val gry = (r + g + b) / 3
      r = gry
      g = gry
      b = gry
      r += (DEPTH * 2)
      g += DEPTH

      if(r > COLOR_MAX) {
        r = 255
      }
      if(g > COLOR_MAX) {
        g = 255
      }
      bitmapOut.setPixel(0, 0, Color.rgb(r, g, b))
*/
    }

    bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height)
    return bitmapOut
  }
}