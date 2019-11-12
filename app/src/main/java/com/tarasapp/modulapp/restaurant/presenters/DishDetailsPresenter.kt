package com.tarasapp.modulapp.restaurant.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tarasapp.modulapp.restaurant.database.Firebase
import com.tarasapp.modulapp.restaurant.models.Dish
import com.tarasapp.modulapp.restaurant.models.UserCart
import com.tarasapp.modulapp.restaurant.views.DishDetailsView

@InjectViewState
class DishDetailsPresenter : MvpPresenter<DishDetailsView>() {
    fun showDishFromDatabase(keyOfDish: String) {
        val database = Firebase.getInstance().getReference("dishes")
        database.child(keyOfDish).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.getValue(Dish::class.java)?.let { viewState.showDish(it) }
            }
        })
    }

    fun addItemToBase(keyOfDish: String){
        val database = Firebase.getInstance().getReference("dishes")
        //var dish: Dish
        val userId = Firebase.getAuthInstance().currentUser?.uid

        val lib = Firebase.getInstance().reference
        val id =lib.child("cart").push().key
        database.child(keyOfDish).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val dishName = p0.key
                val cart = userId?.let { dishName?.let { it1 -> UserCart(1, it, it1) } }
                id?.let { lib.child("cart").child(it).setValue(cart) }
            }

        })
        viewState.showMessage("Добавленно!")
    }
}