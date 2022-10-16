package com.lupa.suitgame2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SliderData(
    val description: String,
    val imgSlider: String
) : Parcelable
