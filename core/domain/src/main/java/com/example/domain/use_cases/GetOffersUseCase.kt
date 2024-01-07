package com.example.domain.use_cases

import com.example.data.models.OffersDto
import com.example.data.repositories.RestaurantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(private val restaurantsRepository: RestaurantsRepository) {
    suspend operator fun invoke(): Flow<List<OffersDto>> {
        return restaurantsRepository.getOffers()
    }
}