package com.codinginflow.simplecachingexample.ui.restaurants

import android.util.Log
import androidx.lifecycle.*
import com.codinginflow.simplecachingexample.data.Repository
import com.codinginflow.simplecachingexample.data.domain.Restaurant
import com.codinginflow.simplecachingexample.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "RestaurantsViewModel"

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    private var _restaurantsChannel = Channel<Boolean>()
    val restaurantsChannel = _restaurantsChannel.receiveAsFlow()

    val restaurants = restaurantsChannel.flatMapLatest {
        repo.getRestaurants()
    }.stateIn(viewModelScope, SharingStarted.Lazily, Resource.Loading(emptyList()))

    private val _restaurantsResourceEvent = MutableSharedFlow<RestaurantsEvent>()
    val restaurantsResourceEvent = _restaurantsResourceEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            _restaurantsChannel.send(true)
        }
    }

    fun onResourceReceived(resource: Resource<List<Restaurant>>) {
        resource ?: return
        when (resource) {
            is Resource.Error -> viewModelScope.launch {
                Log.i(TAG, "onResourceReceived: called")
                _restaurantsResourceEvent.emit(RestaurantsEvent.ShowFailedMessage)
            }
        }
    }

    fun onRetryClick() = viewModelScope.launch {
        Log.i(TAG, "onRetryClick: called")
        _restaurantsChannel.send(true)
    }

    sealed class RestaurantsEvent {
        object ShowFailedMessage : RestaurantsEvent()
    }
}