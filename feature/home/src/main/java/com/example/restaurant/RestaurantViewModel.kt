package com.example.restaurant

import android.util.Log.d
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.restaurant.navigation.restaurantIdArgument
import com.example.welcome.navigation.userIdArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val restaurantId: String =
        URLDecoder.decode(savedStateHandle[restaurantIdArgument], Charsets.UTF_8.name())

    init{
        d("error", restaurantId)
    }

}