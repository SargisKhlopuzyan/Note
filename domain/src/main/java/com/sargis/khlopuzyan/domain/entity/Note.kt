package com.sargis.khlopuzyan.domain.entity

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
)