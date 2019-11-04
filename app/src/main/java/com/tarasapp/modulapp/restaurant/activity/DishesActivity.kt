package com.tarasapp.modulapp.restaurant.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.adapters.DishListAdapter
import com.tarasapp.modulapp.restaurant.models.Dish
import com.tarasapp.modulapp.restaurant.presenters.DishesActivityPresenter
import com.tarasapp.modulapp.restaurant.views.DishesActivityView
import kotlinx.android.synthetic.main.activity_main.*

class DishesActivity : MvpAppCompatActivity(), DishesActivityView {


    lateinit var recyclerView: RecyclerView
    lateinit var dishListAdapter: DishListAdapter
    val mList: ArrayList<Dish> = ArrayList()
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: MaterialSearchView

    @InjectPresenter
    lateinit var presenter: DishesActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchView = search_view
        searchView.setOnQueryTextListener(object: MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                dishListAdapter.filter.filter(newText)
                return false
            }
        })

        toolbar = findViewById(R.id.toolbar1)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val cuisine =intent.extras.getString("Bluda")
        toolbar_title_dishes.text = cuisine
        supportActionBar?.setDisplayShowTitleEnabled(false)
        presenter.getListFromDatabase(cuisine)
        recyclerView = recycler
        dishListAdapter = DishListAdapter{
            val intent = Intent(applicationContext,ScrollingActivity::class.java)
            val bundle = Bundle()
            bundle.putString(keyOfDish,it.key)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = dishListAdapter
        dishListAdapter.notifyDataSetChanged()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun showList(dishList: List<Dish>) {
        dishListAdapter.setupList(dishList)
        dishListAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_dish_menu,menu)

        val searchItem = menu?.findItem(R.id.action_search)
        searchView.setMenuItem(searchItem)
        return true
    }


}
