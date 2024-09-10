package com.example.domain.use_cases

import com.example.data.models.BentoSectionData
import com.example.data.repositories.RestaurantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetBentoSectionsUseCase @Inject constructor(private val repository: RestaurantsRepository) {
    suspend operator fun invoke(): Flow<List<BentoSectionData>> {
        return repository.getBentoSections()
    }
}