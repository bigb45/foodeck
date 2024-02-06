package com.example.restaurant

import com.example.data.models.Restaurant


sealed interface RestaurantState <out T> {
    object Loading: RestaurantState <Nothing>
    object Error: RestaurantState <Nothing>
    data class Success<T>(val data: T,
        val restaurantInfo: Restaurant? = null
    ): RestaurantState <T>
}