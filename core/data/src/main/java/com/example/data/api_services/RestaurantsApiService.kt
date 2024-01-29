package com.example.data.api_services

import com.example.data.models.Offer
import com.example.data.models.Menu
import com.example.data.models.Restaurant
import com.example.data.models.RestaurantMenu
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantsApiService {
    @GET("all")
    suspend fun getAllRestaurants(): Response<List<Restaurant>>

    @GET("{restaurantId}/menu/all")
    suspend fun getMealsByRestaurantId(@Path("restaurantId") restaurantId: String): Response<List<RestaurantMenu>>

    @GET("offers")
    suspend fun getOffers(): Response<List<Offer>>

    @GET("custom_meal")
    suspend fun getMealSections(): Response<Menu>
}