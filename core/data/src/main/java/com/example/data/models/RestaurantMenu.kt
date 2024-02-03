package com.example.data.models

import com.google.gson.annotations.SerializedName

data class RestaurantMenu(
    @SerializedName("section_id")
    val sectionId: String,
    @SerializedName("section_title")
    val sectionTitle: String,
    @SerializedName("StoreItem")
    val storeItems: List<StoreItem>,
)

data class StoreItem(
    @SerializedName("item_id")
    val itemId: String,
    @SerializedName("item_name")
    val itemName: String,
    val description: String,
    val price: Double,
    @SerializedName("cover_image_url")
    val coverImageUrl: String?,
)

//fun List<RestaurantMenu>.toCategoryList(): List<Category>{
//
//}
//
//fun List<StoreItem>.toMenuList(): List<MenuItem>{
//
//}