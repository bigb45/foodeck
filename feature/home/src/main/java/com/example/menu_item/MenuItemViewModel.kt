package com.example.menu_item

import android.util.Log.d
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.asResult
import com.example.data.repositories.Option
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
    private val getMealOptions: GetMealOptionsUseCase,
) : ViewModel() {
    private val _order = MutableStateFlow("")
    private val _counter = MutableStateFlow(1)

    //    TODO: change this to List<BaseSectionData>
    private val _screenState = MutableStateFlow<OptionsState>(OptionsState.Loading)
    private val _totalPrice = MutableStateFlow<Float>(21f)
    //    a map of "sectionId" and the "Selection" of that section
    private val _radioGroupListState = MutableStateFlow<Map<String, Option?>>(emptyMap())
    private val _baseMealPrice = 24f
//    TODO: create a list of floats that stores the additional price of each added component

    val screenState: StateFlow<OptionsState> = _screenState.asStateFlow()
    val radioGroupListState: StateFlow<Map<String, Option?>> = _radioGroupListState.asStateFlow()
    val totalPrice: StateFlow<Float> = _totalPrice.asStateFlow()
    val counter: StateFlow<Int> = _counter.asStateFlow()
    val menuItemId: String =
        URLDecoder.decode(savedStateHandle[menuItemIdArgument], Charsets.UTF_8.name())

    init {
        getOptions()
//        combine(aa
//            _counter,
//            _radioGroupListState,
//            _screenState
//        ){ count, radioButtonListState, screenState ->
//            calculateTotalPrice(count, radioButtonListState, screenState)
//        }.launchIn(viewModelScope)
    }

    private fun calculateTotalPrice(count: Int, options: Map<String, Option?>): Float {
        val extrasTotal = options.map {
            var total = 0f
//            TODO: use this to make the api call to the backend, handle the order details in the backend
//            TODO: create order details class, has all information about meal and (maybe?) user
            val section = getSectionFromId(
                data = _screenState.value as OptionsState.Success,
                sectionId = it.key
            )
            if(it.value != null){
                total += it.value!!.price
            }

            total
        }.sum()

         val total = (_baseMealPrice * count)  + (extrasTotal * count)
         _totalPrice.value = total
//        d("error", "$total")
        return total
    }


    fun onRadioSelected(key: String, newSelection: Option) {
        val mutableMap = _radioGroupListState.value.toMutableMap()

        mutableMap[key] = newSelection
        _radioGroupListState.value = mutableMap
        calculateTotalPrice(_counter.value, _radioGroupListState.value)
    }

    fun incrementCounter() {
        _counter.value += 1
        calculateTotalPrice(_counter.value, _radioGroupListState.value)

    }

    fun decrementCounter() {
        if (_counter.value > 1) {
            _counter.value -= 1
            calculateTotalPrice(_counter.value, _radioGroupListState.value)

        }
    }

    private fun getOptions() {
        viewModelScope.launch {
            getMealOptions().asResult().collect { result ->
                when (result) {
                    is com.example.common.Result.Error -> {
                        _screenState.value = OptionsState.Error(result.exception?.message)
                    }

                    com.example.common.Result.Loading -> {
                        _screenState.value = OptionsState.Loading
                    }

                    is com.example.common.Result.Success -> {
                        _screenState.value = OptionsState.Success(result.data)
                        generateRadioGroupState(result.data)
                    }
                }
            }
        }
    }

    private fun getMenuDetails(menuId: String){
        viewModelScope.launch {
//            TODO: get menu details from api request
//            TODO: set initial price to price from menu details
//            _totalPrice = result.data.basePrice
        }
    }

    private fun generateRadioGroupState(data: List<Section>) {
        val radioGroups = data.filter { it.sectionType == "radio" }
        val mutableList = _radioGroupListState.value.toMutableMap()
//        set the radio selection for each section to null
        radioGroups.forEach { section ->
            mutableList[section.id] = null

        }
        _radioGroupListState.value = mutableList
    }

    private fun getSectionFromId(data: OptionsState.Success, sectionId: String): Section {
        return data.sections.first { it.id == sectionId }
    }
}