package com.codinginflow.simplecachingexample.di

import android.app.Application
import androidx.room.Room
import com.codinginflow.simplecachingexample.data.database.RestaurantDao
import com.codinginflow.simplecachingexample.data.database.RestaurantDatabase
import com.codinginflow.simplecachingexample.data.network.RestaurantApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RestaurantApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRestaurantApi(retrofit: Retrofit): RestaurantApi {
        return retrofit.create(RestaurantApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRestaurantDatabase(app: Application): RestaurantDatabase {
        return Room.databaseBuilder(
            app,
            RestaurantDatabase::class.java,
            "restaurant_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    fun provideRestaurantDao(restaurantDatabase: RestaurantDatabase): RestaurantDao {
        return restaurantDatabase.restaurantDao()
    }
}