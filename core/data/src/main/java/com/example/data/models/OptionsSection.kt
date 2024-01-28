package com.example.data.models

import com.google.gson.annotations.SerializedName

data class OptionsSectionDto(
    @SerializedName("id") val id: String,
//    TODO: create a DTO and a MODEL and change the section type to the enum value
    @SerializedName("type") val sectionType: String,
    @SerializedName("title") val sectionTitle: String,
    @SerializedName("options") val options: List<Option>,
    @SerializedName("required") val required: Boolean,
    @SerializedName("currency") val currency: String,

    )

data class Menu(
    val sections: List<OptionsSectionDto>,
)