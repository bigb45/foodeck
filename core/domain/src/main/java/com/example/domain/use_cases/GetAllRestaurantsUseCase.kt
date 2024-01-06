package com.example.domain.use_cases

import com.example.data.models.RestaurantDto
import com.example.data.repositories.RestaurantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRestaurantsUseCase @Inject constructor(private val restaurantsRepository: RestaurantsRepository){

    suspend operator fun invoke(): Flow<List<RestaurantDto>> {
        return restaurantsRepository.getRestaurants()
    }
}