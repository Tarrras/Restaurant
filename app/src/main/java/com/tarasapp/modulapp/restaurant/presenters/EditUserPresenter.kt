package com.tarasapp.modulapp.restaurant.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tarasapp.modulapp.restaurant.database.Firebase
import com.tarasapp.modulapp.restaurant.models.User
import com.tarasapp.modulapp.restaurant.views.EditUserView


@InjectViewState
class EditUserPresenter: MvpPresenter<EditUserView>() {

    fun showUser(){
        val database = Firebase.getInstance().getReference("users")
        val userId = Firebase.getAuthInstance().currentUser?.uid

        val childEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children) {
                    val comment = data.getValue(User::class.java)
                    if (comment != null) {
                        if(comment.id == userId){
                            comment.let { viewState.showUser(it) }
                        }
                    }
                }
            }
        }
        database.addValueEventListener(childEventListener)
    }

    fun updateUser(user: User){
        val allbase = Firebase.getInstance().reference
        val database = Firebase.getInstance().getReference("users")
        val userId = Firebase.getAuthInstance().currentUser?.uid
        val childUpdates = HashMap<String, Any>()

        val childEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children) {
                    val comment = data.getValue(User::class.java)
                    if (comment != null) {
                        if(comment.id == userId){
                            childUpdates["/users/${data.key}"] = user
                            allbase.updateChildren(childUpdates)
                            viewState.updateUser()
                        }
                    }
                }
            }
        }
        database.addValueEventListener(childEventListener)
    }
}