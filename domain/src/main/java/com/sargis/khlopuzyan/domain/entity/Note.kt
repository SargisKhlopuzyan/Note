package com.sargis.khlopuzyan.domain.entity

import android.graphics.Color

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
) {
    companion object Colors {
        val RedOrange = Color.argb(255, 255, 83, 73)
        val LightGreen = Color.argb(255, 144, 245, 100)
        val Violet = Color.argb(255, 146, 100, 245)
        val BabyBlue = Color.argb(255, 100, 206, 245)
        val RedPink = Color.argb(255, 245, 100, 144)
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}