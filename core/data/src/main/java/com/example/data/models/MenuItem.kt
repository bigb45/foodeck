package com.example.data.models

import com.google.gson.annotations.SerializedName

data class MenuItem(
    @SerializedName("store_name")
    val storeName: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("item_id")
    val itemId: String,
    @SerializedName("item_name")
    val itemName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("cover_image_url")
    val coverImageUrl: String?,
)