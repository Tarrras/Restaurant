package com.tarasapp.modulapp.restaurant.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.adapters.DishListAdapter.MyViewHolder
import com.tarasapp.modulapp.restaurant.models.Dish
import kotlinx.android.synthetic.main.dish_row_item.view.*


class DishListAdapter(val itemClick: (Dish)-> Unit): RecyclerView.Adapter<MyViewHolder>(), Filterable {


    private var mList:ArrayList<Dish> = ArrayList()
    private var mListFull:ArrayList<Dish> = ArrayList()

    fun setupList(list: List<Dish>){
        mList.addAll(list)
        mListFull = ArrayList(mList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dish_row_item,parent,false)
        return MyViewHolder(view,itemClick)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(mList[position])
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }

    val exampleFilter = object:Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<Dish>()
            if (constraint == null || constraint.isEmpty())
            {
                filteredList.addAll(mListFull)
            }
            else
            {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in mListFull)
                {
                    if (item.names.toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            mList.clear()
            if (results != null) {
                mList.addAll(results.values as ArrayList<Dish>)
            }
            notifyDataSetChanged()
        }
    }

    class MyViewHolder(itemView: View, val itemClick: (Dish)-> Unit): RecyclerView.ViewHolder(itemView) {
        val dishImg = itemView.dish_image_id
        val dishDesc = itemView.dish_desc
        val weightDesc = itemView.dish_weight_desc
        val priceDesc = itemView.dish_price
        fun bindItem(dish: Dish){
            with(dish){
                Picasso.with(itemView.context)
                    .load(dish.imageUrl["avatar"])
                    .error(R.drawable.ic_launcher_background)
                    .into(dishImg)
                weightDesc.text = dish.weight.toString()
                priceDesc.text = dish.price.toString()
                dishDesc.text = dish.names
                itemView.setOnClickListener{itemClick(this)}
            }
        }
    }
}


