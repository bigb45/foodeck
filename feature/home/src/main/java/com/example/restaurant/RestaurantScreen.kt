package com.example.restaurant

import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmadhamwi.tabsync_compose.lazyListTabSync
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.core.ui.theme.interBold
import com.example.custom_toolbar.CustomTopAppBarState
import com.example.custom_toolbar.ToolbarState
import com.example.data.models.RestaurantDto

const val MIN_TOOLBAR_HEIGHT = 68
const val MAX_TOOLBAR_HEIGHT = 200

@Composable
fun RestaurantScreen(restaurantId: String = "0", onNavigateUp: () -> Unit) {
    //    data coming from api after loading the page
    LaunchedEffect(Unit){
        d("error", restaurantId)
    }
    val meals = listOf(
        Meal(
            "Meal1", null, "1 regular burger with croquette and hot cocoa1 regular burger with croquette and hot cocoa", "99.99", "$"
        ), Meal(
            "Meal2", null, "1 regular burger with croquette and hot cocoa", "99.99", "$"
        ), Meal(
            "Meal3", null, "1 regular burger with croquette and hot cocoa", "99.99", "$"
        )
    )
    val categories = listOf(
        Category(categoryName = "Popular", items = meals),
        Category(categoryName = "Wraps", items = meals),
        Category(categoryName = "Shwarma", items = meals),
        Category(categoryName = "Drinks", items = meals),
        Category(categoryName = "Sweet", items = meals),
        Category(categoryName = "Extra", items = meals),
        Category(
            categoryName = "Firindan lezzetleri", items = meals
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
        Restaurant(
            restaurant = restaurant,
            categories = categories,
            onNavigateUp = onNavigateUp
        )
    }
}


@Composable
internal fun Restaurant(restaurant: RestaurantDto, categories: List<Category>, onNavigateUp: () -> Unit) {


    val toolbarRange = with(LocalDensity.current) {
        MIN_TOOLBAR_HEIGHT.dp.roundToPx()..MAX_TOOLBAR_HEIGHT.dp.roundToPx()
    }

    val toolbarState = rememberToolbarState(toolbarRange)
    val scope = rememberCoroutineScope()

    val (selectedTabIndex, setSelectedTabIndex, lazyListState) = lazyListTabSync(syncedIndices = categories.indices.toList())

    val nestedScrollConnection = rememberCustomNestedConnection(toolbarState, lazyListState, scope)

    Column(
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {

        RestaurantHeader(
            toolbarState = toolbarState,
            restaurant = restaurant,
            foodItems = categories,
            onNavigateUp = onNavigateUp,
            onFavoriteClick = {},
            onShareClick = {},
            onMoreClick = {},
            onSearchClick = {},
            selectedTabIndex = selectedTabIndex,
            setSelectedTabIndex = setSelectedTabIndex,
        )
        Meals(
            modifier = Modifier,
            categories = categories,
            lazyListState = lazyListState,
        )

    }
}


@Composable
fun Meals(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    lazyListState: LazyListState,
) {
    LazyColumn(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp), state = lazyListState
    ) {
        items(categories) {
            CategorySection(category = it)
        }
    }
}

@Composable
fun CategorySection(category: Category) {
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
                            d(
                                "error",
                                "clicked item ${category.items[it]} in category ${category.categoryName}"
                            )
                        }, meal = category.items[it]
                )

                HorizontalDivider(
                    color = colorScheme.surface
                )
            }
        }
    }
}


@Composable
private fun rememberToolbarState(toolbarHeightRange: IntRange): ToolbarState {
    return rememberSaveable(saver = CustomTopAppBarState.Saver) {
        CustomTopAppBarState(toolbarHeightRange)
    }
}

@Preview
@Composable
fun RestaurantScreenPreview() {
     RestaurantScreen(
        "test"
     ) {}
}