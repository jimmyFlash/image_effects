package com.jimmy.imagefilters.utils

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.math.pow

object BrightFilter {

    fun applyBrightFilter(source: Bitmap): Bitmap {


        val width = source.width
        val height = source.height

        val valueContrast = 30.0
        val darkeningFactor = 0.1
        val intensityFactor = 0.1
        val contrast = ((100 + valueContrast) / 100).pow(2.0)
        // output bitmap
        val bitmapOut = Bitmap.createBitmap(width, height, source.config)

        // iteration through pixels
        for (y in 0 until height) {
            for (x in 0 until width) {

                val p: Int = source.getPixel(x, y)
                var r: Int = Color.red(p)
                r = ((((((r / 255.0) - darkeningFactor) * contrast) + intensityFactor) * 255.0).toInt())
                if(r < 0) r = 0
                else if(r > 255)  r = 255

                var g: Int = Color.green(p)
                g = ((((((g / 255.0) - darkeningFactor) * contrast) + intensityFactor) * 255.0).toInt())
                if(g < 0) g = 0
                else if(g > 255)  g = 255

                var b: Int = Color.blue(p)
                b = ((((((b / 255.0) - darkeningFactor) * contrast) + intensityFactor) * 255.0).toInt())
                if(b < 0) b = 0
                else if(b > 255)  b = 255

                val alpha: Int = Color.alpha(p)


                bitmapOut.setPixel(x, y, Color.argb(alpha, r, g, b))
            }
        }

        return bitmapOut
    }
}