package com.codinginflow.simplecachingexample.data.network

import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class RestaurantApiTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var api: RestaurantApi

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun getRestaurants_returnsListOfTwentyRestaurantObjects() = runBlocking {
        // WHEN call getRestaurants()
        val result = api.getRestaurants()

        // THEN the result contains 20 NetworkRestaurant objects
        assertThat(result, Matchers.hasSize(20))
        assertThat(result, Matchers.everyItem(instanceOf(NetworkRestaurant::class.java)))
    }
}