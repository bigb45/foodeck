package com.example.data.repositories

import com.example.data.models.RestaurantDto
import com.example.data.models.RestaurantModel
import kotlinx.coroutines.flow.Flow

interface RestaurantsRepository {
    suspend fun getRestaurants(): Flow<List<RestaurantDto>>
}