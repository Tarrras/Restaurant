package com.tarasapp.modulapp.restaurant.views

import com.arellomobile.mvp.MvpView
import com.tarasapp.modulapp.restaurant.models.Dish

interface ShoppingCartActivityView: MvpView {
    fun showList(mList: List<Dish>)
    fun showEmptyList()
    fun deleteItems()
}