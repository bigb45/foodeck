package com.example.menu_item

import com.example.data.repositories.Section

sealed interface OptionsState {
    object Loading: OptionsState
    data class Success(
        val sections: List<Section>,
        ): OptionsState
    data class Error(val message: String?) : OptionsState


}