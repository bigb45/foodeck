package com.example.menu_item

import android.util.Log.d
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.gray2
import com.example.compose.gray6
import com.example.core.ui.theme.Typography
import com.example.core.ui.theme.interBold
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

    val nestedScrollConnection = rememberCustomNestedConnection(toolbarState, lazyListState, scope)
    val viewModel: MenuItemViewModel = hiltViewModel()

    Column(
        modifier = Modifier.nestedScroll(nestedScrollConnection)
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
            collapsedActions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.Search, null)
                }
            },
            title = "Meal name",
            subTitle = "Restaurant name, address",
        ) {

        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Counter(modifier = Modifier,
                    counter = viewModel.counter.collectAsState().value,
                    increment = { viewModel.incrementCounter() },
                    decrement = { viewModel.decrementCounter() })
            }
            item {
                Instructions(onTextChange = { /*TODO*/ }, text = "")
            }
        }
    }

}


@Composable
fun Counter(
    modifier: Modifier = Modifier,
    counter: Int,
    increment: () -> Unit,
    decrement: () -> Unit,

    ) {
    Column(
        modifier = modifier
            .background(colorScheme.surface)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Quantity", style = Typography.titleLarge.copy(fontFamily = interBold))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = gray6)
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { decrement() }, modifier = Modifier.weight(1f)) {
                Icon(imageVector = Icons.Outlined.Remove, contentDescription = null, tint = gray2)
            }
            Text(
                counter.toString(),
                style = Typography.titleLarge,
                modifier = Modifier
                    .weight(5f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = { increment() }, modifier = Modifier.weight(1f)
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null, tint = gray2)
            }
        }
    }
}

@Composable
fun Instructions(
    modifier: Modifier = Modifier,
    onTextChange: () -> Unit,
    text: String,
) {
    Column(
        modifier = modifier
            .background(colorScheme.surface)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Instructions", style = Typography.titleLarge.copy(fontFamily = interBold))
        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Let us know if you have specific things in mind",
                style = Typography.bodyLarge.copy(color = gray2)
            )
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = gray6)
                .height(54.dp),
                value = text,
                onValueChange = { onTextChange() },
                shape = RoundedCornerShape(16.dp),
                placeholder = {
                    Text(
                        "e.g. less spices, no mayo etc",
                        style = Typography.titleMedium.copy(color = gray2)
                    )
                }

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