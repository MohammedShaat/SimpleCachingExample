package com.codinginflow.simplecachingexample.ui.restaurants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.codinginflow.simplecachingexample.databinding.ActivityRestaurantBinding
import com.codinginflow.simplecachingexample.util.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "RestaurantsActivity"

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

        lifecycleScope.launchWhenStarted {
            viewModel.restaurantsResourceEvent.collectLatest { event ->
                when (event) {
                    is RestaurantsViewModel.RestaurantsEvent.ShowFailedMessage -> {
                        Snackbar.make(
                            binding.root,
                            "Failed\nCheck internet connection and then click retry",
                            Snackbar.LENGTH_INDEFINITE
                        ).setAction("Retry") {
                            viewModel.onRetryClick()
                        }
                            .show()
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.restaurants.collect {  resource ->
                Log.i(TAG, "viewModel.restaurants.observe: $resource")
                viewModel.onResourceReceived(resource)

                restaurantAdapter.submitList(resource.data)

                binding.apply {
                    progressBar.isVisible =
                        resource is Resource.Loading && resource.data.isNullOrEmpty()
                    textViewError.isVisible =
                        resource is Resource.Error && resource.data.isNullOrEmpty()
                    textViewError.text = resource.error?.localizedMessage
                }

            }
        }

    }
}