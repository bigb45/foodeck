package com.example.menu_item

import android.util.Log.d
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.asResult
import com.example.data.repositories.Section
import com.example.domain.use_cases.GetMealOptionsUseCase
import com.example.menu_item.navigation.menuItemIdArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val _screenState = MutableStateFlow<MenuItemUiState>(MenuItemUiState.Loading)
    private val _radioButtonStateList = MutableStateFlow<Map<String, String>>(emptyMap())
    val counter: StateFlow<Int> = _counter.asStateFlow()
    val options: StateFlow<List<Section>> = _options.asStateFlow()
    val screenState: StateFlow<MenuItemUiState> = _screenState.asStateFlow()
    val radioGroupState: StateFlow<Map<String, String>> = _radioButtonStateList.asStateFlow()

    val menuItemId: String =
        URLDecoder.decode(savedStateHandle[menuItemIdArgument], Charsets.UTF_8.name())

    init {
        getOptions()
    }

    fun incrementCounter() {
        _counter.value += 1
    }
    fun decrementCounter() {
        if(_counter.value > 1){
            _counter.value -= 1
        }
    }

    private fun getOptions(){
        viewModelScope.launch {
            getMealOptions().asResult().collect{
                result ->
                when(result){
                    is com.example.common.Result.Error -> {
                        _screenState.value = MenuItemUiState.Error(result.exception?.message)
                    }
                    com.example.common.Result.Loading -> {
//                        TODO("implement loading state")
                    }
                    is com.example.common.Result.Success -> {
                        _screenState.value = MenuItemUiState.Success(result.data)
                        generateRadioGroupState(result.data)
                    }
                }
            }
        }
    }

    private fun generateRadioGroupState(data: List<Section>) {
        val radioGroups = data.filter { it.sectionType == "radio" }
        val mutableMap = _radioButtonStateList.value.toMutableMap()

        radioGroups.forEach {
            mutableMap[it.sectionTitle] = ""
        }
        _radioButtonStateList.value = mutableMap
        d("error", "${_radioButtonStateList.value}")

    }

    fun setRadioSelection(key: String, newSelection: String) {
        val mutableMap = _radioButtonStateList.value.toMutableMap()
        mutableMap[key] = newSelection
        _radioButtonStateList.value = mutableMap
        d("errora", _radioButtonStateList.value.toString())
    }

}