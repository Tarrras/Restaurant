package com.tarasapp.modulapp.restaurant.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.tarasapp.modulapp.restaurant.models.User

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface BookingActivityView: MvpView {
    fun startLoading()
    fun endLoading()
    fun showUser(user: User)
    fun showCourierDialog()
    fun showPickUpDialog()
}