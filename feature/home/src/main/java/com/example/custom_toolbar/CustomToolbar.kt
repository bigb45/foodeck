package com.example.custom_toolbar

import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.ArrowBack
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.core.ui.theme.inter
import com.example.core.ui.theme.interBold
import com.example.data.models.RestaurantDto
import com.example.fooddeliver.home.R
import com.example.restaurant.Category
import com.example.restaurant.TabSync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

val restaurantInfoHeight = 120.dp


@Composable
fun RestaurantPageHeader(
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
        CollapsingToolbar(
            coverImageUrl = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { toolbarState.height.toDp() })
                .graphicsLayer { translationY = toolbarState.offset },
            progress = toolbarState.progress,
            restaurantName = restaurant.restaurantName,
            restaurantAddress = restaurant.restaurantAddress,
            onNavigateUp = onNavigateUp,
            onFavoriteClick = onFavoriteClick,
            onShareClick = onShareClick,
            onMoreClick = onMoreClick,
            onSearchClick = onSearchClick,

            )
//        RestaurantInfo(restaurant = restaurant,
//            modifier = Modifier
//
//                .height(with(LocalDensity.current) { (toolbarState.infoSectionHeight).toDp() }) /*TODO: toolbarState.bottomSectionHeight*/
//                .graphicsLayer { translationY = toolbarState.offset }
////                TODO: calculate height of info section in toolbarState
//                .background(colorScheme.surface)
//        ) {
//
//        }
//        //        TODO: translate this based on scroll position
//        TabSync(modifier = Modifier.graphicsLayer { translationY = toolbarState.offset },
//            categories = foodItems,
//            selectedTabIndex = selectedTabIndex,
//            setSelectedTabIndex = setSelectedTabIndex)
    }
}

@Composable
fun RestaurantInfo(
    modifier: Modifier = Modifier,
    restaurant: RestaurantDto,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier

            .height(restaurantInfoHeight)
            .fillMaxWidth()
            .shadow(10.dp, spotColor = Color.Transparent)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceAround,

            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Rounded.StarBorder, contentDescription = "Restaurant rating"
                )
                Text(restaurant.restaurantRating)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccessTime, contentDescription = "Time to deliver"
                )
                Text(restaurant.timeToDeliver)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn, contentDescription = "Distance"
                )
//                    TODO: add distance to restaurant dto
                Text("1.4km")
            }
        }

//        Box(Modifier.align(Alignment.BottomCenter)) { content() }
    }
}


// Glenn's code
@Composable
fun CustomToolbar(
    toolbarState: ToolbarState,
    scope: CoroutineScope,
    lazyListState: LazyListState,
    restaurant: RestaurantDto,
    selectedTabIndex: Int,
    foodItems: List<Category>,
    onNavigateUp: () -> Unit,
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit,
    onMoreClick: () -> Unit,
    onSearchClick: () -> Unit,
    setSelectedTabIndex: (Int) -> Unit,
    content: @Composable () -> Unit,
) {


    val nestedScrollConnection = remember {
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
                        ) { value, velocity ->
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

    Box(
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {

        content()

        RestaurantPageHeader(
            toolbarState = toolbarState,
            restaurant = restaurant,
            foodItems = foodItems,
            onNavigateUp = onNavigateUp,
            onFavoriteClick = onFavoriteClick,
            onShareClick = onShareClick,
            onMoreClick = onMoreClick,
            onSearchClick = onSearchClick,
            selectedTabIndex,
            setSelectedTabIndex,
        )

    }
}


@Composable
fun CollapsingToolbar(
    modifier: Modifier = Modifier,
    coverImageUrl: String?,
    progress: Float,
    restaurantName: String,
    restaurantAddress: String,
    onNavigateUp: () -> Unit,
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit,
    onMoreClick: () -> Unit,
    onSearchClick: () -> Unit,

    ) {

    Box(modifier = modifier) {
        RestaurantCoverImage(coverImageUrl = coverImageUrl)

        if (progress > 0.9) {
            ExpandedToolbar(
                restaurantName = restaurantName,
                restaurantAddress = restaurantAddress,
                progress = progress,
                onNavigateUp = onNavigateUp,
                onFavoriteClick = onFavoriteClick,
                onShareClick = onShareClick,
                onMoreClick = onMoreClick,
            )
        } else {
            CollapsedToolbar(
                restaurantName = restaurantName,
                progress = progress,
                onNavigateUp = onNavigateUp,
                onSearchClick = onSearchClick
            )
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RestaurantCoverImage(coverImageUrl: String?) {
    GlideImage(
        model = coverImageUrl ?: R.drawable.wallpaperflare_com_wallpaper,
        contentDescription = "Restaurant cover image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.drawWithCache {
            onDrawWithContent {
                val gradientDown = Brush.verticalGradient(
                    0.6f to Color.Black.copy(alpha = 0.4f), 1f to Color.Transparent
                )
                val gradientUp = Brush.verticalGradient(
                    0.1f to Color.Transparent, 1f to Color.Black.copy(alpha = 0.4f)
                )
                drawContent()
                drawRect(gradientDown)
                drawRect(gradientUp)
            }
        },

        )
}

@Composable
fun CollapsedToolbar(
    restaurantName: String,
    progress: Float,
    onNavigateUp: () -> Unit,
    onSearchClick: () -> Unit,
) {
    Row(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer {
            alpha = 1 - progress
        }
        .background(color = colorScheme.primary.copy(alpha = 1 - progress)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onNavigateUp) {
            Icon(Icons.Outlined.ArrowBack, null, tint = Color.White)
        }
        Text(
            restaurantName, style = TextStyle(
                color = colorScheme.onSecondary,
                fontSize = 24.sp,
                fontFamily = interBold,

                ), maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f)

        )
        IconButton(onClick = onSearchClick) {
            Icon(Icons.Outlined.Search, null, tint = Color.White)
        }
    }
}

@Composable
fun ExpandedToolbar(
    restaurantName: String,
    restaurantAddress: String,
    onNavigateUp: () -> Unit,
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit,
    onMoreClick: () -> Unit,
    progress: Float,
) {

    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateUp) {
                Icon(Icons.Outlined.ArrowBack, null, tint = Color.White)
            }
            Row(
                modifier = Modifier

            ) {
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
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .graphicsLayer {
                alpha = progress
            }) {

            Text(
                restaurantName,
                style = TextStyle(
                    color = colorScheme.onSecondary,
                    fontSize = 24.sp,
                    fontFamily = interBold,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,

                )
            Text(
                restaurantAddress,
                color = colorScheme.onSecondary,
                fontSize = 18.sp,
                fontFamily = inter,

                )


        }
//        TODO: add restaurant info here
    }
}