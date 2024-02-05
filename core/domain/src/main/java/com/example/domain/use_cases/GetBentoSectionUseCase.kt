package com.example.domain.use_cases

import com.example.data.models.BentoSection
import com.example.data.models.Offer
import com.example.data.repositories.RestaurantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBentoSectionUseCase @Inject constructor(private val restaurantsRepository: RestaurantsRepository) {
    suspend operator fun invoke(): Flow<List<BentoSection>> {
        return restaurantsRepository.getBentoSection()
    }
}