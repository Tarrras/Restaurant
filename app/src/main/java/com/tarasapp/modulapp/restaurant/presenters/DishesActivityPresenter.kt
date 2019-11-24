package com.tarasapp.modulapp.restaurant.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tarasapp.modulapp.restaurant.database.Firebase
import com.tarasapp.modulapp.restaurant.models.Dish
import com.tarasapp.modulapp.restaurant.views.DishesActivityView

@InjectViewState
class DishesActivityPresenter : MvpPresenter<DishesActivityView>() {
    fun getListFromDatabase(cuisine: String) {
        val database = Firebase.getInstance()
        val dishes = database.getReference("dishes")
        val list: ArrayList<Dish> = ArrayList()


        val childEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children) {
                    val comment = data.getValue(Dish::class.java)
                    if(comment?.cuisine?.containsKey(cuisine)!!){
                        val elem = data.key?.let {
                            Dish(comment.imageUrl, comment.cuisine, comment.calories, comment.names,comment.description,
                                it,comment.price, comment.weight
                            )
                        }
                        elem.let { it?.let { it1 -> list.add(it1) } }
                    }
                }
                viewState.showList(list)
            }
        }
        dishes.addValueEventListener(childEventListener)
    }
}