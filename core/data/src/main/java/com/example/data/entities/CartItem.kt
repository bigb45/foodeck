package com.example.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.data.models.CustomizationList


@Entity(tableName = "CartItem", primaryKeys = ["cartItemId"])
data class CartItem(
    @ColumnInfo(name = "cartItemId") val id: String,
    @ColumnInfo(name = "menuId") val menuId: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "customInstructions") val customInstructions: String?,
)
