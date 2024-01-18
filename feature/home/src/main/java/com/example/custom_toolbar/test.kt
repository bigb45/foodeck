package com.example.custom_toolbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PrivacyTip
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.roundToInt


private val ContentPadding = 8.dp
private val Elevation = 4.dp
private val ButtonSize = 24.dp
private const val Alpha = 0.75f

private val ExpandedPadding = 1.dp
private val CollapsedPadding = 3.dp

private val ExpandedCostaRicaHeight = 20.dp
private val CollapsedCostaRicaHeight = 16.dp

private val ExpandedWildlifeHeight = 32.dp
private val CollapsedWildlifeHeight = 24.dp

private val MapHeight = CollapsedCostaRicaHeight * 2

@Composable
fun CollapsingToolbar(
    @DrawableRes backgroundImageResId: Int,
    progress: Float,
    onPrivacyTipButtonClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {


    Surface(
        color = colorScheme.primary,
        modifier = modifier
    ) {
        Box (modifier = Modifier.fillMaxSize()) {
            //#region Background Image
            Image(
                painter = painterResource(id = backgroundImageResId),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = progress * Alpha
                    },
                alignment = BiasAlignment(0f, 1f - ((1f - progress) * 0.75f))
            )
            //#endregion
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = ContentPadding)
                    .fillMaxSize()
            ) {
//                CollapsingToolbarLayout (progress = progress) {
//                    //#region Logo Images
//                    Text("hello1")
//                    Text("hello2")
//                    Text("hello3")
//                    Text("hello4")
//                    //#endregion
//                    //#region Buttons
//                    Row (
//                        modifier = Modifier.wrapContentSize(),
//                        horizontalArrangement = Arrangement.spacedBy(ContentPadding)
//                    ) {
//                        IconButton(
//                            onClick = onPrivacyTipButtonClicked,
//                            modifier = Modifier
//                                .size(ButtonSize)
//                                .background(
//                                    color = LocalContentColor.current.copy(alpha = 0.0f),
//                                    shape = CircleShape
//                                )
//                        ) {
//                            Icon(
//                                modifier = Modifier.fillMaxSize(),
//                                imageVector = Icons.Rounded.PrivacyTip,
//                                contentDescription = null,
//                            )
//                        }
//                        IconButton(
//                            onClick = onSettingsButtonClicked,
//                            modifier = Modifier
//                                .size(ButtonSize)
//                                .background(
//                                    color = LocalContentColor.current.copy(alpha = 0.0f),
//                                    shape = CircleShape
//                                )
//                        ) {
//                            Icon(
//                                modifier = Modifier.fillMaxSize(),
//                                imageVector = Icons.Rounded.Settings,
//                                contentDescription = null,
//                            )
//                        }
//                    }
//                    //#endregion
//                }
            }
        }
    }
}

// TODO: change the layout to make the title more like the way you want it fucking handle it yourself
@Composable
private fun CollapsingToolbarLayout(
    progress: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 5) // [0]: Country Map | [1-3]: Logo Images | [4]: Buttons

        val placeables = measurables.map {
            it.measure(constraints)
        }
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {

            val expandedHorizontalGuideline = (constraints.maxHeight * 0.4f).roundToInt()
            val collapsedHorizontalGuideline = (constraints.maxHeight * 0.5f).roundToInt()

            val countryMap = placeables[0]
            val costa = placeables[1]
            val rica = placeables[2]
            val wildlife = placeables[3]
            val buttons = placeables[4]
            countryMap.placeRelative(
                x = 0,
                y = collapsedHorizontalGuideline - countryMap.height / 2,
            )
            costa.placeRelative(
                x = lerp(
                    start = countryMap.width,
                    stop = constraints.maxWidth / 2 - costa.width,
                    fraction = progress
                ),
                y = lerp(
                    start = collapsedHorizontalGuideline - costa.height / 2,
                    stop = expandedHorizontalGuideline - costa.height,
                    fraction = progress
                )
            )
            rica.placeRelative(
                x = lerp(
                    start = countryMap.width + costa.width,
                    stop = constraints.maxWidth / 2 - rica.width,
                    fraction = progress
                ),
                y = lerp(
                    start = collapsedHorizontalGuideline - rica.height / 2,
                    stop = expandedHorizontalGuideline,
                    fraction = progress
                )
            )
            wildlife.placeRelative(
                x = lerp(
                    start = countryMap.width + costa.width + rica.width,
                    stop = constraints.maxWidth / 2,
                    fraction = progress
                ),
                y = lerp(
                    start = collapsedHorizontalGuideline - wildlife.height / 2,
                    stop = expandedHorizontalGuideline + rica.height / 2,
                    fraction = progress
                )
            )
            buttons.placeRelative(
                x = constraints.maxWidth - buttons.width,
                y = lerp(
                    start = (constraints.maxHeight - buttons.height) / 2,
                    stop = 0,
                    fraction = progress
                )
            )
        }
    }
}
