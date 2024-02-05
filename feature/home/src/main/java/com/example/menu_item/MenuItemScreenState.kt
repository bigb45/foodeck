package com.example.menu_item

import com.example.data.models.MenuItem
import com.example.data.models.Option
import com.example.data.models.OptionsSectionDto

data class MenuItemScreenState(
    val optionsState: OptionsState = OptionsState.Loading,
    val menuInfo: MenuItem? = null,
    val sections: List<OptionsSectionDto> = emptyList(),
    val selectedCheckboxOptions: Map<String, List<Option>> = emptyMap(),
    val selectedRadioOption: Map<String, Option?> = emptyMap(),
    val quantity: Int = 1,
    val instructions: String = "",
    val initialMenuPrice: Float,
    val totalPrice: Float = initialMenuPrice,
    val unselectedSection: String = "",
    val unselectedSectionIndex: Int? = null,
    val trigger: Boolean = false,

    )

