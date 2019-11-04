package com.tarasapp.modulapp.restaurant.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.adapters.DishListAdapter
import com.tarasapp.modulapp.restaurant.models.Dish
import com.tarasapp.modulapp.restaurant.presenters.ShoppingCartActivityPresenter
import com.tarasapp.modulapp.restaurant.views.ShoppingCartActivityView
import kotlinx.android.synthetic.main.activity_shopping_kart.*

class ShoppingCartActivity : MvpAppCompatActivity(), ShoppingCartActivityView {
    lateinit var recyclerView: RecyclerView
    lateinit var dishListAdapter: DishListAdapter
    val mList: ArrayList<Dish> = ArrayList()
    private lateinit var toolbar: Toolbar
    private lateinit var searchView: MaterialSearchView

    @InjectPresenter
    lateinit var presenter: ShoppingCartActivityPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_kart)
        searchView = search_view1
        searchView.setOnQueryTextListener(object: MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                dishListAdapter.filter.filter(newText)
                return false
            }
        })

        toolbar = findViewById(R.id.toolbar_cart)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        presenter.getCartList()
        recyclerView = recyclerViewCart
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

    override fun showList(mList: List<Dish>) {
        dishListAdapter.setupList(mList)
        dishListAdapter.notifyDataSetChanged()
        booking.text = "Сделать заказ"
    }

    override fun showEmptyList() {
        recyclerView.visibility = View.GONE
        booking.text = "Ваш список пуст"
    }

    override fun deleteItem(itemId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun goBooking() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
