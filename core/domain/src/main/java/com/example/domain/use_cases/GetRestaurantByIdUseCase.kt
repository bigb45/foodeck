package com.example.domain.use_cases

import com.example.data.models.Restaurant
import com.example.data.repositories.RestaurantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRestaurantByIdUseCase @Inject constructor(private val restaurantsRepository: RestaurantsRepository){
    suspend operator fun invoke(restaurantId: String): Flow<Restaurant> {
        return restaurantsRepository.getRestaurantInfo(restaurantId)

    }
}