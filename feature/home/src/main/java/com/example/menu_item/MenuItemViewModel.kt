package com.example.menu_item

import androidx.compose.runtime.IntState
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.menu_item.navigation.menuItemIdArgument
import com.example.restaurant.navigation.restaurantIdArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class MenuItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _counter = MutableStateFlow(1)
    val counter: StateFlow<Int> = _counter.asStateFlow()
    val menuItemId: String =
        URLDecoder.decode(savedStateHandle[menuItemIdArgument], Charsets.UTF_8.name())

    fun incrementCounter() {
        _counter.value += 1
    }
    fun decrementCounter() {
        if(_counter.value > 1){
            _counter.value -= 1
        }
    }

}