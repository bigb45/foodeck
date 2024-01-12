package com.example.data.api_services

import com.example.data.models.OffersModel
import com.example.data.models.RestaurantModel
import retrofit2.Response
import retrofit2.http.GET

interface RestaurantsApiService {
    @GET("all")
    suspend fun getAllRestaurants(): Response<List<RestaurantModel>>

    @GET("offers")
    suspend fun getOffers(): Response<List<OffersModel>>
}