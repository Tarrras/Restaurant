package com.tarasapp.modulapp.restaurant.models

import java.util.*

data class User(
    val avatarUrl: String = "",
    val city: String="",
    val date: Date= Date(),
    val email: String="",
    val fname: String="",
    val id: String="",
    val lname: String="",
    val phone: String = ""
)