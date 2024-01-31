package com.example.data.models

import com.example.data.entities.OrderSelection

data class Order(
    val menuId: String,
    val quantity: Int,
    val specialInstructions: String,
    val menuName: String,
    val totalPrice: Float,
    val customizations: List<OrderSelectionDto>
//    TODO: add extra information that will be needed later like address maybe
)

data class CustomizationList(
    var customizations: List<OrderSelectionDto>
)

data class OrderSelectionDto(
    val cartItemId: String,
    val menuId: String,
    val sectionId: String,
    val selectionId: String,
    val restaurantId: String,
)

fun OrderSelectionDto.toEntity(): OrderSelection{
    return OrderSelection(
        cartItemId = this.cartItemId,
        menuId = this.menuId,
        selectionId = this.selectionId,
        sectionId = this.sectionId,
        restaurantId = this.restaurantId,
    )
}

fun OrderSelection.toDto(): OrderSelectionDto{
    return OrderSelectionDto(
        cartItemId = this.cartItemId,
        sectionId = this.sectionId,
        selectionId = this.selectionId,
        restaurantId = this.restaurantId,
        menuId = this.menuId,

    )
}