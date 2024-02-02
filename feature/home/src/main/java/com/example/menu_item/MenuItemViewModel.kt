package com.example.menu_item

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.common.asResult
import com.example.common.log
import com.example.data.models.CartItemDto
import com.example.data.models.Option
import com.example.data.models.OptionsSectionDto
import com.example.domain.use_cases.GetMealOptionsUseCase
import com.example.domain.use_cases.SaveCartItem
import com.example.menu_item.navigation.menuItemIdArgument
import com.example.restaurant.navigation.restaurantIdArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MenuItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMealOptions: GetMealOptionsUseCase,
    private val addToCart: SaveCartItem,
) : ViewModel() {

    private val _counter = MutableStateFlow(1)
    private val _optionsState = MutableStateFlow<OptionsState>(OptionsState.Loading)
    private val _baseMenuPrice = MutableStateFlow<Float>(21f)
    private val _launchedEffectTrigger = MutableStateFlow<Boolean>(true)
    private val _state = MutableStateFlow<MenuItemScreenState>(MenuItemScreenState(
        quantity = _counter.value,
        optionsState = _optionsState.value,
        trigger = _launchedEffectTrigger.value,
        initialMenuPrice = _baseMenuPrice.value
    ))

    val menuItemScreenState: StateFlow<MenuItemScreenState> = _state.asStateFlow()


    private val menuItemId: String =
        URLDecoder.decode(savedStateHandle[menuItemIdArgument], Charsets.UTF_8.name())
    private val restaurantId: String =
        URLDecoder.decode(savedStateHandle[restaurantIdArgument], Charsets.UTF_8.name())

    init {
        getOptions()
    }

    private fun calculateTotalPrice(): Float {
        val radioButtonList = _state.value.selectedRadioOption
        val checkboxList = _state.value.selectedCheckboxOptions
        val selectionsTotal = (radioButtonList + checkboxList).map { (key, value) ->
            val section = getSectionFromId(key)
            when (section.sectionType) {
                "checkbox" -> {
                    (value as List<Option>).map { option -> option.price }.sum()
                }

                "radio" -> {
                    (value as Option).price
                }

                else -> 0f
            }
        }.sum()


        val total = (_baseMenuPrice.value + selectionsTotal) * _counter.value
        _state.value = _state.value.copy(
            totalPrice = total
        )
        return total
    }


    //    TODO: make the calculateTotalPrice function generic
    fun onRadioSelected(key: String, newSelection: Option) {
        val mutableMap = _state.value.selectedRadioOption.toMutableMap()
        mutableMap[key] = newSelection
        _state.value = _state.value.copy(
            selectedRadioOption = mutableMap
        )
        calculateTotalPrice()
    }

    fun onCheckBoxSelected(key: String, newSelection: Option, isSelected: Boolean) {
        val mutableMap = _state.value.selectedCheckboxOptions.toMutableMap()
        val mutableList = (mutableMap[key] ?: emptyList()).toMutableSet()


        if (isSelected) {
            mutableList.add(newSelection)
        } else {
            mutableList.remove(newSelection)
        }

//        this is to make sure the map does not have empty lists like {2=[], 3=[items]} (remove 2)
        if (mutableList.isEmpty()) {
            mutableMap.remove(key)
        } else {
            mutableMap[key] = mutableList.toList()
        }
        _state.value = _state.value.copy(
            selectedCheckboxOptions = mutableMap
        )
        calculateTotalPrice()
    }

    fun incrementCounter() {
        _counter.value += 1
        _state.value = _state.value.copy(
            quantity = _counter.value
        )
        calculateTotalPrice()
    }

    fun decrementCounter() {
        if (_counter.value > 1) {
            _counter.value -= 1
            _state.value = _state.value.copy(
                quantity = _counter.value
            )
            calculateTotalPrice()
        }
    }

    private fun getOptions() {
        viewModelScope.launch {
            getMealOptions(restaurantId = restaurantId, menuId = menuItemId).asResult()
                .collect { result ->
                    when (result) {
                        is Result.Error -> {
                            _state.value = _state.value.copy(
                                optionsState = OptionsState.Error(result.exception?.message)
                            )
                        }

                        Result.Loading -> {
                            _state.value = _state.value.copy(
                                optionsState = OptionsState.Loading
                            )
                        }

                        is Result.Success -> {
                            _optionsState.value = OptionsState.Success(result.data)
                            _state.value = _state.value.copy(
                                optionsState = OptionsState.Success(result.data),
                                sections = result.data
                            )
                        }


                    }
                }
        }
    }

    fun setCustomInstructions(newInstructions: String) {
        _state.value = _state.value.copy(
            instructions = newInstructions
        )
    }

    private fun getMenuDetails(menuId: String) {
        viewModelScope.launch {
//            TODO: get menu details from api request
//            TODO: set initial price to price from menu details
//            _totalPrice = result.data.basePrice
        }
    }

    private fun getSectionFromId(sectionId: String): OptionsSectionDto {
        return (_optionsState.value as OptionsState.Success).sections.first { it.id == sectionId }
    }

    fun onAddToCartClick(): Boolean {
        if (validateSelections()) {
            CoroutineScope(IO).launch {
                val cartItemDto = CartItemDto(
                    id = UUID.randomUUID().toString(),
                    menuId = menuItemId,
                    orderQuantity = _state.value.quantity,
                    customInstructions = _state.value.instructions

                )
                log("adding $cartItemDto to the database")
                addToCart(cartItemDto)

            }
            return true
        }
        return false
    }

    private fun validateSelections(): Boolean {
        val radioButtonList = _state.value.selectedRadioOption
        val checkboxList = _state.value.selectedCheckboxOptions
        val requiredSections =
            (_optionsState.value as OptionsState.Success).sections.map { getSectionFromId(it.id) }
                .filter {
                    it.required
                }
        requiredSections.forEachIndexed { index, section ->
//            if the lists of selected sections don't contain the required section -> trigger
            if (!(radioButtonList.keys + checkboxList.keys).contains(section.id)) {
                _launchedEffectTrigger.value = !_launchedEffectTrigger.value
                _state.value = _state.value.copy(
                    unselectedSectionIndex = index,
                    unselectedSection = section.sectionTitle,
                    trigger = _launchedEffectTrigger.value
                )
                return false
            }
        }
        return true
    }

}