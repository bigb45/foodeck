package com.example.restaurant

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.core.ui.theme.inter
import com.example.core.ui.theme.interBold
import kotlin.math.roundToInt


private val ContentPadding = 8.dp

private const val Alpha = 1f


@Composable
fun CollapsingToolbar(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    coverImageUrl: String? = null,
    progress: Float,
    onNavigateUp: () -> Unit,
    preContent: (@Composable () -> Unit)? = null,
    expandedActions: @Composable () -> Unit,
    collapsedActions: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val topBarItemsColor = animateColorAsState(
        targetValue = if (progress > 0.3) Color.White else colorScheme.onPrimaryContainer,
        animationSpec = tween(100),
        label = "animated top bar buttons and text color"
    )
    Column {

        Surface(
            color = colorScheme.primaryContainer, modifier = modifier
        ) {

            Box(modifier = Modifier.fillMaxSize()) {

                //#region Background Image
                RestaurantCoverImage(coverImageUrl = coverImageUrl, progress = progress)
                //#endregion
                Box(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = ContentPadding)
                        .fillMaxSize()
                ) {
                    CollapsingToolbarLayout(progress = progress) {
                        IconButton(onNavigateUp) {
                            Icon(Icons.AutoMirrored.Outlined.ArrowBack, null, tint = topBarItemsColor.value)
                        }
                        Text(
                            title,
                            style = TextStyle(
                                color = topBarItemsColor.value,
                                fontSize = 24.sp,
                                fontFamily = interBold,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.layoutId("title")

                        )

                        Row {
                            expandedActions()
                        }

                        collapsedActions()

                        Text(
                            text = subTitle,
                            color = topBarItemsColor.value,
                            fontSize = 18.sp,
                            fontFamily = inter,
                            modifier = Modifier.graphicsLayer {
                                alpha = progress
                            }
                        )

                    }
                }

            }

        }
        if (preContent != null) {
            preContent()
        }
        content()

    }
}

@Composable
private fun CollapsingToolbarLayout(
    progress: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier, content = content
    ) { measurables, constraints ->
        val placeables = measurables.filter { it.layoutId != "title" }.map {
                it.measure(constraints)
            }
        val navigationButton = placeables[0]
        val optionsRow = placeables[1]
        val searchButton = placeables[2]
        val address = placeables[3]

        val titleWidth = constraints.maxWidth - navigationButton.width - searchButton.width
        val titleConstraints = Constraints.fixedWidth(
            width = titleWidth
        )
        val titlePlaceable = measurables.first { it.layoutId == "title" }.measure(titleConstraints)


        val expandedHorizontalGuideline = (constraints.maxHeight * 0.4f).roundToInt()
        val collapsedHorizontalGuideline = (constraints.maxHeight * 0.5f).roundToInt()

        layout(
            width = constraints.maxWidth, height = constraints.maxHeight
        ) {
            navigationButton.placeRelative(
                x = 0, y = 30
            )


            // Place the title using the calculated width
            titlePlaceable.placeRelative(
                x = navigationButton.width + (-progress * 100).toInt().coerceIn(-100, 0),
                y = (progress * 300).toInt().coerceIn(60, 300)
            )

            address.placeRelative(
                x = navigationButton.width + (-progress * 100).toInt().coerceIn(-100, 0),
                y = (progress * 300).toInt().coerceIn(60, 300) + titlePlaceable.height
            )

            // Place either optionsRow or searchButton based on the progress value
            if (progress > 0.5) {
                optionsRow.placeRelative(
                    x = constraints.maxWidth - optionsRow.width, y = 30
                )
            } else {
                searchButton.placeRelative(
                    x = constraints.maxWidth - searchButton.width, y = 30
                )
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RestaurantCoverImage(coverImageUrl: String?, progress: Float) {
    GlideImage(
        model = coverImageUrl
            ?: com.example.fooddeliver.home.R.drawable.wallpaperflare_com_wallpaper,
        contentDescription = "Restaurant cover image",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                alpha = progress * Alpha
            }
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
        alignment = BiasAlignment(0f, 1f - ((1f - progress) * 0.75f)),

        )
}