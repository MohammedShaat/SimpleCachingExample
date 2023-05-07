package com.codinginflow.simplecachingexample.ui.restaurants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.codinginflow.simplecachingexample.databinding.ActivityRestaurantBinding
import com.codinginflow.simplecachingexample.util.Resource
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

        viewModel.restaurants.observe(this) { resource ->
            restaurantAdapter.submitList(resource.data)

            binding.apply {
                progressBar.isVisible = resource is Resource.Loading && resource.data.isNullOrEmpty()
                textViewError.isVisible = resource is Resource.Error && resource.data.isNullOrEmpty()
                textViewError.text = resource.error?.localizedMessage
            }
        }
    }
}