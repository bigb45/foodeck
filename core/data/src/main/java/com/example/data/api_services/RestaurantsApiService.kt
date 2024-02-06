package com.example.data.api_services

import com.example.data.models.BentoSection
import com.example.data.models.Offer
import com.example.data.models.Menu
import com.example.data.models.MenuItem
import com.example.data.models.Restaurant
import com.example.data.models.RestaurantSection
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantsApiService {
    @GET("all")
    suspend fun getAllRestaurants(): Response<List<Restaurant>>
    @GET("offers")
    suspend fun getOffers(): Response<List<Offer>>
    @GET("bento_categories")
    suspend fun getBentoSections(): Response<List<BentoSection>>

    @GET("{restaurantId}/menu/all")
    suspend fun getAllMenusByRestaurantId(@Path("restaurantId") restaurantId: String): Response<List<RestaurantSection>>

//    TODO: combine the below two functions into one
    @GET("{restaurantId}/{menuId}/options")
    suspend fun getMenuOptions(@Path("restaurantId") restaurantId: String, @Path("menuId") menuId: String): Response<Menu>

    @GET("{restaurantId}/menu/{menuId}")
    suspend fun getMenuInfo(@Path("restaurantId") restaurantId: String, @Path("menuId") menuId: String): Response<MenuItem>

    @GET("{restaurantId}")
    suspend fun getRestaurantById(@Path("restaurantId") restaurantId: String): Response<Restaurant>
}