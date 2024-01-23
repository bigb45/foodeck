package com.example.domain.use_cases

import com.example.data.models.UserDetailsModel
import com.example.data.repositories.AuthRepository
import com.example.data.repositories.RestaurantsRepository
import com.example.data.repositories.Section
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class GetMealOptionsUseCase @Inject constructor(private val repository: RestaurantsRepository) {
    suspend operator fun invoke(): Flow<List<Section>>{
        return repository.getMealOptions()
    }
}