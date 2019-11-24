package com.tarasapp.modulapp.restaurant.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tarasapp.modulapp.restaurant.database.Firebase
import com.tarasapp.modulapp.restaurant.models.Order
import com.tarasapp.modulapp.restaurant.models.User
import com.tarasapp.modulapp.restaurant.models.UserCart
import com.tarasapp.modulapp.restaurant.views.BookingActivityView

@InjectViewState
class BookingActivityPresenter: MvpPresenter<BookingActivityView>() {
    fun loadUser(){
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

    fun makeOrder(order: Order){
        viewState.startLoading()

        val auth = Firebase.getAuthInstance().currentUser
        val base = Firebase.getInstance()
        val cart = base.getReference("cart")
        val list: HashMap<String, Int> = HashMap()

        val id =base.reference.child("active_orders").push().key

        android.os.Handler().postDelayed(
            {
                val childEventListener = object : ValueEventListener {

                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        for (data in p0.children) {
                            val comment = data.getValue(UserCart::class.java)
                            if (auth != null && comment != null) {
                                if (comment.userId == auth.uid) {
                                    list[comment.dishId] = comment.count
                                }
                            }
                        }
                        cart.removeEventListener(this)
                        order.dishes = list

                        id?.let { base.reference.child("active_orders").child(it).setValue(order) }
                        viewState.endLoading()
                        if(order.delivery == "Самовывоз"){
                            viewState.showPickUpDialog()
                        } else viewState.showCourierDialog()
                    }
                }
                cart.addValueEventListener(childEventListener)
            }, 5000
        )

    }
}