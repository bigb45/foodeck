package com.example.data.repositories

import com.example.data.models.Meal
import com.example.data.models.Offer
import com.example.data.models.Restaurant
import com.example.data.models.OptionsSectionDto
import kotlinx.coroutines.flow.Flow

interface RestaurantsRepository {
    suspend fun getRestaurants(): Flow<List<Restaurant>>
    suspend fun getRestaurantMeals(): Flow<List<Meal>>
    suspend fun getMealOptions(): Flow<List<OptionsSectionDto>>
    suspend fun getOffers(): Flow<List<Offer>>
}