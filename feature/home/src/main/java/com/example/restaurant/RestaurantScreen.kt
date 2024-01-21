package com.example.restaurant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmadhamwi.tabsync_compose.lazyListTabSync
import com.example.compose.gray6
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.core.ui.theme.interBold
import com.example.custom_toolbar.CollapsingToolbar
import com.example.custom_toolbar.CustomTopAppBarState
import com.example.custom_toolbar.FixedScrollFlagState
import com.example.custom_toolbar.ToolbarState
import com.example.data.models.RestaurantDto

val MIN_TOOLBAR_HEIGHT = 68.dp
val MAX_TOOLBAR_HEIGHT = 176.dp
val TAB_LAYOUT_HEIGHT = 40.dp
@Composable
fun RestaurantScreen(
    onNavigateUp: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    val viewModel: RestaurantViewModel = hiltViewModel()

//    make request using viewmodel
    val meals = listOf(
        Meal(
            id = "1",
            name = "Meal1",
            imageUrl = null,
            contents = "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa",
            price = "99.99",
            currency = "$"
        ), Meal(
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
    val restaurant = RestaurantDto(
        "test",
        "The Foodeck Shop",
        "Ankara, Golbasi",
        "40 min",
        "test",
        "test",
        "4.4",
    )

    FoodDeliveryTheme {
        Scaffold(

        ){
            it
            Restaurant(
                restaurant = restaurant,
                categories = categories,
                onNavigateUp = onNavigateUp,
                onItemClick = onItemClick,

                )
        }
    }
}


@Composable
internal fun Restaurant(
    restaurant: RestaurantDto,
    categories: List<Category>,
    onNavigateUp: () -> Unit,
    onItemClick: (String) -> Unit,
) {


    val toolbarRange = with(LocalDensity.current) {
        MIN_TOOLBAR_HEIGHT.roundToPx()..MAX_TOOLBAR_HEIGHT.roundToPx()
    }

    val toolbarState = rememberToolbarState(toolbarRange)
    val scope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()

    val (selectedTabIndex, setSelectedTabIndex) = lazyListTabSync(
        syncedIndices = categories.indices.toList(),
        lazyListState = lazyListState)

    val nestedScrollConnection = rememberCustomNestedConnection(
        lazyListState = lazyListState,
        toolbarState = toolbarState,
        scope = scope
    )
//    LaunchedEffect(key1 =  lazyListState.firstVisibleItemScrollOffset){
////        d("error", "${lazyListState.firstVisibleItemScrollOffset}")
////        this side effect statement fixes a bug where if the scrollOffset of the first element is
////        more than 0, it does not scroll back up until you scroll down to the second element and
////        then scroll back up
//    }
    Box(
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {

        Meals(
            modifier = Modifier
                .graphicsLayer { translationY = toolbarState.height + toolbarState.offset + TAB_LAYOUT_HEIGHT.toPx()}
            ,
            contentPadding = PaddingValues(bottom = TAB_LAYOUT_HEIGHT),
            categories = categories,
            lazyListState = lazyListState,
            onItemClick = onItemClick,
        )
        CollapsingToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { toolbarState.height.toDp() })
                .graphicsLayer { translationY = toolbarState.offset }
            ,

            coverImageUrl = null,
            progress = toolbarState.progress,
        ){
            Column{
                AnimatedTabs(
                    modifier = Modifier
                        .graphicsLayer { translationY = toolbarState.offset }
                    ,
                    categories = categories,
                    selectedTabIndex = selectedTabIndex,
                    setSelectedTabIndex = setSelectedTabIndex
                )
            }
        }

//        RestaurantHeader(
//            toolbarState = toolbarState,
//            restaurant = restaurant,
//            foodItems = categories,
//            onNavigateUp = onNavigateUp,
//            onFavoriteClick = {},
//            onShareClick = {},
//            onMoreClick = {},
//            onSearchClick = {},
//            selectedTabIndex = selectedTabIndex,
//            setSelectedTabIndex = setSelectedTabIndex,
//        )


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
        items(categories) {
            category ->

//                Text(
//                    text = category.categoryName,
//                    style = TextStyle(
//                        fontSize = 24.sp, fontFamily = interBold
//                    ),
//                    modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 8.dp)
//                )

            CategorySection(category = category, onItemClick = onItemClick)
//            Text("",
//                Modifier
//                    .padding(vertical = 70.dp)
//                    .fillMaxWidth())
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

