package com.sargis.khlopuzyan.domain.entity

import android.graphics.Color

data class Note(
    val id: Long? = null,
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
) {
    companion object {
        val RedOrange = Color.argb(255, 83, 73, 255)
        val LightGreen = Color.argb(144, 245, 100, 255)
        val Violet = Color.argb(146, 100, 245, 255)
        val BabyBlue = Color.argb(100, 206, 245, 255)
        val RedPink = Color.argb(245, 100, 144, 255)
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}