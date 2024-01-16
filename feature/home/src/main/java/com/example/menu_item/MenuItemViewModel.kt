package com.example.menu_item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.menu_item.navigation.menuItemIdArgument
import com.example.restaurant.navigation.restaurantIdArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class MenuItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val menuItemId: String =
        URLDecoder.decode(savedStateHandle[menuItemIdArgument], Charsets.UTF_8.name())

}