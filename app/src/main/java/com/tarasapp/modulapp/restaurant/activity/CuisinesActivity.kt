package com.tarasapp.modulapp.restaurant.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.adapters.TabPagerAdapter
import com.tarasapp.modulapp.restaurant.fragments.CuisinesListFragment
import com.tarasapp.modulapp.restaurant.fragments.PersonalAreaFragment
import com.tarasapp.modulapp.restaurant.fragments.RestaurantInfoFragment
import kotlinx.android.synthetic.main.activity_cuisines.*


class CuisinesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuisines)

        val tabLayout = sliding_tabs
        val viewpagerS = viewpager

        val adapter = TabPagerAdapter(supportFragmentManager)
        adapter.addFragment(CuisinesListFragment())
        adapter.addFragment(RestaurantInfoFragment())
        adapter.addFragment(PersonalAreaFragment())

        viewpagerS.adapter = adapter
        tabLayout.setupWithViewPager(viewpagerS)

        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home_black_24dp)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_location_on_black_24dp)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_person_black_24dp)
    }

}
