package com.codinginflow.simplecachingexample.ui.restaurants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.codinginflow.simplecachingexample.databinding.ActivityRestaurantBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantsActivity : AppCompatActivity() {

    private val viewModel: RestaurantsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantAdapter = RestaurantAdapter()
        binding.recyclerViewRestaurantsList.apply {
            setHasFixedSize(true)
            adapter = restaurantAdapter
        }

        viewModel.restaurants.observe(this) {
            restaurantAdapter.submitList(it)
        }
    }
}