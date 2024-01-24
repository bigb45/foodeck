package com.example.menu_item

import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.CollapsingToolbar
import com.example.compose.gray6
import com.example.custom_toolbar.ToolbarState
import com.example.restaurant.MAX_TOOLBAR_HEIGHT
import com.example.restaurant.MIN_TOOLBAR_HEIGHT
import com.example.restaurant.rememberCustomNestedConnection
import com.example.restaurant.rememberToolbarState

@Composable
fun MenuItemScreen(onNavigateUp: () -> Unit) {
    val toolbarRange = with(LocalDensity.current) {
        MIN_TOOLBAR_HEIGHT.roundToPx()..MAX_TOOLBAR_HEIGHT.roundToPx()
    }

    val toolbarState = rememberToolbarState(toolbarRange)
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    val nestedScrollConnection = rememberCustomNestedConnection(toolbarState, lazyListState, scope)
    val viewModel: MenuItemViewModel = hiltViewModel()

    val screenState = viewModel.screenState.collectAsState()

    val checkboxSelectedOptions = remember {
        mutableStateOf(setOf<String>())
    }
    val radioSelectedOption by remember { mutableStateOf("") }

    val radioGroups = remember { mutableMapOf<String, String>() }

    when (screenState.value) {

        is MenuItemUiState.Loading -> {
            CircularProgressIndicator()
        }

        is MenuItemUiState.Error -> {
            Text("error")
        }

        is MenuItemUiState.Success -> {

            MenuItem(
                nestedScrollConnection = nestedScrollConnection,
                toolbarState = toolbarState,
                onNavigateUp = onNavigateUp,
                lazyListState = lazyListState,
                screenState = screenState.value as MenuItemUiState.Success,
                viewModel = viewModel,
                checkboxSelectedOptions = checkboxSelectedOptions,
                onRadioSelectionChange = {
                                      key, newSelection ->
                                      viewModel.setRadioSelection(key, newSelection)
                },
                radioSelectedOptions = viewModel.radioGroupState.collectAsState(),
                )
        }

    }
}


@Composable
fun MenuItem(
    nestedScrollConnection: NestedScrollConnection,
    toolbarState: ToolbarState,
    onNavigateUp: () -> Unit,
    lazyListState: LazyListState,
    screenState: MenuItemUiState.Success,
    viewModel: MenuItemViewModel,
    checkboxSelectedOptions: MutableState<Set<String>>,
    onRadioSelectionChange: (String, String) -> Unit,
    radioSelectedOptions: State<Map<String, String>>,

    ) {
    Column(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection)
            .background(gray6)
    ) {

        CollapsingToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { toolbarState.height.toDp() }),
            coverImageUrl = null,
            progress = toolbarState.progress,
            onNavigateUp = onNavigateUp,
            expandedActions = { MealActions() },
            title = "Meal name",
            subTitle = "Restaurant name, address",
        )
        Box {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 108.dp),
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(screenState.sections) { section ->
                    if (section.sectionType == "checkbox") {
//                        TODO: change section and only pass options.data instead of creating data class here
                        CheckBoxSelector(
                            data = CheckBoxSelectorData(
                                title = section.sectionTitle,
                                options = section.options.associate { option ->
                                    Pair(option.optionName, option.price)
                                },
                                currency = section.currency,
                                required = section.required,
                            ), selectedOptions = checkboxSelectedOptions
                        ) { option, isSelected ->
                            val currentSelected = checkboxSelectedOptions.value.toMutableSet()
                            if (isSelected) {
                                currentSelected.add(option)
                            } else {
                                currentSelected.remove(option)
                            }
                            checkboxSelectedOptions.value = currentSelected
                            d("error", checkboxSelectedOptions.value.toString())
                        }

                    } else if (section.sectionType == "radio") {
                        RadioSelector(
                            data = RadioSelectorData(
                                title = section.sectionTitle,
                                options = section.options,
                                currency = section.currency,
                                required = section.required,
                            ),
                            selectedOption = radioSelectedOptions.value[section.sectionTitle],
                            onSelectionChange = onRadioSelectionChange
                        )

                    }

                }

                item {
                    Counter(modifier = Modifier.fillMaxHeight(),
                        counter = viewModel.counter.collectAsState().value,
                        increment = { viewModel.incrementCounter() },
                        decrement = { viewModel.decrementCounter() })
                }

                item {
                    Instructions(onTextChange = { /*TODO*/ }, text = "")
                }
            }

            CartBottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                onAddToCartClick = { },
                totalPrice = "186.00"
            )

        }
    }
}


@Composable
fun MealActions() {
    IconButton(onClick = {
        d("error", "add meal to favorites")
    }) {
        Icon(
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = "Like restaurant",
            tint = Color.White,
        )
    }
    IconButton(onClick = {
        d("error", "share meal")

    }) {
        Icon(
            imageVector = Icons.Outlined.Share,
            contentDescription = "Share restaurant",
            tint = Color.White,
        )
    }
    IconButton(onClick = {
        d("error", "more options")


    }) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = "More",
            tint = Color.White,
        )
    }
}