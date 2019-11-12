package com.tarasapp.modulapp.restaurant.views

import com.arellomobile.mvp.MvpView
import com.tarasapp.modulapp.restaurant.models.CartDish

interface ShoppingCartActivityView: MvpView {
    fun showList(mList: List<CartDish>)
    fun showEmptyList()
    fun updateList(position: Int, count: Int, key: String)
    fun deleteItems(position: Int)
}