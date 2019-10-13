package com.tarasapp.modulapp.restaurant.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.activity.DishesActivity
import com.tarasapp.modulapp.restaurant.adapters.CuisinesListAdapter
import com.tarasapp.modulapp.restaurant.models.Cuisine
import com.tarasapp.modulapp.restaurant.models.Dish
import com.tarasapp.modulapp.restaurant.presenters.CuisinesActivityPresenter
import com.tarasapp.modulapp.restaurant.views.CuisinesActivityView
import kotlinx.android.synthetic.main.fragment_cuisines_list.*


class CuisinesListFragment : MvpAppCompatFragment(), CuisinesActivityView {

    lateinit var recyclerView: RecyclerView
    lateinit var cuisinesListAdapter: CuisinesListAdapter
    val mList: ArrayList<Dish> = ArrayList()
    private lateinit var toolbar: Toolbar

    @InjectPresenter
    lateinit var presenter: CuisinesActivityPresenter

    override fun showList(cuisinesList: List<Cuisine>) {
        cuisinesListAdapter.setupList(cuisinesList)
        cuisinesListAdapter.notifyDataSetChanged()
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cuisines_list, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        // Inflate the layout for this fragment
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        presenter.getListFromDatabase()
        recyclerView = recycler_cuisines
        cuisinesListAdapter = CuisinesListAdapter {
            val intent = Intent(context, DishesActivity::class.java)
            val bundle = Bundle()
            bundle.putString("Bluda",it.name)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = cuisinesListAdapter
        cuisinesListAdapter.notifyDataSetChanged()
    }

}
