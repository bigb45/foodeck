package com.example.menu_item

import android.view.ViewTreeObserver
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.CollapsingToolbar
import com.example.common.LoadingIndicator
import com.example.common.log
import com.example.compose.gray6
import com.example.custom_toolbar.ToolbarState
import com.example.data.models.Option
import com.example.data.models.OptionsSectionDto
import com.example.data.models.toSectionData
import com.example.restaurant.MAX_TOOLBAR_HEIGHT
import com.example.restaurant.MIN_TOOLBAR_HEIGHT
import com.example.restaurant.rememberCustomNestedConnection
import com.example.restaurant.rememberToolbarState
import kotlinx.coroutines.async

@Composable
fun MenuItemScreen(onNavigateUp: () -> Unit) {
    val toolbarRange = with(LocalDensity.current) {
        MIN_TOOLBAR_HEIGHT.roundToPx()..MAX_TOOLBAR_HEIGHT.roundToPx()
    }

    val viewModel: MenuItemViewModel = hiltViewModel()

    val toolbarState = rememberToolbarState(toolbarRange)
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    val nestedScrollConnection = rememberCustomNestedConnection(toolbarState, lazyListState, scope)
    val screenState = viewModel.menuItemScreenState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {

        when (screenState.value.optionsState) {

            is OptionsState.Loading -> {
                LoadingIndicator()
            }

            is OptionsState.Error -> {
                Text("error")
            }

            is OptionsState.Success -> {
                MenuItems(
                    modifier = Modifier
                        .padding(it),
                    nestedScrollConnection = nestedScrollConnection,
                    toolbarState = toolbarState,
                    lazyListState = lazyListState,
                    screenState = screenState.value,
                    viewModel = viewModel,
                    snackbarHost = snackbarHostState,
                    onNavigateUp = onNavigateUp,
                    )
            }

        }
    }
}


@Composable
fun MenuItems(
    modifier: Modifier = Modifier,
    nestedScrollConnection: NestedScrollConnection,
    toolbarState: ToolbarState,
    lazyListState: LazyListState,
    screenState: MenuItemScreenState,
    viewModel: MenuItemViewModel,
    snackbarHost: SnackbarHostState,
    onNavigateUp: () -> Unit,
    ) {

    Box(
        modifier = modifier
            .nestedScroll(nestedScrollConnection)
            .background(gray6)
    ) {
        MenuOptions(
            toolbarState = toolbarState,
            lazyListState = lazyListState,
            screenState = screenState,
            snackbarHost = snackbarHost,
            { key, newSelection ->
                viewModel.onRadioSelected(key = key, newSelection = newSelection)
            },
            onCheckboxSelectionChange = { key, newSelection, isSelected ->
                viewModel.onCheckBoxSelected(
                    key = key,
                    newSelection = newSelection,
                    isSelected
                )
            },
            increment = { viewModel.incrementCounter() },
            decrement = { viewModel.decrementCounter() },
            onInstructionsChange = { newText -> viewModel.setCustomInstructions(newText) },
            onAddToCartClick = {
                if (viewModel.onAddToCartClick()) {
                    onNavigateUp()
                }
            }
        )

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
    screenState: MenuItemScreenState,
    snackbarHost: SnackbarHostState,
    onRadioSelectionChange: (String, Option) -> Unit,
    onCheckboxSelectionChange: (String, Option, Boolean) -> Unit,
    increment: () -> Unit,
    decrement: () -> Unit,
    onInstructionsChange: (String) -> Unit,
    onAddToCartClick: () -> Unit,

    ) {


    val imeState = rememberImeState()
//  key1 scrolls the text field into view
//  key2 scrolls the text field into view when the user starts typing
    LaunchedEffect(key1 = imeState.value, key2 = screenState.instructions) {
        /*
        * This block prevents unwanted behavior, the unwanted behavior is as follows:
        * 1. when the user clicks on the text field, it doesn't come up into view
        * 2. when the text field has focus and the user scrolls away, typing does not bring the text field back into view
        * 3. when the top bar is expanded and the text field is clicked, the top bar does not collapse
        * */
        if (imeState.value) {
            toolbarState.collapse()
            lazyListState.animateScrollToItem(lazyListState.layoutInfo.totalItemsCount)
        }
    }

    LaunchedEffect(key1 = screenState.trigger) {
        val scrollToItemDeferred = async {
            if (screenState.unselectedSectionIndex != null) {
                lazyListState.animateScrollToItem(screenState.unselectedSectionIndex)
            }
        }

        val showSnackbarDeferred = async {
            if (screenState.unselectedSectionIndex != null) {
                snackbarHost.showSnackbar(
                    "Please make a selection on ${screenState.unselectedSection}",
                    duration = SnackbarDuration.Short,
                    withDismissAction = true
                )
            }
        }

        val collapseToolbarDeferred = async {
            if (lazyListState.firstVisibleItemIndex != 0) {
                toolbarState.collapse()
            }
        }

        scrollToItemDeferred.await()
        showSnackbarDeferred.await()
        collapseToolbarDeferred.await()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    translationY = toolbarState.height + toolbarState.offset
                },

            contentPadding = PaddingValues(bottom = 160.dp),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(screenState.sections) { section ->
                val isSectionSelected = screenState.unselectedSection != section.sectionTitle
                val data = remember { section.toSectionData() }
                if (section.sectionType == "checkbox") {
                    CheckBoxSelector(
                        data = data,
                        selectedOptions = screenState.selectedCheckboxOptions,
                        selected = isSectionSelected,
                        onSelectionChange = { key, option, isSelected ->
                            onCheckboxSelectionChange(key, option, isSelected)
                        },
                    )

                } else if (section.sectionType == "radio") {

                    RadioSelector(
                        data = data,
                        selectedOption = screenState.selectedRadioOption[section.id],
                        sectionSelected = isSectionSelected,
                        onSelectionChange = onRadioSelectionChange,
                        )
                }
            }

            item {
                Counter(
                    modifier = Modifier.fillMaxHeight(),
                    counter = screenState.quantity,
                    increment = increment,
                    decrement = decrement
                )
            }

            item {
                Instructions(
                    onTextChange = {
                        onInstructionsChange(it)
                    },
                    text = screenState.instructions,
                )
            }


        }
        CartBottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onAddToCartClick = onAddToCartClick,
            totalPrice = screenState.totalPrice
        )
    }
}


@Composable
fun MealActions() {
    IconButton(onClick = {
        log("add meal to favorites")
    }) {
        Icon(
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = "Like restaurant",
            tint = Color.White,
        )
    }
    IconButton(onClick = {
        log("share meal")

    }) {
        Icon(
            imageVector = Icons.Outlined.Share,
            contentDescription = "Share restaurant",
            tint = Color.White,
        )
    }
    IconButton(onClick = {
        log("more options")
    }) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = "More",
            tint = Color.White,
        )
    }
}

@Composable
fun rememberImeState(): State<Boolean> {
    val imeState = remember {
        mutableStateOf(false)
    }

    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            imeState.value = isKeyboardOpen
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    return imeState
}
