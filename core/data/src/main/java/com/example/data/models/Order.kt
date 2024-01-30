package com.example.data.models

data class Order(
    val menuId: String,
    val quantity: Int,
    val specialInstructions: String,
    val menuName: String,
    val totalPrice: Float,
    val customizations: List<MenuCustomization>
//    TODO: add extra information that will be needed later like address maybe
)

data class CustomizationList(
    var customizations: List<MenuCustomization>
)

data class MenuCustomization(
    val sectionId: String,
    val selectionId: String,
)