package com.jimmy.imagefilters.utils

import android.graphics.Bitmap
import android.graphics.Color

object GrayFilter {
    val red = 0.33
    val green = 0.59
    val blue = 0.11

    fun applyGrayFilter(source: Bitmap): Bitmap {


        val width = source.width
        val height = source.height

        // output bitmap
        val bitmapOut = Bitmap.createBitmap(width, height, source.config)
        // iteration through pixels
        for (y in 0 until height) {
            for (x in 0 until width) {

                val p: Int = source.getPixel(x, y)
                var r: Int = Color.red(p)
                var g: Int = Color.green(p)
                var b: Int = Color.blue(p)

                val intensity = (r + g + b) / 3
                r = intensity
                g = intensity
                b = intensity

              /*
                r *= red.toInt()
                g *= green.toInt()
                b *= blue.toInt()
                */

                bitmapOut.setPixel(x, y, Color.argb(Color.alpha(p), r, g, b))
            }
        }

        return bitmapOut
    }
}