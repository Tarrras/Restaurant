package com.tarasapp.modulapp.restaurant.models

data class Restaurant(
    val imageUrl: String = "",
    val longitude: Float= 0F,
    val latitude: Float=0F,
    val phone: String="",
    val street: String="",
    val houseL: Int=0,
    val rating: Float=0F,
    val workTime: String=""
)