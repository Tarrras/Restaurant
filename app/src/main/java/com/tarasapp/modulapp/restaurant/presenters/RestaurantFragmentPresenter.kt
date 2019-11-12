package com.tarasapp.modulapp.restaurant.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tarasapp.modulapp.restaurant.database.Firebase
import com.tarasapp.modulapp.restaurant.models.Restaurant
import com.tarasapp.modulapp.restaurant.views.RestaurantFragmentView

@InjectViewState
class RestaurantFragmentPresenter: MvpPresenter<RestaurantFragmentView>(){
    fun loadList(){
        val database = Firebase.getInstance()
        val dishes = database.getReference("restaurants")
        val list: ArrayList<Restaurant> = ArrayList()


        val childEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                viewState.showError(p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children) {
                    val comment = data.getValue(Restaurant::class.java)
                    val elem = comment?.longitude?.let { Restaurant(comment.imageUrl,it, comment.latitude, comment.phone, comment.street, comment.houseL, comment.rating, comment.workTime) }
                    elem?.let { list.add(it) }
                }
                viewState.showList(list)
            }
        }
        dishes.addValueEventListener(childEventListener)
    }
}