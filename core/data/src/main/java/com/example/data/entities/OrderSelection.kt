package com.example.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = ["cartItemId", "selectionId", "sectionId", "restaurantId"]
)
data class OrderSelection(
    @ColumnInfo(name = "cartItemId") val cartItemId: String,
    @ColumnInfo(name = "menuId") val menuId: String,
    @ColumnInfo(name = "sectionId") val sectionId: String,
    @ColumnInfo(name = "selectionId") val selectionId: String,
    @ColumnInfo(name = "restaurantId") val restaurantId: String,
)