package com.tarasapp.modulapp.restaurant.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Firebase {
    companion object{
        fun getInstance(): FirebaseDatabase{
            return FirebaseDatabase.getInstance()
        }

        fun getAuthInstance():FirebaseAuth{
            return FirebaseAuth.getInstance()
        }
    }
}