package com.codinginflow.simplecachingexample.data

import android.util.Log
import androidx.room.withTransaction
import com.codinginflow.simplecachingexample.data.database.RestaurantDatabase
import com.codinginflow.simplecachingexample.data.domain.Restaurant
import com.codinginflow.simplecachingexample.data.network.RestaurantApi
import com.codinginflow.simplecachingexample.util.Resource
import com.codinginflow.simplecachingexample.util.fromNetworkToDatabaseModels
import com.codinginflow.simplecachingexample.util.fromDatabaseToDomainModels
import com.codinginflow.simplecachingexample.util.networkBoundResource
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val TAG = "Repository"

class Repository @Inject constructor(
    private val api: RestaurantApi,
    private val db: RestaurantDatabase
) {

    private val dao = db.restaurantDao()

    fun getRestaurants() = networkBoundResource(
        dbQuery = {
            dao.getAllRestaurants().map { it.fromDatabaseToDomainModels() }
        },
        apiFetch = {
            api.getRestaurants()
        },
        saveApiFetch = { networkRestaurants ->
            db.withTransaction {
                dao.deleteAllRestaurants()
                dao.insertRestaurants(networkRestaurants.fromNetworkToDatabaseModels())
            }
        }
    )
}
