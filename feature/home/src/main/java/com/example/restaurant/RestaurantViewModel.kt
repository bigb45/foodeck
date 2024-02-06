package com.example.restaurant

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.common.asResult
import com.example.common.log
import com.example.data.models.RestaurantSection
import com.example.domain.use_cases.GetAllRestaurantMenusUseCase
import com.example.domain.use_cases.GetRestaurantByIdUseCase
import com.example.restaurant.navigation.restaurantIdArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRestaurantInfo: GetRestaurantByIdUseCase,
    private val getAllMeals: GetAllRestaurantMenusUseCase,
) : ViewModel() {
    val restaurantId: String =
        URLDecoder.decode(savedStateHandle[restaurantIdArgument], Charsets.UTF_8.name())

    init {
        log("navigating to $restaurantId")
    }

    private val _restaurantState =
        MutableStateFlow<RestaurantState<List<RestaurantSection>>>(RestaurantState.Loading)
    val restaurantMenus: StateFlow<RestaurantState<List<RestaurantSection>>> =
        _restaurantState.asStateFlow()

    fun fetchRestaurantDetails() {
        _restaurantState.value = RestaurantState.Loading
        viewModelScope.launch {
            val menuFlow = getAllMeals(restaurantId).asResult()
            val restaurantFlow = getRestaurantInfo(restaurantId).asResult()
            combine(menuFlow, restaurantFlow) { menuResult, restaurantResult ->
                when {

                    menuResult is Result.Error -> {
                        log(menuResult.exception?.message ?: "unknown")
                        _restaurantState.value = RestaurantState.Error
                    }

                    restaurantResult is Result.Error -> {
                        log(restaurantResult.exception?.message ?: "unknown")
                        _restaurantState.value = RestaurantState.Error
                    }

                    menuResult is Result.Loading || restaurantResult is Result.Loading -> {
                        _restaurantState.value = RestaurantState.Loading
                    }

                    menuResult is Result.Success && restaurantResult is Result.Success -> {
                        _restaurantState.value = RestaurantState.Success(
                            data = menuResult.data,
                            restaurantInfo = restaurantResult.data
                        )

                    }
                }

            }.collect{}

//                .collect { menuResult, restaurantResult ->
//                when(result) {
//                    is com.example.common.Result.Error -> {
//                        log(result.exception?.message?:"unknown")
//                        _restaurantState.value = RestaurantState.Error
//                    }
//
//                    com.example.common.Result.Loading -> {
//                        _restaurantState.value = RestaurantState.Loading
//                    }
//
//                    is com.example.common.Result.Success -> {
//                        _restaurantState.value = RestaurantState.Success(result.data)
//                    }
//                }
//            }
        }
    }


}