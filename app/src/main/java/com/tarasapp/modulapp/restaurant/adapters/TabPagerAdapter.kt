package com.tarasapp.modulapp.restaurant.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class TabPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val lstFragment = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
       return lstFragment[position]
    }

    override fun getCount(): Int {
        return lstFragment.size
    }

    fun addFragment(fragment: Fragment){
        lstFragment.add(fragment)
    }
}