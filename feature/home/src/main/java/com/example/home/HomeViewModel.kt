package com.example.home

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.ListAsResult
import com.example.common.Result
import com.example.data.models.OffersDto
import com.example.domain.use_cases.GetAllRestaurantsUseCase
import com.example.domain.use_cases.GetOffersUseCase
import com.example.home.navigation.HomeScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRestaurants: GetAllRestaurantsUseCase,
    private val getOffers: GetOffersUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<HomeScreenUiState> =
        MutableStateFlow(HomeScreenUiState.Loading)
    val uiState: StateFlow<HomeScreenUiState> = _state

    init {
//        loadRestaurants()
        loadOffers()
    }

    fun load() {
//        loadRestaurants()
        loadOffers()
    }

    private fun loadOffers() {
        viewModelScope.launch {
            val restaurantsFlow = getRestaurants().ListAsResult()
            val offersFlow = getOffers().ListAsResult()
            combine(restaurantsFlow, offersFlow){
                restaurantsResult, offersResult ->
                when {
                    restaurantsResult is Result.Error -> {
                        _state.value = HomeScreenUiState.Error(restaurantsResult.exception?.message)
                    }
                    offersResult is Result.Error -> {
                        _state.value = HomeScreenUiState.Error(offersResult.exception?.message)
                    }
                    restaurantsResult == Result.Loading || offersResult == Result.Loading -> {
                        _state.value = HomeScreenUiState.Loading
                    }
                    restaurantsResult is Result.Success && offersResult is Result.Success -> {
                        _state.value = HomeScreenUiState.Success(
                            restaurants = restaurantsResult.data,
                            offers = offersResult.data
                        )
                    }
                }
            }.collect{}
//            getOffers()
//                .ListAsResult().collect { result ->
//                    when (result) {
//                        is Result.Error -> {
//                            d("error", "${result.exception}")
////                            TODO: change to error state
//                            _state.value = HomeScreenUiState.Error(result.exception?.message)
//
//                        }
//
//                        Result.Loading -> {
//                            _state.value = HomeScreenUiState.Loading
//                        }
//
//                        is Result.Success -> {
//                            d("error", "${result.data}")
//                            _state.value = HomeScreenUiState.Success(
//                                offers = result.data,
//                                restaurants = emptyList()
//                            )
//                        }
//                    }
//                }
        }
    }

    private fun loadRestaurants() {
        viewModelScope.launch {
            getRestaurants()
                .ListAsResult().collect { result ->
                    when (result) {
                        is Result.Error -> {
//                            TODO: change to error state
                            d("error", "${result.exception}")
                            _state.value = HomeScreenUiState.Error(result.exception?.message)

                        }

                        Result.Loading -> {
                            _state.value = HomeScreenUiState.Loading
                        }

                        is Result.Success -> {
                            _state.value = HomeScreenUiState.Success(
                                restaurants = result.data,
                                offers = listOf()
                            )
                        }
                    }
                }
        }
    }

}




