package com.codinginflow.simplecachingexample.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codinginflow.simplecachingexample.data.domain.Restaurant
import com.codinginflow.simplecachingexample.data.network.RestaurantDays

@Entity(tableName = "restaurant_table")
data class DatabaseRestaurant(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val logo: String,
    val address: String,
    val hours: RestaurantDays
) {
    fun asDomainModel(): Restaurant {
        return Restaurant(
            id = id,
            name = name,
            type = type,
            logo = logo,
            address = address
        )
    }
}