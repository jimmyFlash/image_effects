package com.jimmy.imagefilters.utils

import android.graphics.Bitmap
import android.graphics.Color

object Sketch {

  private const val COLOR_MAX = 0xff
  private const val DEPTH = 20
  private const val INTENSITY_FACTOR = 120


  fun applySketchFilter(source: Bitmap): Bitmap {
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
    for (y in 0 until height) {
      for (x in 0 until width) {
        val pixel = source.getPixel(x, y)

        // get color
        r = Color.red(pixel)
        g = Color.green(pixel)
        b = Color.blue(pixel)

        val intensity = (r + g + b) / 3
        // applying new pixel value to newBitmap
        // condition for Sketch

        // applying new pixel value to newBitmap
        // condition for Sketch
        var newPixel = 0


        newPixel = when {
            intensity > INTENSITY_FACTOR -> {
              // apply white color
              Color.rgb(COLOR_MAX, COLOR_MAX, COLOR_MAX)
            }
            intensity > 100 -> {
              // apply grey color
              Color.rgb(150, 150, 150)
            }
            else -> {
              // apply black color
              Color.rgb(0, 0, 0)
            }
        }

        bitmapOut.setPixel(0, 0, newPixel)
      }
    }

    return bitmapOut
  }
}