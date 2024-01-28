package com.example.data.models

import com.google.gson.annotations.SerializedName

data class Option(
//    TODO: move this into the models folder
    @SerializedName("id") val id: String,
    @SerializedName("option") val optionName: String,
    @SerializedName("price") val price: Float,
)