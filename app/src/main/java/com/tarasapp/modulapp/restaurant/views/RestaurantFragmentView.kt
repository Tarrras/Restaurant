package com.tarasapp.modulapp.restaurant.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.tarasapp.modulapp.restaurant.models.Restaurant

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface RestaurantFragmentView: MvpView {
    fun showList(list: List<Restaurant>)
    fun showError(message: String)
}