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
import com.tarasapp.modulapp.restaurant.adapters.DishCartListAdapter
import com.tarasapp.modulapp.restaurant.fragments.BottomCartFragment
import com.tarasapp.modulapp.restaurant.fragments.BottomSheetListener
import com.tarasapp.modulapp.restaurant.models.CartDish
import com.tarasapp.modulapp.restaurant.models.Dish
import com.tarasapp.modulapp.restaurant.presenters.ShoppingCartActivityPresenter
import com.tarasapp.modulapp.restaurant.views.ShoppingCartActivityView
import kotlinx.android.synthetic.main.activity_shopping_kart.*


class ShoppingCartActivity : MvpAppCompatActivity(), ShoppingCartActivityView, BottomSheetListener {

    override fun updateList(position: Int, count: Int, key: String) {
        dishListAdapter.mList.find { it.dish.key == key }?.count = count
        dishListAdapter.notifyItemChanged(position)
        allprice_tv.text="Общая цена: " + countPrice().toString() + " UAH"
    }

    override fun changeCount(count: Int, key: String, position: Int) {
        presenter.updateCount(count, key,position)
    }

    override fun deleteItems(position: Int) {
        dishListAdapter.deleteItem(position)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        finish()
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
        dishListAdapter = DishCartListAdapter ({ dish: Dish, i: Int ->
            presenter.deleteItem(dish.key , i)
        },{ cartDish: CartDish, i: Int ->
            val fragment = BottomCartFragment()
            val bundle = Bundle()
            bundle.putInt("count", cartDish.count)
            bundle.putString("key", cartDish.dish.key)
            bundle.putInt("position", i)
            fragment.arguments = bundle
            fragment.show(supportFragmentManager,"editfragment")
        })
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
                allprice_tv.text="Общая цена: " + countPrice().toString() + " UAH"
                if (dishListAdapter.itemCount == 0){
                    recyclerView.visibility = View.GONE
                    empty_list_tv.visibility = View.VISIBLE
                    booking.isClickable = false
                }
            }
        })

        booking.setOnClickListener{
            val intent = Intent(applicationContext, BookingActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("price",countPrice())
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    fun countPrice(): Int{
        var count = 0
        for(data in dishListAdapter.mList){
            count += data.count * data.dish.price
        }
        return count
    }

    override fun showList(mList: List<CartDish>) {
        empty_list_tv.visibility = View.GONE
        dishListAdapter.setupList(mList)
        dishListAdapter.notifyDataSetChanged()
        booking.text = "Сделать заказ"
    }

    override fun showEmptyList() {
//        booking.backgroundTintList = ContextCompat.getColorStateList(this, R.color.colorPrimaryDark)
        empty_list_tv.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        booking.isClickable = false
    }

}
