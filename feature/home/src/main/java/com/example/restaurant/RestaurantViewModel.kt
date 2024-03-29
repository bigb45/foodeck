package com.example.restaurant

import android.util.Log.d
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.asResult
import com.example.common.log
import com.example.data.models.RestaurantMenu
import com.example.domain.use_cases.GetAllRestaurantMealsUseCase
import com.example.menu_item.OptionsState
import com.example.restaurant.navigation.restaurantIdArgument
import com.example.welcome.navigation.userIdArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAllMeals: GetAllRestaurantMealsUseCase
): ViewModel() {
    private val restaurantId: String =
        URLDecoder.decode(savedStateHandle[restaurantIdArgument], Charsets.UTF_8.name())

    private val _restaurantState = MutableStateFlow<RestaurantState<List<RestaurantMenu>>>(RestaurantState.Loading)
    val restaurantMenus: StateFlow<RestaurantState<List<RestaurantMenu>>> = _restaurantState.asStateFlow()
    fun fetchRestaurantDetails(){
        _restaurantState.value = RestaurantState.Loading
        viewModelScope.launch {
            getAllMeals(restaurantId).asResult().collect { result ->
                when (result) {
                    is com.example.common.Result.Error -> {
                        log(result.exception?.message?:"unknown")
                        _restaurantState.value = RestaurantState.Error
                    }

                    com.example.common.Result.Loading -> {
                        _restaurantState.value = RestaurantState.Loading
                    }

                    is com.example.common.Result.Success -> {
                        log(result.data.toString())
                        _restaurantState.value = RestaurantState.Success(result.data)
                    }
                }
            }
        }
    }


}