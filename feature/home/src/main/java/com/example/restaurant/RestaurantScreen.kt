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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.ahmadhamwi.tabsync_compose.lazyListTabSync
import com.example.common.AnimatedTabs
import com.example.common.Category
import com.example.common.CollapsingToolbar
import com.example.compose.gray6
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.core.ui.theme.interBold
import com.example.custom_toolbar.CustomTopAppBarState
import com.example.custom_toolbar.ToolbarState
import com.example.data.models.Restaurant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

val MIN_TOOLBAR_HEIGHT = 68.dp
val MAX_TOOLBAR_HEIGHT = 176.dp
val TAB_LAYOUT_HEIGHT = 40.dp

@Composable
fun RestaurantScreen(
    onNavigateUp: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    val viewModel: RestaurantViewModel = hiltViewModel()

    LaunchedEffect(Unit){
        viewModel.fetchRestaurantDetails()
    }

//    make request using viewmodel
    val meals = listOf(
        Meal(
            id = "1",
            name = "Meal1",
            imageUrl = null,
            contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
            price = "99.99",
            currency = "$"
        ),
        Meal(
            id = "2",
            name = "Meal2",
            imageUrl = null,
            contents = "1 regular burger with croquette and hot cocoa",
            price = "99.99",
            currency = "$"
        ),
        Meal(
            id = "3",
            name = "Meal3",
            imageUrl = null,
            contents = "1 regular burger with croquette and hot cocoa",
            price = "99.99",
            currency = "$"
        ),
    )
    val categories = listOf(
        Category(categoryName = "Popular", items = meals),
        Category(categoryName = "Wraps", items = meals),
        Category(categoryName = "Shwarma", items = meals),
        Category(categoryName = "Drinks", items = meals),
        Category(categoryName = "Sweet", items = meals),
        Category(categoryName = "Extra", items = meals),
        Category(
            categoryName = "Firindan lezzetler", items = meals
        ),
    )
//   data coming from previous page
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
            Restaurant(
                restaurant = restaurant,
                categories = categories,
                onNavigateUp = onNavigateUp,
                onItemClick = onItemClick,
                onShareClick = {},
                onMoreClick = {},
                onFavoriteClick = {},
                onSearchClick = {}
            )
        }
    }
}


@Composable
internal fun Restaurant(
    restaurant: Restaurant,
    categories: List<Category>,
    onNavigateUp: () -> Unit,
    onItemClick: (String) -> Unit,
    onShareClick: () -> Unit,
    onMoreClick: () -> Unit,
    onFavoriteClick: (String) -> Unit,
    onSearchClick: () -> Unit,
) {


    val toolbarRange = with(LocalDensity.current) {
        MIN_TOOLBAR_HEIGHT.roundToPx()..MAX_TOOLBAR_HEIGHT.roundToPx()
    }

    val toolbarState = rememberToolbarState(toolbarRange)
    val scope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()

    val (selectedTabIndex, setSelectedTabIndex) = lazyListTabSync(
        syncedIndices = categories.indices.toList(),
        lazyListState = lazyListState
    )

    val nestedScrollConnection = rememberCustomNestedConnection(
        lazyListState = lazyListState,
        toolbarState = toolbarState,
        scope = scope
    )
    Box(
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {

        Meals(
            modifier = Modifier
                .graphicsLayer {
                    translationY =
                        toolbarState.height + toolbarState.offset + TAB_LAYOUT_HEIGHT.toPx() + (100.dp.toPx() * toolbarState.progress)
                },
            contentPadding = PaddingValues(bottom = TAB_LAYOUT_HEIGHT),
            categories = categories,
            lazyListState = lazyListState,
            onItemClick = onItemClick,
        )
        CollapsingToolbar(
            title = restaurant.restaurantName,
            subTitle = restaurant.restaurantAddress,
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { toolbarState.height.toDp() })
                .graphicsLayer { translationY = toolbarState.offset },
            coverImageUrl = restaurant.coverImageUrl,
            progress = toolbarState.progress,
            onNavigateUp = onNavigateUp,
            preContent = {
                RestaurantInfo(modifier = Modifier

                    .height(with(LocalDensity.current) { (toolbarState.progress * 100).dp })
                    .graphicsLayer { translationY = toolbarState.offset }
                    ,
                    restaurant = restaurant)
            },
            expandedActions = {
                IconButton(onClick =
                { onFavoriteClick(restaurant.restaurantId) }
                ) {
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
            Column {
                AnimatedTabs(
                    modifier = Modifier
                        .graphicsLayer { translationY = toolbarState.offset },
                    categories = categories,
                    selectedTabIndex = selectedTabIndex,
                    setSelectedTabIndex = setSelectedTabIndex
                )
            }
        }
    }
}


@Composable
fun Meals(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    contentPadding: PaddingValues,
    lazyListState: LazyListState,
    onItemClick: (String) -> Unit,
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp), state = lazyListState
    ) {
        items(categories) { category ->
            CategorySection(category = category, onItemClick = onItemClick)
            HorizontalDivider()

        }
    }
}

@Composable
fun CategorySection(category: Category, onItemClick: (String) -> Unit) {
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

                MealCard(
                    modifier = Modifier.clickable {
                        onItemClick(category.items[it].id!!)
                    }, meal = category.items[it]
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



