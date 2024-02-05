package com.example.domain.use_cases

import com.example.data.repositories.RestaurantsRepository
import com.example.data.models.OptionsSectionDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMenuOptionsUseCase @Inject constructor(private val repository: RestaurantsRepository) {
    suspend operator fun invoke(restaurantId: String, menuId: String): Flow<List<OptionsSectionDto>>{
        return repository.getMenuOptions(restaurantId = restaurantId, menuId = menuId)
    }
}