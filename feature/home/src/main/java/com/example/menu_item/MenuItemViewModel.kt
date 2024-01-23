package com.example.menu_item

import android.util.Log.d
import androidx.compose.runtime.IntState
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.asResult
import com.example.data.repositories.Section
import com.example.domain.use_cases.GetMealOptionsUseCase
import com.example.menu_item.navigation.menuItemIdArgument
import com.example.restaurant.navigation.restaurantIdArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class MenuItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMealOptions: GetMealOptionsUseCase
): ViewModel() {
    private val _counter = MutableStateFlow(1)
//    TODO: change this to List<BaseSectionData>
    private val _options = MutableStateFlow<List<Section>>(emptyList())

    val counter: StateFlow<Int> = _counter.asStateFlow()
    val options: StateFlow<List<Section>> = _options.asStateFlow()
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

    fun getOptions(){
        viewModelScope.launch {
            getMealOptions().asResult().collect{
                result ->
                when(result){
                    is com.example.common.Result.Error -> {
                        TODO("Implement error state")
                    }
                    com.example.common.Result.Loading -> {
//                        TODO("implement loading state")
                    }
                    is com.example.common.Result.Success -> {
                        _options.value = result.data
                    }
                }
            }
        }
    }

}