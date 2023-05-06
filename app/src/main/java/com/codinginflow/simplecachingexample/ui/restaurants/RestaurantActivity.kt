package com.codinginflow.simplecachingexample.ui.restaurants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codinginflow.simplecachingexample.R

class RestaurantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        val restaurantAdapter = RestaurantAdapter()
    }
}