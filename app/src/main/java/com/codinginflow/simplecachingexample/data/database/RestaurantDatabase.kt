package com.codinginflow.simplecachingexample.data.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurant_table")
    fun getAllRestaurants(): Flow<List<DatabaseRestaurant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurants(restaurants: List<DatabaseRestaurant>)

    @Query("DELETE FROM restaurant_table")
    suspend fun deleteAllRestaurants()
}


@Database(entities = [DatabaseRestaurant::class], version = 1)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao
}
