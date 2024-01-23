package com.example.data.api_services

import com.example.data.models.OffersModel
import com.example.data.models.RestaurantModel
import com.example.data.repositories.Menu
import com.example.data.repositories.Section
import retrofit2.Response
import retrofit2.http.GET

interface RestaurantsApiService {
    @GET("all")
    suspend fun getAllRestaurants(): Response<List<RestaurantModel>>

    @GET("offers")
    suspend fun getOffers(): Response<List<OffersModel>>

    @GET("custom_meal")
    suspend fun getMealSections(): Response<Menu>
}