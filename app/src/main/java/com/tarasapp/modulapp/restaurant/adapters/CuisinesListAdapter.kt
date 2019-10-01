package com.tarasapp.modulapp.restaurant.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.adapters.DishListAdapter.MyViewHolder
import com.tarasapp.modulapp.restaurant.models.Cuisine
import com.tarasapp.modulapp.restaurant.models.Dish
import kotlinx.android.synthetic.main.cuisine_row_item.view.*
import kotlinx.android.synthetic.main.dish_row_item.view.*

class CuisinesListAdapter(val itemClick: (Cuisine)-> Unit): RecyclerView.Adapter<CuisinesListAdapter.MyViewHolderTwo>() {
    private var mList:ArrayList<Cuisine> = ArrayList()

    fun setupList(list: List<Cuisine>){
        mList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderTwo {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cuisine_row_item,parent,false)
        return MyViewHolderTwo(view,itemClick)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolderTwo, position: Int) {
        holder.bindItem(mList[position])
    }

    class MyViewHolderTwo(itemView: View, val itemClick: (Cuisine)-> Unit): RecyclerView.ViewHolder(itemView) {
        val cuisineDesc = itemView.cuisine_row_desc
        fun bindItem(cuisine: Cuisine){
            with(cuisine){
                cuisineDesc.text = cuisine.name
                itemView.setOnClickListener{itemClick(this)}
            }
        }
    }
}


