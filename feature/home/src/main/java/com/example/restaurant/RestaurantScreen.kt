package com.example.restaurant

import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ahmadhamwi.tabsync_compose.lazyListTabSync
import com.example.common.AnimatedTabs
import com.example.model.Category
import com.example.common.CollapsingToolbar
import com.example.common.LoadingIndicator
import com.example.common.log
import com.example.compose.gray6
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.core.ui.theme.interBold
import com.example.custom_toolbar.CustomTopAppBarState
import com.example.custom_toolbar.ToolbarState
import com.example.data.models.Restaurant
import com.example.data.models.RestaurantSection
import com.example.model.Menu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

val MIN_TOOLBAR_HEIGHT = 68.dp
val MAX_TOOLBAR_HEIGHT = 176.dp
val TAB_LAYOUT_HEIGHT = 40.dp

lateinit var  restaurantId: String
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(
    onNavigateUp: () -> Unit,
    onItemClick: (String, String) -> Unit,
) {

    val scope = rememberCoroutineScope()

    val isDialogOpen = remember { mutableStateOf(false) }
    val isSheetOpen = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val viewModel: RestaurantViewModel = hiltViewModel()
    restaurantId = viewModel.restaurantId
    val items = viewModel.restaurantMenus.collectAsState()
    LaunchedEffect(Unit) {
        if(items.value !is RestaurantState.Success){
            viewModel.fetchRestaurantDetails()
        }
    }

    val placeHolder = listOf(
        Category(
            categoryName = "1", items = listOf(
                Menu(
                    id = "99",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "98",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "97",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "96",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),

            )
        ),
        Category(
            categoryName = "2", items = listOf(
                Menu(
                    id = "89",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "88",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "87",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "86",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),

            )
        ),
        Category(
            categoryName = "3", items = listOf(
                Menu(
                    id = "79",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "78",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "77",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "76",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),

            )
        ),
        Category(
            categoryName = "4", items = listOf(
                Menu(
                    id = "69",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "68",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "67",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "66",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),

            )
        ),
        Category(
            categoryName = "5", items = listOf(
                Menu(
                    id = "59",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "58",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "57",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),
                Menu(
                    id = "56",
                    name = "Meal1",
                    imageUrl = null,
                    contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
                    price = "99.99",
                    currency = "$"
                ),

            )
        ),

    )
//

// TODO: make this an api call
    val restaurant = Restaurant(
        "test",
        "The Foodeck Shop",
        "Ankara, Golbasi",
        "40 min",
        "test",
        null,
        "4.4",
    )

    FoodDeliveryTheme {
        Scaffold {
            it
            when (items.value) {
                is RestaurantState.Success -> {
                    //    This is bad, don't do this in the UI
                    var categories =
                        (items.value as RestaurantState.Success<List<RestaurantSection>>).data.map { section ->
                            Category(categoryName = section.sectionTitle,
                                items = section.menuItems.map { storeItem ->
                                    Menu(
                                        id = storeItem.itemId,
                                        name = storeItem.itemName,
                                        imageUrl = storeItem.coverImageUrl,
                                        contents = storeItem.description,
                                        price = storeItem.price.toString(),
                                        currency = "$"
                                    )
                                })
                        }
//                    crash prevention measures
                    if (categories.isEmpty()) {
                        categories = placeHolder
                    }
                    when {
                        isDialogOpen.value -> {
                            AlertDialog(title = { Text("Delivery Time") },
                                text = { Text("This restaurant will take approximately ${40} minutes to deliver.") },
                                onDismissRequest = { isDialogOpen.value = false },
                                confirmButton = {
                                    TextButton(onClick = {
                                        isDialogOpen.value = false
                                        log("confirmed dialog")
                                    }) {
                                        Text("Ok")
                                    }
                                })
                        }

                    }
                        if(isSheetOpen.value){
                            ModalBottomSheet(
                                onDismissRequest = {
                                    log("Sheet dismissed")
                                    isSheetOpen.value = false
                                },
                                sheetState = sheetState
                            ) {
                                LazyColumn {
                                    items(100) { index ->
                                        Text(
                                            modifier = Modifier.padding(
                                                horizontal = 10.dp,
                                                vertical = 8.dp
                                            ),
                                            text = "This is ${index}th modal bottom sheet item"
                                        )
                                    }
                                }
                            }
                        }

                    Restaurant(restaurant = restaurant,
                        categories = categories,
                        onNavigateUp = onNavigateUp,
                        onItemClick = {
                                      restaurantId, menuId ->
                            onItemClick(restaurantId, menuId)
                        },
                        onDeliveryTimeClick = {
                            isDialogOpen.value = true
                        },
                        onRatingsClick = {
                            isSheetOpen.value = true
                            scope.launch{
                                sheetState.expand()

                            }
                        }

                    )
                }

                is RestaurantState.Loading -> {
                    LoadingIndicator()
                }

                RestaurantState.Error -> {
                    Text("error")
                }
            }

        }
    }
}


@Composable
internal fun Restaurant(
    restaurant: Restaurant,
    categories: List<Category>,
    onNavigateUp: () -> Unit = {},
    onItemClick: (String, String) -> Unit = {_, _ -> },
    onShareClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    onFavoriteClick: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onRestaurantLocationClick: () -> Unit = {},
    onDeliveryTimeClick: () -> Unit = {},
    onRatingsClick: () -> Unit = {},

    ) {

    val toolbarRange = with(LocalDensity.current) {
        MIN_TOOLBAR_HEIGHT.roundToPx()..MAX_TOOLBAR_HEIGHT.roundToPx()
    }

    val toolbarState = rememberToolbarState(toolbarRange)

    val scope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()

    val (selectedTabIndex, setSelectedTabIndex) = lazyListTabSync(
        syncedIndices = categories.indices.toList(), lazyListState = lazyListState
    )

    val nestedScrollConnection = rememberCustomNestedConnection(
        lazyListState = lazyListState, toolbarState = toolbarState, scope = scope
    )
    Box(
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {

        Menus(
            modifier = Modifier.graphicsLayer {
                translationY =
                    toolbarState.height + toolbarState.offset + TAB_LAYOUT_HEIGHT.toPx() + (100.dp.toPx() * toolbarState.progress)
            },
            contentPadding = PaddingValues(bottom = TAB_LAYOUT_HEIGHT),
            categories = categories,
            lazyListState = lazyListState,
            onItemClick = {restaurantId, menuId ->
                onItemClick(restaurantId, menuId)
            },
        )

        CollapsingToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { toolbarState.height.toDp() })
                .graphicsLayer { translationY = toolbarState.offset },
            title = restaurant.restaurantName,
            subTitle = restaurant.restaurantAddress,
            coverImageUrl = restaurant.coverImageUrl,
            progress = toolbarState.progress,
            onNavigateUp = onNavigateUp,
            preContent = {
                RestaurantInfo(
                    modifier = Modifier

                        .height((toolbarState.progress * 100).dp)
                        .graphicsLayer { translationY = toolbarState.offset },
                    restaurant = restaurant,
                    onRatingsClick = onRatingsClick,
                    onDeliveryTimeClick = onDeliveryTimeClick,
                    onRestaurantLocationClick = onRestaurantLocationClick
                )
            },
            expandedActions = {
                IconButton(onClick = { onFavoriteClick(restaurant.restaurantId) }) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like restaurant",
                        tint = Color.White,
                    )
                }

                IconButton(onClick = onShareClick) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "Share restaurant",
                        tint = Color.White,
                    )
                }
                IconButton(onClick = onMoreClick) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = "More",
                        tint = Color.White,
                    )
                }
            },
            collapsedActions = {
                IconButton(onClick = onSearchClick) {
                    Icon(Icons.Outlined.Search, null)
                }
            },
        ) {

            AnimatedTabs(
                modifier = Modifier.graphicsLayer { translationY = toolbarState.offset },
                categories = categories.map{it.categoryName},
                selectedTabIndex = selectedTabIndex,
                setSelectedTabIndex = {
                    newIndex ->
                    setSelectedTabIndex(newIndex)
                    toolbarState.collapse()
                }
            )
        }

    }
}


@Composable
fun Menus(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    contentPadding: PaddingValues,
    lazyListState: LazyListState,
    onItemClick: (String, String) -> Unit,
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = lazyListState
    ) {
        items(categories) { category ->
            CategorySection(category = category, onItemClick = {
                    restaurantId, menuId ->
                onItemClick(restaurantId, menuId)
            },)
            HorizontalDivider()

        }
    }
}

@Composable
fun CategorySection(category: Category, onItemClick: (String, String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)

    ) {

        Column {
            Text(
                text = category.categoryName,
                style = TextStyle(
                    fontSize = 24.sp, fontFamily = interBold
                ),
                modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 8.dp)
            )
            repeat(category.items.size) {

                MenuCard(
                    modifier = Modifier.clickable {
                        onItemClick(category.items[it].id!!, restaurantId)
                    }, menu = category.items[it]
                )

                HorizontalDivider(
                    color = gray6
                )
            }
        }
    }
}


@Composable
fun rememberToolbarState(toolbarHeightRange: IntRange): ToolbarState {
    return rememberSaveable(saver = CustomTopAppBarState.Saver) {
        CustomTopAppBarState(toolbarHeightRange)
    }
}


@Composable
fun rememberCustomNestedConnection(
    toolbarState: ToolbarState,
    lazyListState: LazyListState = rememberLazyListState(),
    scope: CoroutineScope,
) = remember {
    object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            toolbarState.scrollTopLimitReached =
                lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0
            toolbarState.scrollOffset = toolbarState.scrollOffset - available.y
            return Offset(0f, toolbarState.consumed)
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            if (available.y > 0) {
                scope.launch {
                    animateDecay(
                        initialValue = toolbarState.height + toolbarState.offset,
                        initialVelocity = available.y,
                        animationSpec = FloatExponentialDecaySpec()
                    ) { value, _ ->
                        toolbarState.scrollTopLimitReached =
                            lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0
                        toolbarState.scrollOffset =
                            toolbarState.scrollOffset - (value - (toolbarState.height + toolbarState.offset))
                        if (toolbarState.scrollOffset == 0f) scope.coroutineContext.cancelChildren()
                    }
                }
            }
            return super.onPostFling(consumed, available)
        }
    }
}



