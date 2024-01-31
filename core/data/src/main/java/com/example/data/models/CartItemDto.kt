package com.example.data.models

import com.example.data.entities.CartItem

data class CartItemDto(
    val id: String,
    val menuId: String,
    val orderQuantity: Int,
    val customInstructions: String?,
)

fun CartItemDto.toEntity(): CartItem{

    return CartItem(
        id = this.id,
        menuId = this.menuId,
        quantity = this.orderQuantity,
        customInstructions = this.customInstructions
    )
}

fun CartItem.toDto(): CartItemDto{
    return CartItemDto(
        this.id,
        this.menuId,
        this.quantity,
        this.customInstructions
    )
}