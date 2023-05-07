package com.codinginflow.simplecachingexample.util

import com.codinginflow.simplecachingexample.data.database.DatabaseRestaurant
import com.codinginflow.simplecachingexample.data.domain.Restaurant
import com.codinginflow.simplecachingexample.data.network.NetworkRestaurant

fun List<NetworkRestaurant>.fromNetworkToDomainModels(): List<Restaurant> {
    return this.map { networkRestaurant ->
        networkRestaurant.asDomainModel()
    }
}

fun List<NetworkRestaurant>.fromNetworkToDatabaseModels(): List<DatabaseRestaurant> {
    return this.map { networkRestaurant ->
        networkRestaurant.asDatabaseModel()
    }
}

fun List<DatabaseRestaurant>.fromDatabaseToDomainModels(): List<Restaurant> {
    return this.map { databaseRestaurant ->
        databaseRestaurant.asDomainModel()
    }
}