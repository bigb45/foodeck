package com.example.data.repositories

import com.example.data.entities.CartItem
import com.example.data.entities.OrderSelection
import com.example.data.models.BentoSection
import com.example.data.models.CartItemDto
import com.example.data.models.Offer
import com.example.data.models.Restaurant
import com.example.data.models.OptionsSectionDto
import com.example.data.models.Order
import com.example.data.models.RestaurantMenu
import kotlinx.coroutines.flow.Flow

interface RestaurantsRepository {
    suspend fun getRestaurants(): Flow<List<Restaurant>>
    suspend fun getRestaurantMeals(restaurantId: String): Flow<List<RestaurantMenu>>
    suspend fun getMealOptions(restaurantId: String, menuId: String): Flow<List<OptionsSectionDto>>
    suspend fun getOffers(): Flow<List<Offer>>
    suspend fun saveUserMenuSelections(selection: OrderSelection): Boolean
    suspend fun saveCartItem(cartItem: CartItemDto)
    suspend fun getBentoSection(): Flow<List<BentoSection>>
}