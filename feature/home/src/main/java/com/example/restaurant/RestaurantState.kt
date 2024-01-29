package com.example.restaurant


sealed interface RestaurantState <out T> {
    object Loading: RestaurantState <Nothing>
    object Error: RestaurantState <Nothing>
    data class Success<T>(val data: T): RestaurantState <T>
}