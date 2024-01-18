package com.example.restaurant

import android.util.Log
import android.util.Log.d
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.core.ui.theme.inter
import com.example.core.ui.theme.interBold
import com.example.custom_toolbar.ToolbarState
import com.example.fooddeliver.home.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

const val TRIGGER_VISIBILITY_THRESHOLD = 0.6f

@Composable
fun CustomToolbar(
    modifier: Modifier = Modifier,
    progress: Float,
    coverImageUrl: String?,
    restaurantName: String,
    restaurantAddress: String,
    expandedActions: @Composable () -> Unit,
    collapsedActions: @Composable () -> Unit,
    navigationButton: @Composable () -> Unit,

    ) {

    Box(modifier = modifier) {
        RestaurantCoverImage(coverImageUrl = coverImageUrl)

        var expandedToolBarVisible by remember { mutableStateOf(true) }
        LaunchedEffect(progress) {
            expandedToolBarVisible = progress > TRIGGER_VISIBILITY_THRESHOLD
        }

        AnimatedVisibility(
            visible = expandedToolBarVisible, enter = fadeIn(
                animationSpec = tween(100),
                initialAlpha = 0.1f,
            ), exit = fadeOut(
                animationSpec = tween(100), targetAlpha = 0f
            )
        ) {
            ExpandedToolbar(
                modifier = Modifier,
                restaurantName = restaurantName,
                restaurantAddress = restaurantAddress,
                progress = progress,
                navigationButton = navigationButton,
                actions = expandedActions,
            )
        }

        AnimatedVisibility(
            visible = !expandedToolBarVisible, enter = fadeIn(
                animationSpec = tween(100), initialAlpha = 0.6f
            ), exit = fadeOut(
                animationSpec = tween(100),

                targetAlpha = 0f
            )
        ) {
            CollapsedToolbar(
                modifier = Modifier.align(Alignment.TopCenter),
                restaurantName = restaurantName,
                progress = progress,
                navigationButton = navigationButton,
                actions = collapsedActions
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
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .drawWithCache {
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
    modifier: Modifier,
    restaurantName: String,
    progress: Float,
    navigationButton: @Composable () -> Unit,
    actions: @Composable (() -> Unit)?,
) {

    Row(
        modifier = Modifier
            .background(color = colorScheme.primaryContainer.copy(alpha = 1 - progress))
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigationButton()

        Text(
            restaurantName, style = TextStyle(
                color = colorScheme.onSurface,
                fontSize = 24.sp,
                fontFamily = interBold,

                ), maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f)

        )

        if (actions != null) {
            actions()
        }
    }

}

@Composable
fun ExpandedToolbar(
    modifier: Modifier,
    restaurantName: String,
    restaurantAddress: String,
    progress: Float,
    navigationButton: @Composable () -> Unit,
    actions: @Composable (() -> Unit)?,
) {

    Column(verticalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navigationButton()

            Row {
                if (actions != null) {
                    actions()
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


@Composable
fun rememberCustomNestedConnection(
    toolbarState: ToolbarState,
    lazyListState: LazyListState,
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