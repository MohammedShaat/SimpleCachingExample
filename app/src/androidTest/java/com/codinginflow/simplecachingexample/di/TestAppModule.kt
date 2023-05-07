package com.codinginflow.simplecachingexample.di

import android.app.Application
import androidx.room.Room
import com.codinginflow.simplecachingexample.data.database.RestaurantDao
import com.codinginflow.simplecachingexample.data.database.RestaurantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    @Named("test_db")
    fun provideRestaurantDatabase(app: Application): RestaurantDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            RestaurantDatabase::class.java,
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Named("test_dao")
    fun provideRestaurantDao(@Named("test_db") restaurantDatabase: RestaurantDatabase): RestaurantDao {
        return restaurantDatabase.restaurantDao()
    }
}