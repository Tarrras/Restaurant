package com.tarasapp.modulapp.restaurant.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tarasapp.modulapp.restaurant.database.Firebase
import com.tarasapp.modulapp.restaurant.models.Cuisine
import com.tarasapp.modulapp.restaurant.models.Dish
import com.tarasapp.modulapp.restaurant.views.CuisinesActivityView
import com.tarasapp.modulapp.restaurant.views.DishesActivityView

@InjectViewState
class CuisinesActivityPresenter: MvpPresenter<CuisinesActivityView>() {
    fun getListFromDatabase(){
        val database = Firebase.getInstance()
        val dishes = database.getReference("cuisines")
        val list: ArrayList<Cuisine> = ArrayList()

//        val lib = database.reference
//        val id =lib.child("dishes").push().key
//        val map = HashMap<String,Boolean>()
//        map["Японская кухня"] = true
//        val dish = Dish("https://www.gastronom.ru/binfiles/images/20150602/b0687f3b.jpg",
//            map,
//            150,
//            "Темпура",
//            "Темпура из овощей и морепродуктов")
//        id?.let { lib.child("dishes").child(it).setValue(dish) }

        val childEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                viewState.showError(p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children) {
                    val comment = data.getValue(Cuisine::class.java)
                    val elem = comment?.country?.let { Cuisine(country = it,dish = comment.dish,name = comment.name) }
                    elem?.let { list.add(it) }
                }
                viewState.showList(list)
            }
        }
        dishes.addValueEventListener(childEventListener)
    }
}