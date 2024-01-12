package com.example.data.models

data class RestaurantDto(
    val restaurantId: String,
    val restaurantName: String,
    val restaurantAddress: String,
    val timeToDeliver: String,
    val phoneNumber: String,
    val coverImageUrl: String?,
    val restaurantRating: String,
)