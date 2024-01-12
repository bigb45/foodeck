@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)

package com.example.restaurant

import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ahmadhamwi.tabsync_compose.lazyListTabSync
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.custom_toolbar.CustomToolbar
import com.example.custom_toolbar.CustomTopAppBarState
import com.example.custom_toolbar.ToolbarState
import com.example.custom_toolbar.restaurantInfoHeight
import com.example.data.models.RestaurantDto
import kotlinx.coroutines.CoroutineScope


@Composable
fun RestaurantScreen(restaurantId: String = "0") {
    LaunchedEffect(Unit) {
        d("error", "navigated to restaurant with id: $restaurantId")
    }
//    data coming from previous page
    FoodDeliveryTheme {
        Restaurant(
            RestaurantDto(
                "test",
                "The Foodeck Shop",
                "Ankara, Golbasi",
                "40 min",
                "test",
                "test",
                "4.4",
            )
        )
    }
}


@Composable
internal fun Restaurant(restaurant: RestaurantDto) {
//    data coming from api after loading page
    val categories = listOf(
        Category("Popular", listOf("Popular1", "Popular2", "Popular3")),
        Category("Wraps", listOf("Popular1", "Popular2", "Popular3")),
        Category("Shwarma", listOf("Popular1", "Popular2", "Popular3")),
        Category("Drinks", listOf("Popular1", "Popular2", "Popular3")),
        Category("Sweet", listOf("Popular1", "Popular2", "Popular3")),
        Category("Extra", listOf("Popular1", "Popular2", "Popular3")),
        Category("Firindan lezzetleri", listOf("Popular1", "Popular2", "Popular3")),
    )

    val toolbarRange = with(LocalDensity.current) {
        68.dp.roundToPx()..200.dp.roundToPx()
    }

    val toolbarState = rememberToolbarState(toolbarRange)
    val scope = rememberCoroutineScope()

    val (selectedTabIndex, setSelectedTabIndex, lazyListState) =
        lazyListTabSync(syncedIndices = categories.indices.toList())

    Scaffold(topBar = {
        CustomToolbar(lazyListState = lazyListState,
            restaurant = restaurant,
            onNavigateUp = {},
            onFavoriteClick = {},
            onShareClick = {},
            onMoreClick = {},
            scope = scope,
            foodItems = categories,
            toolbarState = toolbarState,
            onSearchClick = {},
            selectedTabIndex = selectedTabIndex,
            setSelectedTabIndex = setSelectedTabIndex,
            content = {Meals(
                modifier = Modifier,
                categories = categories,
                lazyListState = lazyListState,
                toolbarState = toolbarState,
            )})}) {
            it


    }
}




@Composable
fun Meals(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    lazyListState: LazyListState,
    toolbarState: ToolbarState,
) {
    val restaurantInfoOffset = with(LocalDensity.current) {
        restaurantInfoHeight.toPx()
    }
    LazyColumn(modifier = modifier
        .fillMaxSize()
        .graphicsLayer {
//            clear the lazy column from the top bar
//            TODO: add content padding to ToolbarState
            translationY = toolbarState.height + toolbarState.offset /*+ toolbarState.infoSectionHeight*/

        }
        , verticalArrangement = Arrangement.spacedBy(16.dp), state = lazyListState
    ) {
        items(categories) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = it.categoryName, modifier = Modifier.padding(vertical = 10.dp))
                    repeat(10) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(60.dp)
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                        ) {
                            Text("$it big small")
                        }
                    }
                }
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