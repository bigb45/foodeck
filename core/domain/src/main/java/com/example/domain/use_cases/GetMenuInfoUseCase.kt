package com.example.domain.use_cases

import com.example.data.models.MenuItem
import com.example.data.repositories.RestaurantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMenuInfoUseCase @Inject constructor(private val restaurantsRepository: RestaurantsRepository) {
    suspend operator fun invoke(restaurantId: String, menuId: String): Flow<MenuItem> {
        return restaurantsRepository.getMenuInfo(restaurantId = restaurantId, menuId = menuId)
    }
}