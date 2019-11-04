package com.tarasapp.modulapp.restaurant.models

data class Dish(
    val imageUrl: HashMap<String,String> = HashMap(),
    val cuisine: HashMap<String,Boolean> = HashMap(),
    val calories: Int=0,
    val names: String="",
    val description: String="",
    val key: String="",
    val price: Int=0,
    val weight: Int=0
)