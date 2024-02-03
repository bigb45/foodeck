package com.example.menu_item

import com.example.data.models.Option

data class MenuOptionsActions(
    val onRadioSelectionChange: (String, Option) -> Unit,
    val onCheckboxSelectionChange: (String, Option, Boolean) -> Unit,
    val increment: () -> Unit,
    val decrement: () -> Unit,
    val onInstructionsChange: (String) -> Unit,
    val onAddToCartClick: () -> Unit
)