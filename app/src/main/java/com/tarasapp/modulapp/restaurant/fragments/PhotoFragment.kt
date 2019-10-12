package com.tarasapp.modulapp.restaurant.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.adapters.photoPager.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_photo.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val photo1 = "photo1"
const val photo2 = "photo2"
const val photo3 = "photo3"


class PhotoFragment : Fragment() {
    private lateinit var viewPager: ViewPager
    private val mList:ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo, container, false)
        viewPager = view.viewpager_id
        arguments?.getString(photo1)?.let { mList.add(it) }
        arguments?.getString(photo2)?.let { mList.add(it) }
        arguments?.getString(photo3)?.let { mList.add(it) }
        return view
    }

    override fun onResume() {
        super.onResume()

        val pagerAdapter = context?.let { ViewPagerAdapter(it,mList) }
        viewPager.adapter = pagerAdapter
    }

}
