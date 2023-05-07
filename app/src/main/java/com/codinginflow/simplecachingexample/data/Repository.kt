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

    /*private val _restaurantsRetryEvent = MutableSharedFlow<Boolean>()
    val restaurantsRetryEvent = _restaurantsRetryEvent.asSharedFlow()

    private var _restaurants = networkBoundResource(
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

    val restaurants: Flow<Resource<List<Restaurant>>>
        get() = _restaurants

    private fun getFromNetworkBoundResource() = networkBoundResource(
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

    suspend fun retry() {
        Log.i(TAG, "retry: called")
        _restaurants = networkBoundResource(
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
    }*/

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
