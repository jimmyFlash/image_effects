package com.jimmy.imagefilters.utils

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.math.pow

object DarkFilter {

    fun applyDarkFilter(source: Bitmap): Bitmap {


        val width = source.width
        val height = source.height
        val valueContrast = 50.0

        // get contrast value

        // get contrast value
        val contrast = ((100 + valueContrast) / 100).pow(2.0)
        // output bitmap
        val bitmapOut = Bitmap.createBitmap(width, height, source.config)
        // iteration through pixels
        for (y in 0 until height) {
            for (x in 0 until width) {

                val p: Int = source.getPixel(x, y)
                var r: Int = Color.red(p)
                r = ((((((r / 255.0) - 0.5) * contrast) + 0.5) * 255.0).toInt())
                if(r < 0) r = 0
                else if(r > 255)  r = 255

                var g: Int = Color.green(p)
                g = ((((((g / 255.0) - 0.5) * contrast) + 0.5) * 255.0).toInt())
                if(g < 0) g = 0
                else if(g > 255)  g = 255

                var b: Int = Color.blue(p)
                b = ((((((b / 255.0) - 0.5) * contrast) + 0.5) * 255.0).toInt())
                if(b < 0) b = 0
                else if(b > 255)  b = 255

                val alpha: Int = Color.alpha(p)


                bitmapOut.setPixel(x, y, Color.argb(alpha, r, g, b))
            }
        }

        return bitmapOut
    }
}