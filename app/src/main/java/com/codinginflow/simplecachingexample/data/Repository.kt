package com.codinginflow.simplecachingexample.data

import androidx.room.withTransaction
import com.codinginflow.simplecachingexample.data.database.RestaurantDatabase
import com.codinginflow.simplecachingexample.data.network.RestaurantApi
import com.codinginflow.simplecachingexample.util.fromNetworkToDatabaseModels
import com.codinginflow.simplecachingexample.util.fromDatabaseToDomainModels
import com.codinginflow.simplecachingexample.util.networkBoundResource
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Repository @Inject constructor(
    api: RestaurantApi,
    db: RestaurantDatabase
) {

    private val dao = db.restaurantDao()

    val restaurants = networkBoundResource(
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
