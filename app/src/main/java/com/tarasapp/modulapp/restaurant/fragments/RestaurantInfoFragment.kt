package com.tarasapp.modulapp.restaurant.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.adapters.RestaurantListAdapter
import com.tarasapp.modulapp.restaurant.models.Restaurant
import com.tarasapp.modulapp.restaurant.presenters.RestaurantFragmentPresenter
import com.tarasapp.modulapp.restaurant.views.RestaurantFragmentView
import kotlinx.android.synthetic.main.fragment_restaurant_info.*


class RestaurantInfoFragment : MvpAppCompatFragment(), RestaurantFragmentView {

    lateinit var recyclerView: RecyclerView
    lateinit var restaurantListAdapter: RestaurantListAdapter

    @InjectPresenter
    lateinit var presenter: RestaurantFragmentPresenter

    override fun showList(list: List<Restaurant>) {
        restaurantListAdapter.setupList(list)
        restaurantListAdapter.notifyDataSetChanged()
    }

    override fun showError(message: String) {
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_info, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadList()
        recyclerView = restaurant_recycler
        restaurantListAdapter = RestaurantListAdapter {
            val fragment = BottomFragment()
            val bundle = Bundle()
            bundle.putString(rest_phone,it.phone)
            val address = it.street + ", " + it.houseL
            bundle.putString(rest_address,address)
            bundle.putFloat(rest_lat,it.latitude)
            bundle.putFloat(rest_long,it.longitude)
            bundle.putString(rest_time,it.workTime)
            fragment.arguments = bundle
            fragmentManager?.let { it1 -> fragment.show(it1,"example") }
        }
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = restaurantListAdapter
        restaurantListAdapter.notifyDataSetChanged()
    }
}
