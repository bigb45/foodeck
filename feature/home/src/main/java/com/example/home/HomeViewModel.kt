package com.example.home

import android.util.Log.d
import androidx.lifecycle.ViewModel
import com.example.domain.use_cases.GetAllRestaurantsUseCase
import com.example.home.navigation.HomeScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.common.asResultList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
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
        viewModelScope.launch{
            getRestaurants()
                .asResultList().collect{
                        result ->
                    when(result){
                        is Result.Error -> {
//                            change to error state
                            _state.value = HomeScreenUiState.Loading

                        }
                        Result.Loading -> {
                            _state.value = HomeScreenUiState.Loading
                        }
                        is Result.Success -> {
                            d("error", result.toString())
                            _state.value = HomeScreenUiState.Success(result.data)

                        }
                    }
                }

        }
    }

}




