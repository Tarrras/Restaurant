package com.tarasapp.modulapp.restaurant.fragments


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.activity.DishesActivity
import com.tarasapp.modulapp.restaurant.activity.ShoppingCartActivity
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
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cuisines_list, container, false)
        toolbar = view.findViewById(R.id.toolbarS)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        // Inflate the layout for this fragment
        return view
    }

    override fun onResume() {
        super.onResume()
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
//        val decorator = DividerItemDecoration(context , layoutManager.orientation)
//        recyclerView.addItemDecoration(decorator)

        recyclerView.adapter = cuisinesListAdapter
        cuisinesListAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tothe_cart_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.to_cart_item -> {
                val intent = Intent(context,ShoppingCartActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
