package com.codinginflow.simplecachingexample.util

import com.codinginflow.simplecachingexample.data.domain.Restaurant
import com.codinginflow.simplecachingexample.data.network.NetworkRestaurant

fun List<NetworkRestaurant>.asDomainModel(): List<Restaurant> {
    return this.map { networkRestaurant ->
        networkRestaurant.asDomainModel()
    }
}