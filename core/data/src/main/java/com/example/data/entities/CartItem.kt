package com.example.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.models.CustomizationList
import com.example.data.models.MenuCustomization
import com.example.data.models.Offer

@Entity
data class CartItem(
    @PrimaryKey val id: String,
    val orderId: String,
    val orderQuantity: Int,
    val menuId: String,
    val customInstructions: String?,
    val customizations: CustomizationList

)
