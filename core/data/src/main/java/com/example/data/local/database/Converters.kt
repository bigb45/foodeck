package com.example.data.local.database

import androidx.room.TypeConverter
import com.example.data.models.CustomizationList
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun convertCustomizationListToJsonString(customizationList: CustomizationList): String{
        return Gson().toJson(customizationList)
    }

    @TypeConverter
    fun convertJsonStringToCustomizationList(jsonString: String): CustomizationList{
        return Gson().fromJson(jsonString, CustomizationList::class.java)
    }
}