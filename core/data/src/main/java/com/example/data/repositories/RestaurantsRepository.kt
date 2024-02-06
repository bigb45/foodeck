package com.example.data.repositories

import com.example.data.entities.OrderSelection
import com.example.data.models.BentoSection
import com.example.data.models.CartItemDto
import com.example.data.models.MenuItem
import com.example.data.models.Offer
import com.example.data.models.Restaurant
import com.example.data.models.OptionsSectionDto
import com.example.data.models.RestaurantSection
import kotlinx.coroutines.flow.Flow

interface RestaurantsRepository {
    suspend fun getRestaurants(): Flow<List<Restaurant>>
    suspend fun getRestaurantMenus(restaurantId: String): Flow<List<RestaurantSection>>
    suspend fun getOffers(): Flow<List<Offer>>
    suspend fun saveUserMenuSelections(selection: OrderSelection): Boolean
    suspend fun saveCartItem(cartItem: CartItemDto)
    suspend fun getBentoSection(): Flow<List<BentoSection>>
    suspend fun getMenuOptions(restaurantId: String, menuId: String): Flow<List<OptionsSectionDto>>
    suspend fun getMenuInfo(restaurantId: String, menuId: String): Flow<MenuItem>
    suspend fun getRestaurantInfo(restaurantId: String): Flow<Restaurant>
}