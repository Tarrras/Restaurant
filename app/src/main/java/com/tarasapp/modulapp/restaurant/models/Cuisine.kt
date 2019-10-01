package com.tarasapp.modulapp.restaurant.models

data class Cuisine(
    val country:String="",
    val dish: HashMap<String,Boolean> = HashMap(),
    val name:String=""
) {
}