package com.tarasapp.modulapp.restaurant.models

import java.util.*

data class User(
    val fName: String="",
    val lName: String="",
    val phone: String = "",
    val date: Date= Date(),
    val avatarUrl: String = "",
    val city: String=""
)