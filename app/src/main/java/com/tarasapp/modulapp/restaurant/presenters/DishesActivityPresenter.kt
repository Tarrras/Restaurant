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
                                it
                            )
                        }
                        elem.let { it?.let { it1 -> list.add(it1) } }
                    }
                }
                viewState.showList(list)
//            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.key!!)
//                // A new comment has been added, add it to the displayed list
////                for (snap in dataSnapshot.value) {
//                    val comment = dataSnapshot.getValue(Dish::class.java)
//                    val elem = comment?.imageUrl?.let { Dish(it,comment.cuisine,comment.calories,comment.names) }
//                    elem?.let { list.add(it) }
//
//                //}
//
////                val comment = dataSnapshot.getValue(Dish::class.java)
////                val elem = comment?.calories?.let { Dish(comment.imageUrl, it, da) }
////                elem?.let { list.add(it) }
////                viewState.showList(list)
//
//                viewState.showList(list)
//            }
//
//            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                Log.d(TAG, "onChildChanged: ${dataSnapshot.key}")
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so displayed the changed comment.
//                val newComment = dataSnapshot.getValue(Dish::class.java)
//                val commentKey = dataSnapshot.key
//
//                // ...
//            }
//
//            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
//                Log.d(TAG, "onChildRemoved:" + dataSnapshot.key!!)
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so remove it.
//                val commentKey = dataSnapshot.key
//
//                // ...
//            }
//
//            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
//                Log.d(TAG, "onChildMoved:" + dataSnapshot.key!!)
//
//                // A comment has changed position, use the key to determine if we are
//                // displaying this comment and if so move it.
//                val movedComment = dataSnapshot.getValue(Dish::class.java)
//                val commentKey = dataSnapshot.key
//
//                // ...
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.w(TAG, "postComments:onCancelled", databaseError.toException())
//            }
            }
        }
        dishes.addValueEventListener(childEventListener)
    }
}