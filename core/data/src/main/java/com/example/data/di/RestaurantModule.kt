package com.example.data.di

import com.example.common.Constants
import com.example.data.api_services.RestaurantsApiService
import com.example.data.repositories.RestaurantsRepository
import com.example.data.repositories.RestaurantsRepositoryImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestaurantModule {

    @Provides
    @Singleton
    fun provideRestaurantsRepository(apiService: RestaurantsApiService): RestaurantsRepository{
        return RestaurantsRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideRestaurantsApiService(): RestaurantsApiService {
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Constants.baseUrl + Constants.restaurantsEndpoint).build().create(RestaurantsApiService::class.java)
    }
}