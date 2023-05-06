package com.codinginflow.simplecachingexample.ui.restaurants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.codinginflow.simplecachingexample.R
import com.codinginflow.simplecachingexample.data.domain.Restaurant
import com.codinginflow.simplecachingexample.databinding.RestaurantItemBinding

class RestaurantAdapter :
    ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewModel>(RestaurantDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewModel {
        val binding =
            RestaurantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewModel(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewModel, position: Int) {
        holder.bind(getItem(position))
    }

    class RestaurantViewModel(private val binding: RestaurantItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant) {
            binding.apply {
                Glide.with(itemView)
                    .load(restaurant.logo)
                    .apply(
                        RequestOptions().placeholder(R.drawable.image_loading)
                            .error(R.drawable.image_failed)
                    )
                    .into(restaurantImage)

                restaurantName.text = restaurant.name
                restaurantType.text = restaurant.type
                restaurantAddress.text = restaurant.address
            }
        }

    }

    object RestaurantDiffUtil : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }
    }

}