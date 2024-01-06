package com.example.home

import android.util.Log.d
import androidx.lifecycle.ViewModel
import com.example.domain.use_cases.GetAllRestaurantsUseCase
import com.example.home.navigation.HomeScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getRestaurants: GetAllRestaurantsUseCase): ViewModel() {
    private val _state: MutableStateFlow<HomeScreenUiState> = MutableStateFlow(HomeScreenUiState.Loading)
    val uiState: StateFlow<HomeScreenUiState> = _state

    init {
        load()
    }
    fun load(){
        _state.value = HomeScreenUiState.Loading
        viewModelScope.launch{
            getRestaurants().collect{
                restaurants ->
                d("error", restaurants.toString())
                _state.value = HomeScreenUiState.Success(restaurants)
            }

        }
    }

}