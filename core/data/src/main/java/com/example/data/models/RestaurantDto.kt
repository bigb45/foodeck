package com.example.data.models

data class RestaurantDto(
    val restaurantId: String = "test",
    val restaurantName: String = "test",
    val restaurantAddress: String = "test",
    val timeToDeliver: String = "test",
    val phoneNumber: String = "test",
    val coverImageUrl: String? = "test",
    val restaurantRating: String = "test",
)