package com.codinginflow.simplecachingexample.data.database

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class RestaurantDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_dao")
    lateinit var dao: RestaurantDao

    private val restaurants = listOf(
        DatabaseRestaurant(
            1,
            "title_1",
            "bar",
            "https://www.google.com",
            "address_1"
        ),
        DatabaseRestaurant(
            2,
            "title_2",
            "bar",
            "https://www.google.com",
            "address_2"
        ),
        DatabaseRestaurant(
            3,
            "title_3",
            "bar",
            "https://www.google.com",
            "address_3"
        )
    )

    @Before
    fun setupHiltRule() {
        hiltRule.inject()
    }

    @Test
    fun getAllRestaurants_3DatabaseRestaurant_returnsFlowOfListOf3DatabaseRestaurant() =
        runBlocking {
            // GIVEN a list of 3 DatabaseRestaurant objects
            dao.insertRestaurants(*restaurants.toTypedArray())

            // WHEN call getRestaurants
            val result = dao.getAllRestaurants()

            // THEN result contains 3 DatabaseRestaurant objects
            result.test {
                val list = awaitItem()
                assertThat(list, hasSize(3))
                assertThat(list, everyItem(instanceOf(DatabaseRestaurant::class.java)))
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun deleteAllRestaurant_deletesAllRestaurantsInDb() = runBlocking {
        // GIVEN a list of 3 DatabaseRestaurant objects
        dao.insertRestaurants(*restaurants.toTypedArray())

        // WHEN call deleteAllRestaurants
        dao.deleteAllRestaurants()

        // THEN all restaurants are deleted
        val result = dao.getAllRestaurants()
        result.test {
            val list = awaitItem()
            assertThat(list, hasSize(0))
            cancelAndConsumeRemainingEvents()
        }
    }
}