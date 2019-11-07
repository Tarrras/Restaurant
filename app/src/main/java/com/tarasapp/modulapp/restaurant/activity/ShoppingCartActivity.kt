package com.tarasapp.modulapp.restaurant.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.adapters.DishCartListAdapter
import com.tarasapp.modulapp.restaurant.models.Dish
import com.tarasapp.modulapp.restaurant.presenters.ShoppingCartActivityPresenter
import com.tarasapp.modulapp.restaurant.views.ShoppingCartActivityView
import kotlinx.android.synthetic.main.activity_shopping_kart.*

class ShoppingCartActivity : MvpAppCompatActivity(), ShoppingCartActivityView {
    override fun deleteItems() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var recyclerView: RecyclerView
    lateinit var dishListAdapter: DishCartListAdapter
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
        dishListAdapter = DishCartListAdapter{
//            val intent = Intent(applicationContext,ScrollingActivity::class.java)
//            val bundle = Bundle()
//            bundle.putString(keyOfDish,it.key)
//            intent.putExtras(bundle)
//            startActivity(intent)
        }
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = dishListAdapter
        dishListAdapter.notifyDataSetChanged()

        dishListAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                if (dishListAdapter.itemCount == 0){
                    recyclerView.visibility = View.GONE
                    empty_list_tv.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun showList(mList: List<Dish>) {
        empty_list_tv.visibility = View.GONE
        dishListAdapter.setupList(mList)
        dishListAdapter.notifyDataSetChanged()
        booking.text = "Сделать заказ"
    }

    override fun showEmptyList() {
        recyclerView.visibility = View.GONE
        booking.isClickable = false
    }

}
