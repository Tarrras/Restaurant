package com.tarasapp.modulapp.restaurant.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.models.Cuisine
import com.tarasapp.modulapp.restaurant.views.CuisinesActivityView


class CuisinesListFragment : MvpAppCompatFragment(), CuisinesActivityView {
    override fun showList(cuisinesList: List<Cuisine>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cuisines_list, container, false)
    }

}
