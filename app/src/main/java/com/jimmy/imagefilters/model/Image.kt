package com.jimmy.imagefilters.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val name: String,
    val url: String,
    val description: String,
    var effect : Int = 0) : Parcelable