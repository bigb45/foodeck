package com.example.menu_item

import com.example.data.repositories.Option
import com.example.data.repositories.Section

sealed interface MenuItemUiState {
    object Loading: MenuItemUiState
    data class Success(val sections: List<Section>): MenuItemUiState
    data class Error(val message: String?) : MenuItemUiState


}