package com.codinginflow.simplecachingexample.data.network

import com.codinginflow.simplecachingexample.data.database.DatabaseRestaurant
import com.codinginflow.simplecachingexample.data.domain.Restaurant
import com.google.gson.annotations.SerializedName

data class NetworkRestaurant(
    val id: Int,
    val uid: String,
    val name: String,
    val type: String,
    val description: String,
    val review: String,
    val logo: String,
    @SerializedName(value = "phone_number") val phoneNumber: String,
    val address: String,
    val hours: RestaurantDays
) {

    fun asDatabaseModel(): DatabaseRestaurant {
        return DatabaseRestaurant(
            id = id,
            name = name,
            type = type,
            logo = logo,
            address = address,
            hours = hours
        )
    }

    fun asDomainModel(): Restaurant {
        return Restaurant(
            id = id,
            name = name,
            type = type,
            logo = logo,
            address = address,
        )
    }

}

data class RestaurantDays(
    val monday: RestaurantHours,
    val tuesday: RestaurantHours,
    val wednesday: RestaurantHours,
    val thursday: RestaurantHours,
    val friday: RestaurantHours,
    val saturday: RestaurantHours,
    val sunday: RestaurantHours,
)

data class RestaurantHours(
    @SerializedName("opens_at") val opensAt: String,
    @SerializedName("closes_at") val closesAt: String,
    @SerializedName("is_closed") val isClosed: Boolean,
)
