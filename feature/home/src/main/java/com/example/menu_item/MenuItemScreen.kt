package com.example.menu_item

import android.util.Log.d
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.restaurant.CollapsingToolbar
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

    val nestedScrollConnection =
        rememberCustomNestedConnection(toolbarState, lazyListState, scope)
    val viewModel: MenuItemViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection)
    ) {

        CollapsingToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { toolbarState.height.toDp() }),
            coverImageUrl = null,
            progress = toolbarState.progress,
            onNavigateUp = onNavigateUp,
            expandedActions = { MealActions() },
            collapsedActions = { IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.Search, null)
            }},
            title = "Meal name",
            subTitle = "Restaurant name, address",
        ){

        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = lazyListState
        ) {
            items(100) {
                Text(viewModel.menuItemId)
            }
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