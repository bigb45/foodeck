package com.example.data.models

import com.google.gson.annotations.SerializedName

data class Option(
//    TODO: move this into the models folder
    @SerializedName("option_id") val id: String,
    @SerializedName("option_name") val optionName: String,
    @SerializedName("price") val price: Float,
)