package com.tarasapp.modulapp.restaurant.models

import java.util.*
import kotlin.collections.HashMap

data class Order(
    val totalPrice: Int = 0,
    val userName: String = "",
    val userEmail: String = "",
    val userPhone: String = "",
    val delivery: String = "",
    val userStreet: String = "",
    val userHouse: String = "",
    val userFlat: String = "",
    var dishes: HashMap<String,Int> = HashMap(),
    val date: Date = Date()
)