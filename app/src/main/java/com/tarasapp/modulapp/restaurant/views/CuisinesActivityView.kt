package com.tarasapp.modulapp.restaurant.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.tarasapp.modulapp.restaurant.models.Cuisine
import com.tarasapp.modulapp.restaurant.models.Dish

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface CuisinesActivityView: MvpView {
    fun showList(cuisinesList: List<Cuisine>)
    fun showError(message: String)
}