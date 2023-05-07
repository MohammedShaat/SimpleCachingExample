package com.codinginflow.simplecachingexample.ui.restaurants

import androidx.lifecycle.*
import com.codinginflow.simplecachingexample.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    repo: Repository
) : ViewModel(){

//    private val _restaurants = MutableLiveData<List<Restaurant>>()
//    val restaurants: LiveData<List<Restaurant>>
//        get() = _restaurants
//
//    init {
//        viewModelScope.launch {
//            delay(2_000L)
//            _restaurants.value = api.getRestaurants().asDomainModels()
//        }
//    }

    val restaurants = repo.restaurants.asLiveData()
}