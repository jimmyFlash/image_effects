package com.jimmy.imagefilters.utils

import android.graphics.Bitmap

object InvertFilter {
  var MASK = 0x00FFFFFF

  fun applyInvertFilter(source: Bitmap): Bitmap {
    // get image size
    val width = source.width
    val height = source.height
    val pixels = IntArray(width * height)
    // get pixel array from source
    source.getPixels(pixels, 0, width, 0, 0, width, height)

    for (y in pixels.indices) {
      pixels[y] =  pixels[y] xor MASK
    }
    // output bitmap
    val bitmapOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    bitmapOut.setPixels(pixels, 0, width, 0, 0, width, height)
    return bitmapOut
  }
}