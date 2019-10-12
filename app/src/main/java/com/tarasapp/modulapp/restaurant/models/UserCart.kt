package com.tarasapp.modulapp.restaurant.models

data class UserCart(
    val count: Int = 0,
    val userId: String = "",
    val dishId: String = ""
)