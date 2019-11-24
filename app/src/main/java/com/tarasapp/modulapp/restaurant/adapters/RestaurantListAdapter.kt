package com.tarasapp.modulapp.restaurant.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.models.Restaurant
import kotlinx.android.synthetic.main.restaurant_row_item.view.*

class RestaurantListAdapter(val itemClick: (Restaurant)-> Unit): RecyclerView.Adapter<RestaurantListAdapter.MyViewHolderRestaurant>() {
    private var mList:ArrayList<Restaurant> = ArrayList()

    fun setupList(list: List<Restaurant>){
        mList.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderRestaurant {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_row_item,parent,false)
        return MyViewHolderRestaurant(view,itemClick)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolderRestaurant, position: Int) {
        holder.bindItem(mList[position])
    }

    class MyViewHolderRestaurant(itemView: View, val itemClick: (Restaurant)-> Unit): RecyclerView.ViewHolder(itemView) {
        val address = itemView.address_tv
        val image = itemView.card_view_image_restaurant
        val ratingView = itemView.raiting_bar_id
        fun bindItem(cuisine: Restaurant){
            with(cuisine){
                Picasso.with(itemView.context).load(cuisine.imageUrl)
                    .error(R.drawable.ic_image_black_24dp).into(image)
                address.text = cuisine.street + ", " + cuisine.houseL.toString()
                ratingView.numStars = 5
                ratingView.rating = cuisine.rating
                itemView.setOnClickListener{itemClick(this)}
            }
        }
    }
}


