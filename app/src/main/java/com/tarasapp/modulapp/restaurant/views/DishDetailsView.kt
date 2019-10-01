package com.tarasapp.modulapp.restaurant.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.tarasapp.modulapp.restaurant.models.Dish

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface DishDetailsView:MvpView {
    fun showDish(dish: Dish)
}