package com.example.data.models

import androidx.compose.ui.text.toUpperCase
import com.example.common.log
import com.google.gson.annotations.SerializedName
import java.util.Locale

data class OptionsSectionDto(
    @SerializedName("section_id") val id: String,
//    TODO: create a DTO and a MODEL and change the section type to the enum value
    @SerializedName("sectionType") val sectionType: String,
    @SerializedName("section_name") val sectionTitle: String,
    @SerializedName("Option") val options: List<Option>,
    @SerializedName("required") val required: Boolean,
    @SerializedName("currency") val currency: String? = "$",

    )

fun OptionsSectionDto.toSectionData(): SectionData{

    return SectionData(
        id = this.id,
        title = this.sectionTitle,
        options = this.options,
        type = SectionType.valueOf(this.sectionType.uppercase(Locale.getDefault())),
        currency = this.currency,
        required = this.required,
    )
}


data class Menu(
    val sections: List<OptionsSectionDto>,
)