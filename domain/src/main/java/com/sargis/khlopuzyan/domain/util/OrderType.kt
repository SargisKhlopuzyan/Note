package com.sargis.khlopuzyan.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}