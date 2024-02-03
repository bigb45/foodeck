package com.example.data.models

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("store_id") val restaurantId: String = "test",
    @SerializedName("store_name") val restaurantName: String = "test",
    @SerializedName("address") val restaurantAddress: String = "test",
    @SerializedName("delivery_time") val timeToDeliver: String = "test",
    @SerializedName("phone") val phoneNumber: String = "test",
    @SerializedName("cover_image_url") val coverImageUrl: String? = "test",
    @SerializedName("rating") val restaurantRating: String = "test",
)