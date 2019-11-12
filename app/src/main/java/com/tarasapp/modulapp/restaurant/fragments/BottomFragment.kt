package com.tarasapp.modulapp.restaurant.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.activity.MapsActivity
import kotlinx.android.synthetic.main.fragment_bottom.*


const val rest_address = "rest_address"
const val rest_phone = "rest_phone"
const val rest_time = "rest_time"
const val rest_long = "rest_longitude"
const val rest_lat = "rest_latitude"

class BottomFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom, container, false)
    }

    override fun onResume() {
        super.onResume()
        address_tv_fragment.text = arguments?.getString(rest_address)
        time_tv_fragment.text = arguments?.getString(rest_time)
        phone_tv_fragment.text = arguments?.getString(rest_phone)
        open_map_tv.setOnClickListener {
            val intent = Intent(context,MapsActivity::class.java)
            startActivity(intent)
        }
    }
}
