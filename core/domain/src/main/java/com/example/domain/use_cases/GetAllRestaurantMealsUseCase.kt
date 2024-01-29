package com.example.domain.use_cases

import com.example.data.models.RestaurantMenu
import com.example.data.repositories.RestaurantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRestaurantMealsUseCase @Inject constructor(private val restaurantsRepository: RestaurantsRepository) {
    suspend operator fun invoke(restaurantId: String): Flow<List<RestaurantMenu>> {
        return restaurantsRepository.getRestaurantMeals(restaurantId)
    }
}