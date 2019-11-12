package com.tarasapp.modulapp.restaurant.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.tarasapp.modulapp.restaurant.database.Firebase
import com.tarasapp.modulapp.restaurant.models.CartDish
import com.tarasapp.modulapp.restaurant.models.Dish
import com.tarasapp.modulapp.restaurant.models.UserCart
import com.tarasapp.modulapp.restaurant.views.ShoppingCartActivityView

@InjectViewState
class ShoppingCartActivityPresenter : MvpPresenter<ShoppingCartActivityView>() {
    fun getCartList() {
        val auth = Firebase.getAuthInstance().currentUser
        val base = Firebase.getInstance()
        val cart = base.getReference("cart")
        val list: HashMap<String, Int> = HashMap()

        val dishes = base.getReference("dishes")
        val dishList: ArrayList<CartDish> = ArrayList()

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
            }
        }
        cart.addValueEventListener(childEventListener)

        val childEventListener1 = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children) {
                    val comment = data.getValue(Dish::class.java)
                    if (comment != null) {
                        if (list.containsKey(data.key)) {
                            val elem = data.key?.let {
                                CartDish(Dish(
                                    comment.imageUrl, comment.cuisine,
                                    comment.calories, comment.names, comment.description,
                                    it, comment.price, comment.weight), list.getValue(it)
                                )
                            }
                            elem.let { it?.let { it1 -> dishList.add(it1) } }
                        }
                    }
                }
                if (dishList.isEmpty()) {
                    viewState.showEmptyList()
                } else viewState.showList(dishList)
            }
        }
        dishes.addValueEventListener(childEventListener1)
    }

    fun deleteItem(key: String, position: Int) {
        val database = Firebase.getInstance()
        val userId = Firebase.getAuthInstance().currentUser?.uid
        val dishes = database.getReference("cart")
        val list: ArrayList<CartDish> = ArrayList()


        val childEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children) {
                    val comment = data.getValue(UserCart::class.java)
                    if (comment != null) {
                        if (comment.userId == userId && comment.dishId == key) {
                            val dump = dishes.child(data.key!!)
                            dump.removeValue()
                        }
                    }
                }
                viewState.showList(list)
            }
        }

        dishes.addValueEventListener(childEventListener)
        viewState.deleteItems(position)
    }

    fun updateCount(count: Int, key: String, position: Int){
        var newNum = ""
        val allbase = Firebase.getInstance().reference
        val cart = Firebase.getInstance().getReference("cart")
        var userId = Firebase.getAuthInstance().currentUser?.uid
        val dishes = Firebase.getInstance().getReference("dishes")
        val childUpdates = HashMap<String, Any>()
        val myMap = HashMap<String, Int>()

        val childEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children) {
                    val comment = data.getValue(UserCart::class.java)
                    if (comment != null) {
                        if(comment.dishId == key && comment.userId == userId && comment.count!= count){
                           childUpdates["/cart/${data.key}/count"] = count
                            myMap[comment.dishId] = count
                            allbase.updateChildren(childUpdates)
                            viewState.updateList(position, count ,key)
                            userId = null
                        }
                    }
                }
            }
        }
        cart.addValueEventListener(childEventListener)

//        val childEventListener1 = object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                for (data in p0.children) {
//                    val comment = data.getValue(Dish::class.java)
//                    if (comment != null) {
//                        if (myMap.containsKey(data.key)) {
//                            val elem = data.key?.let {
//                                CartDish(Dish(
//                                    comment.imageUrl, comment.cuisine,
//                                    comment.calories, comment.names, comment.description,
//                                    it, comment.price, comment.weight), myMap.getValue(it)
//                                )
//                            }
//                            if (elem != null) {
//                                viewState.updateList(position, elem)
//                            }
//                        }
//                    }
//                }
//
//            }
//        }
//        dishes.addValueEventListener(childEventListener1)
    }
}