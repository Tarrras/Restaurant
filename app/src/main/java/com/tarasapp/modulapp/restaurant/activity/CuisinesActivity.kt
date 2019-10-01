package com.tarasapp.modulapp.restaurant.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.adapters.CuisinesListAdapter
import com.tarasapp.modulapp.restaurant.adapters.DishListAdapter
import com.tarasapp.modulapp.restaurant.models.Cuisine
import com.tarasapp.modulapp.restaurant.models.Dish
import com.tarasapp.modulapp.restaurant.presenters.CuisinesActivityPresenter
import com.tarasapp.modulapp.restaurant.presenters.DishesActivityPresenter
import com.tarasapp.modulapp.restaurant.views.CuisinesActivityView
import kotlinx.android.synthetic.main.activity_cuisines.*
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.widget.Toolbar


class CuisinesActivity : MvpAppCompatActivity(), CuisinesActivityView {
    override fun showError(message: String) {
        Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
    }

    lateinit var recyclerView: RecyclerView
    lateinit var cuisinesListAdapter: CuisinesListAdapter
    val mList: ArrayList<Dish> = ArrayList()
    private lateinit var toolbar: Toolbar

    @InjectPresenter
    lateinit var presenter: CuisinesActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuisines)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        presenter.getListFromDatabase()
        recyclerView = recycler_cuisines
        cuisinesListAdapter = CuisinesListAdapter {
            val intent = Intent(applicationContext,DishesActivity::class.java)
            val bundle = Bundle()
            bundle.putString("Bluda",it.name)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = cuisinesListAdapter
        cuisinesListAdapter.notifyDataSetChanged()
    }

    override fun showList(cuisinesList: List<Cuisine>) {
        cuisinesListAdapter.setupList(cuisinesList)
        cuisinesListAdapter.notifyDataSetChanged()
    }
}
