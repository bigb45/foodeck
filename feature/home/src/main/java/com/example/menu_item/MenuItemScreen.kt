package com.example.menu_item

import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.CollapsingToolbar
import com.example.common.LoadingIndicator
import com.example.compose.gray6
import com.example.custom_toolbar.ToolbarState
import com.example.data.repositories.Option
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

    when (screenState.value) {

        is OptionsState.Loading -> {
            LoadingIndicator()
        }

        is OptionsState.Error -> {
            Text("error")
        }

        is OptionsState.Success -> {

            MenuItems(
                nestedScrollConnection = nestedScrollConnection,
                toolbarState = toolbarState,
                onNavigateUp = onNavigateUp,
                lazyListState = lazyListState,
                screenState = screenState.value as OptionsState.Success,
                viewModel = viewModel,
                checkboxSelectedOptions = checkboxSelectedOptions,
                radioSelectedOptions = viewModel.radioGroupListState.collectAsState().value,
                onRadioSelectionChange = { key, newSelection ->
                    viewModel.onRadioSelected(key, newSelection)
                },
            )
        }

    }
}


@Composable
fun MenuItems(
    nestedScrollConnection: NestedScrollConnection,
    toolbarState: ToolbarState,
    onNavigateUp: () -> Unit,
    lazyListState: LazyListState,
    screenState: OptionsState.Success,
    viewModel: MenuItemViewModel,
    checkboxSelectedOptions: MutableState<Set<String>>,
    onRadioSelectionChange: (String, Option) -> Unit,
    radioSelectedOptions: Map<String, Option?>,

    ) {
    d("error", "total price: ${viewModel.totalPrice.collectAsState().value}")
    Box(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection)
            .background(gray6)
    ) {
        MenuOptions(
            toolbarState = toolbarState,
            lazyListState = lazyListState,
            screenState = screenState,
            checkboxSelectedOptions = checkboxSelectedOptions,
            onRadioSelectionChange = onRadioSelectionChange,
            radioSelectedOptions = radioSelectedOptions,
            count = viewModel.counter.collectAsState().value,
            totalPrice = viewModel.totalPrice.collectAsState().value,
            increment = { viewModel.incrementCounter() }
        ) { viewModel.decrementCounter() }

        CollapsingToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { toolbarState.height.toDp() })
                .graphicsLayer { translationY = toolbarState.offset },
            coverImageUrl = null,
            progress = toolbarState.progress,
            onNavigateUp = onNavigateUp,
            expandedActions = { MealActions() },
            title = "Meal name",
            subTitle = "Restaurant name, address",
        )

    }
}

@Composable
fun MenuOptions(
    toolbarState: ToolbarState,
    lazyListState: LazyListState,
    screenState: OptionsState.Success,
    checkboxSelectedOptions: MutableState<Set<String>>,
    radioSelectedOptions: Map<String, Option?>,
    count: Int,
    totalPrice: Float,
    onRadioSelectionChange: (String, Option) -> Unit,
    increment: () -> Unit,
    decrement: () -> Unit,
) {
    Box(
        modifier = Modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    translationY =
                        toolbarState.height + toolbarState.offset
                },
            contentPadding = PaddingValues(bottom = 108.dp),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(screenState.sections) { section ->

                if (section.sectionType == "checkbox") {
//                        TODO: change section and only pass options.data instead of creating data class here
                    val data = remember {
                        CheckBoxSelectorData(
                            title = section.sectionTitle,
                            options = section.options.associate { option ->
                                Pair(option.optionName, option.price)
                            },
                            currency = section.currency,
                            required = section.required,
                        )
                    }
                    CheckBoxSelector(
                        data = data,
                        selectedOptions = checkboxSelectedOptions
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
                    val data = remember {
                        RadioSelectorData(
                            id = section.id,
                            title = section.sectionTitle,
                            options = section.options,
                            currency = section.currency,
                            required = section.required,
                        )
                    }
                    RadioSelector(
                        data = data,
                        selectedOption = radioSelectedOptions[section.id],
                        onSelectionChange = onRadioSelectionChange,

                        )

                }

            }

            item {
                Counter(
                    modifier = Modifier.fillMaxHeight(),
                    counter = count,
                    increment = increment,
                    decrement = decrement
                )
            }

            item {
                Instructions(onTextChange = { /*TODO*/ }, text = "")
            }
        }

        CartBottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onAddToCartClick = { },
            totalPrice = totalPrice

        )

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