package com.example.data.models

import com.google.gson.annotations.SerializedName

data class RestaurantModel(
    @SerializedName("store_id") val restaurantId:String,
    @SerializedName("store_name") val restaurantName:String,
    @SerializedName("address") val restaurantAddress:String,
    @SerializedName("delivery_time") val timeToDeliver:String,
    @SerializedName("phone") val phoneNumber:String,
    @SerializedName("cover_image_url") val coverImageUrl:String?,
    @SerializedName("rating") val restaurantRating :String,
){
    fun toDto(): RestaurantDto{
        return RestaurantDto(
            restaurantId, restaurantName, restaurantAddress, timeToDeliver, phoneNumber, coverImageUrl, restaurantRating
        )
    }
}
