package com.example.domain.use_cases

import com.example.data.models.RestaurantSection
import com.example.data.repositories.RestaurantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRestaurantMenusUseCase @Inject constructor(private val restaurantsRepository: RestaurantsRepository) {
    suspend operator fun invoke(restaurantId: String): Flow<List<RestaurantSection>> {
        return restaurantsRepository.getRestaurantMenus(restaurantId)
    }
}