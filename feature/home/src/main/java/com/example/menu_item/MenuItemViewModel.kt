package com.example.menu_item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.asResult
import com.example.common.Result
import com.example.data.models.Option
import com.example.data.models.OptionsSectionDto
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
    private val _optionsState = MutableStateFlow<OptionsState>(OptionsState.Loading)
    private val _baseMealPrice = 21f
    private val _totalPrice = MutableStateFlow(_baseMealPrice)
    private val _radioGroupListState = MutableStateFlow<Map<String, Option?>>(emptyMap())
    private val _checkboxListState = MutableStateFlow<Map<String, List<Option>>>(emptyMap())
//    TODO: create a list of floats that stores the additional price of each added component

    val screenState: StateFlow<OptionsState> = _optionsState.asStateFlow()
    val radioGroupListState: StateFlow<Map<String, Option?>> = _radioGroupListState.asStateFlow()
    val checkboxListState: StateFlow<Map<String, List<Option>>> = _checkboxListState.asStateFlow()
    val totalPrice: StateFlow<Float> = _totalPrice.asStateFlow()
    val counter: StateFlow<Int> = _counter.asStateFlow()
    val menuItemId: String =
        URLDecoder.decode(savedStateHandle[menuItemIdArgument], Charsets.UTF_8.name())

    init {
        getOptions()
    }
//    TODO: add form checking on add to cart click
    private fun calculateTotalPrice(): Float {
        val radioTotal = _radioGroupListState.value.map {
            var total = 0f
//            TODO: create order details class which has all information about meal and (maybe?) user
//            TODO: use this to make the api call to the backend, handle the order details in the backend
            val section = getSectionFromId(
                data = _optionsState.value as OptionsState.Success,
                sectionId = it.key
            )
            if(it.value != null){
                total += it.value!!.price
            }

            total
        }.sum()

        val checkBoxTotal = _checkboxListState.value.map {
            var total = 0f
            val section = getSectionFromId(
                data = _optionsState.value as OptionsState.Success,
                sectionId = it.key
            )

            total += it.value.map { option -> option.price }.sum()
            total
        }.sum()

         val total = (_baseMealPrice + radioTotal + checkBoxTotal) * _counter.value
         _totalPrice.value = total
        return total
    }



//    TODO: make the calculateTotalPrice function generic
    fun onRadioSelected(key: String, newSelection: Option) {
        val mutableMap = _radioGroupListState.value.toMutableMap()

        mutableMap[key] = newSelection
        _radioGroupListState.value = mutableMap
        calculateTotalPrice()
    }

    fun onCheckBoxSelected(key: String, newSelection: Option, isSelected: Boolean) {
        val mutableMap = _checkboxListState.value.toMutableMap()
        val mutableList =  (mutableMap[key] ?: emptyList()).toMutableSet()

        if(isSelected){
            mutableList.add(newSelection)
        }else{
            mutableList.remove(newSelection)
        }

        mutableMap[key] = mutableList.toList()
        _checkboxListState.value = mutableMap
        calculateTotalPrice()
    }

    fun incrementCounter() {
        _counter.value += 1
        calculateTotalPrice()
    }

    fun decrementCounter() {
        if (_counter.value > 1) {
            _counter.value -= 1
            calculateTotalPrice()
        }
    }

    private fun getOptions() {
        viewModelScope.launch {
            getMealOptions().asResult().collect { result ->
                when (result) {
                    is Result.Error -> {
                        _optionsState.value = OptionsState.Error(result.exception?.message)
                    }

                    Result.Loading -> {
                        _optionsState.value = OptionsState.Loading
                    }

                    is Result.Success -> {
                        _optionsState.value = OptionsState.Success(result.data)
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

    private fun getSectionFromId(data: OptionsState.Success, sectionId: String): OptionsSectionDto {
        return data.sections.first { it.id == sectionId }
    }
}