package com.example.menu_item

import com.example.data.models.OptionsSectionDto

sealed interface OptionsState {
    object Loading: OptionsState
    data class Success(
        val sections: List<OptionsSectionDto>,
        ): OptionsState
    data class Error(val message: String?) : OptionsState


}