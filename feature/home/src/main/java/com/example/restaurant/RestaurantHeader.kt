package com.example.restaurant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.custom_toolbar.ToolbarState
import com.example.data.models.RestaurantDto

@Composable
fun RestaurantHeader(
    toolbarState: ToolbarState,
    restaurant: RestaurantDto,
    foodItems: List<Category>,
    onNavigateUp: () -> Unit,
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit,
    onMoreClick: () -> Unit,
    onSearchClick: () -> Unit,
    selectedTabIndex: Int,
    setSelectedTabIndex: (Int) -> Unit,
) {
    RestaurantPageHeader(
        toolbarState = toolbarState,
        restaurant = restaurant,
        foodItems = foodItems,
        onNavigateUp = onNavigateUp,
        onFavoriteClick = onFavoriteClick,
        onShareClick = onShareClick,
        onMoreClick = onMoreClick,
        onSearchClick = onSearchClick,
        selectedTabIndex = selectedTabIndex,
        setSelectedTabIndex = setSelectedTabIndex,
    )
}


@Composable
internal fun RestaurantPageHeader(
    toolbarState: ToolbarState,
    restaurant: RestaurantDto,
    foodItems: List<Category>,
    onNavigateUp: () -> Unit,
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit,
    onMoreClick: () -> Unit,
    onSearchClick: () -> Unit,
    selectedTabIndex: Int,
    setSelectedTabIndex: (Int) -> Unit,
) {
    Column {
        CustomToolbar(
            coverImageUrl = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { toolbarState.height.toDp() }),
            progress = toolbarState.progress,
            restaurantName = restaurant.restaurantName,
            restaurantAddress = restaurant.restaurantAddress,
            navigationButton = {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        Icons.AutoMirrored.Outlined.ArrowBack,
                        null,
                        tint = if (toolbarState.progress > TRIGGER_VISIBILITY_THRESHOLD) Color.White else colorScheme.onSurface
                    )
                }
            },
            expandedActions = {
                IconButton(onClick = onFavoriteClick) {
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
                    Icon(Icons.Outlined.Search, null, tint = colorScheme.onSurface)
                }
            }

        )
        RestaurantInfo(
            restaurant = restaurant,
            modifier = Modifier
                .height(with(LocalDensity.current) { (toolbarState.infoSectionHeight).toDp() })
                .background(colorScheme.surface)
        )
        TabSync(
            modifier = Modifier,
            categories = foodItems,
            selectedTabIndex = selectedTabIndex,
            setSelectedTabIndex = setSelectedTabIndex
        )

    }
}

@Composable
fun RestaurantInfo(
    modifier: Modifier = Modifier,
    restaurant: RestaurantDto,
) {
    Box(
        modifier = modifier
            .shadow(10.dp, spotColor = Color.Transparent)
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(30.dp),

            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.StarBorder,
                    contentDescription = "Restaurant rating",
                    modifier = Modifier
                        .size(24.dp)
                )
                Text(restaurant.restaurantRating)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccessTime, contentDescription = "Time to deliver",
                )
                Text(restaurant.timeToDeliver)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn, contentDescription = "Distance",
                )
//                    TODO: add distance to restaurant dto
                Text("1.4km")
            }
        }

    }
}