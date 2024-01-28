package com.example.domain.use_cases

import com.example.data.repositories.RestaurantsRepository
import com.example.data.models.OptionsSectionDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMealOptionsUseCase @Inject constructor(private val repository: RestaurantsRepository) {
    suspend operator fun invoke(): Flow<List<OptionsSectionDto>>{
        return repository.getMealOptions()
    }
}