package com.example.domain.use_cases

import com.example.data.entities.CartItem
import com.example.data.models.CartItemDto
import com.example.data.models.toEntity
import com.example.data.repositories.RestaurantsRepository
import javax.inject.Inject

class SaveCartItem @Inject constructor(private val repository: RestaurantsRepository) {

    suspend operator fun invoke(cartItem: CartItemDto){
        repository.saveCartItem(cartItem)
    }
}