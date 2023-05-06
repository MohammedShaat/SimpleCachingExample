package com.codinginflow.simplecachingexample.data.network

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApi {

    companion object {
        const val BASE_URL = "https://random-data-api.com/api/"
    }

    @GET("restaurant/random_restaurant")
    suspend fun getRestaurants(@Query("size") size: Int = 20): List<NetworkRestaurant>
}