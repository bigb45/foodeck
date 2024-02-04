package com.example.main_screen

import android.util.Log.d
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.common.LoadingIndicator
import com.example.compose.gray2
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.core.ui.theme.Typography
import com.example.core.ui.theme.inter
import com.example.core.ui.theme.interBold
import com.example.data.models.Offer
import com.example.data.models.Restaurant
import com.example.fooddeliver.home.R
import com.example.restaurant.RestaurantState
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onRestaurantClick: (restaurantId: String) -> Unit,
) {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if(uiState !is MainScreenUiState.Success){
            viewModel.fetchRestaurantData()
        }
    }

    val pullToRefreshState = rememberPullToRefreshState()
    val number = 2
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val scaleFraction = if (pullToRefreshState.isRefreshing) 1f else
        LinearOutSlowInEasing.transform(pullToRefreshState.progress).coerceIn(0f, 1f)

    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            viewModel.reload()
            pullToRefreshState.endRefresh()
        }
    }

    FoodDeliveryTheme {
        Scaffold(modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            AddressTopAppBar(
                address = "Ankara, Kecioren, Baglarbasi Mahllesi",
                scrollBehavior = scrollBehavior
            )
        }, floatingActionButton = {
            BadgedFab(number) { onRestaurantClick("2") }
        }, bottomBar = {
            BottomNavBar()
        }

        ) {
            padding ->
            Box(
                modifier = Modifier
                    .padding(
                        padding
                    )
                    .fillMaxSize()
                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
            ){
                when (uiState) {

                    is MainScreenUiState.Success -> {
                        val restaurants = (uiState as MainScreenUiState.Success).restaurants
                        val offers = (uiState as MainScreenUiState.Success).offers

                        Home(
                            restaurants = restaurants,
                            offers = offers,
                            onRestaurantClick = onRestaurantClick,
                        )


                    }

                    MainScreenUiState.Loading -> {
                        LoadingIndicator()
                    }

//            TODO: Error state
                    is MainScreenUiState.Error -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ){
                           item {
                                Text(
                                    "${(uiState as MainScreenUiState.Error).message}",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                }
                PullToRefreshContainer(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .graphicsLayer(scaleX = scaleFraction, scaleY = scaleFraction),
                    state = pullToRefreshState,
                )
            }
        }
    }
}

@Composable
fun Home(
    restaurants: List<Restaurant>,
    offers: List<Offer>,
    onRestaurantClick: (String) -> Unit,
) {


    var query by remember { mutableStateOf("") }

        LazyColumn(
            Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 56.dp)

        ) {
            item {
                CustomSearchBox(query = query, onValueChange = { query = it })
            }

            item {
                BentoSection(modifier = Modifier.padding(16.dp))
            }
            item {
                CarrouselCards(
                    modifier = Modifier.padding(vertical = 16.dp), offers
                )
            }

            item {
                DealsSection(
                    modifier = Modifier.padding(vertical = 16.dp),
                    restaurants = restaurants,
                    onRestaurantClick = onRestaurantClick
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        "Explore More", style = TextStyle(
                            fontWeight = FontWeight.W900,
                            fontSize = 20.sp,
                            fontFamily = interBold
                        )
                    )
                }
            }
            items(restaurants) {
                RestaurantCard(
                    modifier = Modifier.padding(16.dp),
                    boxModifier = Modifier.height(240.dp),
                    restaurant = it,
                    onRestaurantClick
                )
            }

    }
}



@Composable
private fun BentoSection(modifier: Modifier = Modifier) {
    val gradientBrush = Brush.verticalGradient(
        0.5f to Color.Transparent, 1F to Color.Black.copy(alpha = 0.5f)
    )

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.wallpaperflare_com_wallpaper),
                    contentDescription = "Food image",
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(16.dp))
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(gradientBrush)
                            }
                        },
                )
                Column(
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                ) {
                    Text(text = "Candy", style = TextStyle(color = Color.White, fontSize = 20.sp))
                    Text("Sweet tooth!", style = TextStyle(color = Color.White, fontSize = 16.sp))

                }
            }
            Row(
                horizontalArrangement = Arrangement.Absolute.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(Modifier.weight(1f)) {
                    Image(
                        painter = painterResource(id = R.drawable.wallpaperflare_com_wallpaper),
                        contentDescription = "Food image",
                        Modifier
                            .height(160.dp)
                            .clip(shape = RoundedCornerShape(16.dp))
                            .drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(gradientBrush)
                                }
                            },
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        Modifier
                            .align(Alignment.BottomStart)
                            .padding(10.dp)
                    ) {
                        Text("Salty", style = TextStyle(color = Color.White, fontSize = 20.sp))
                        Text(
                            "Fill up on Sodium!",
                            style = TextStyle(color = Color.White, fontSize = 16.sp)
                        )

                    }
                }
                Box(Modifier.weight(1f)) {
                    Image(
                        painter = painterResource(id = R.drawable.wallpaperflare_com_wallpaper),
                        contentDescription = "Food image",
                        Modifier
                            .height(160.dp)
                            .clip(shape = RoundedCornerShape(16.dp))
                            .drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(gradientBrush)
                                }
                            },
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        Modifier
                            .align(Alignment.BottomStart)
                            .padding(10.dp)
                    ) {
                        Text("Salty", style = TextStyle(color = Color.White, fontSize = 20.sp))
                        Text(
                            "Fill up on Sodium!",
                            style = TextStyle(color = Color.White, fontSize = 16.sp)
                        )

                    }
                }
            }
        }
    }
}


@Composable
fun DealsSection(
    modifier: Modifier = Modifier,
    restaurants: List<Restaurant>,
    onRestaurantClick: (String) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                "Deals", style = TextStyle(
                    fontWeight = FontWeight.W900, fontSize = 20.sp, fontFamily = interBold
                )
            )
            Icon(Icons.AutoMirrored.Outlined.ArrowForward, null)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)

        ) {
            repeat(restaurants.size) {
                with(restaurants[it]) {
                    RestaurantCard(
                        modifier = Modifier
                            .width(240.dp)
                            .height(160.dp),
                        boxModifier = Modifier.height(240.dp),
                        restaurant = this,
                        onRestaurantClick = onRestaurantClick
                    )
                }

            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddressTopAppBar(address: String, scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = colorScheme.primaryContainer,
        titleContentColor = colorScheme.primary,
    ), title = {


        TextButton(
            onClick = { d("error", "edit address") },
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,

                ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "delivery address",
                    modifier = Modifier.weight(0.5f),
                    tint = colorScheme.onPrimaryContainer
                )
                Text(
                    text = address,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier.weight(4f)
                )
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "open dropdown menu",
                    Modifier
                        .size(32.dp)
                        .weight(0.5f),
                    tint = colorScheme.onPrimaryContainer
                )
            }
        }
    }, actions = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Outlined.Menu, contentDescription = "Menu")
        }
    }, scrollBehavior = scrollBehavior
    )
}

@Composable
private fun CustomSearchBox(
    modifier: Modifier = Modifier,
    query: String,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(colorScheme.primaryContainer)
    ) {
        OutlinedTextField(
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {

                    focusManager.clearFocus()
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorScheme.onPrimary,
                unfocusedContainerColor = colorScheme.onPrimary,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = colorScheme.surfaceTint,
                    modifier = Modifier.size(20.dp)
                )
            },
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(
                    "Search",
                    style = TextStyle(
                        fontFamily = inter, fontSize = 20.sp, color = colorScheme.surfaceTint
                    ),
                )
            },
            value = query,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                fontFamily = inter, fontSize = 20.sp, color = colorScheme.surfaceTint
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
private fun BottomNavBar() {
    NavigationBar {
        NavigationBarItem(selected = true,
            onClick = { },
            icon = { Icon(Icons.Outlined.Search, null) },
            label = { Text("Discover") })
        NavigationBarItem(selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.FavoriteBorder, null) },
            label = { Text("Saved") })
        NavigationBarItem(selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.Notifications, null) },
            label = { Text("Notifications") })
        NavigationBarItem(selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.Person, null) },
            label = { Text("Profile") })
    }
}

@Composable
private fun BadgedFab(number: Int, onClick: () -> Unit) {
    Box {


        FloatingActionButton(
            onClick = onClick, Modifier.padding(5.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = null,
                tint = colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .size(24.dp)

            )
        }
        Badge(
            Modifier
                .size(20.dp)
                .align(Alignment.TopEnd)
        ) {
            Text("$number", style = TextStyle(fontSize = 14.sp))
        }
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
// TODO: pass a callback to know which item was clicked
fun CarrouselCards(modifier: Modifier = Modifier, items: List<Offer>) {

    val pagerState = rememberPagerState {
        items.size
    }

    HorizontalPager(
        contentPadding = PaddingValues(horizontal = 16.dp),
        pageSpacing = 4.dp,
        state = pagerState,
        modifier = modifier,
    ) { index ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            val offer = items[index]
            GlideImage(modifier = Modifier

                .graphicsLayer {

                    val pageOffset =
                        ((pagerState.currentPage - index) + pagerState.currentPageOffsetFraction).absoluteValue
                    val scale = lerp(
                        start = 0.8f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    scaleY = scale
                }
                .clip(shape = RoundedCornerShape(16.dp))
                .align(Alignment.Center)
                .drawWithCache {
                    val brush = Brush.verticalGradient(
                        0.4f to Color.Transparent, 1f to Color.Black
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(brush)
                    }
                },
                model = offer.offerImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .fillMaxWidth(0.7f)

            ) {
                Text(
                    offer.offerName,
                    style = TextStyle(
                        fontFamily = interBold,
                        fontSize = 48.sp,
                        color = Color.White
                    ),
                )

            }
        }

    }
    PagerIndicatorRow(pagerState.pageCount, pagerState.currentPage)
}

@Composable
fun PagerIndicatorRow(pageCount: Int, selectedPage: Int, modifier: Modifier = Modifier) {
    Row(
        modifier
            .height(30.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) {
            PagerIndicator(isSelected = it == selectedPage)
        }
    }

}

@Composable
private fun PagerIndicator(isSelected: Boolean) {
    val selectedWidth = 8.dp
    val unselectedWidth = 6.dp
    val animatedColor by animateColorAsState(
        targetValue = (if (isSelected) colorScheme.inversePrimary else gray2), label = "background"
    )
    val animatedWidth by animateDpAsState(

        if (isSelected) {
            selectedWidth
        } else {
            unselectedWidth
        }, animationSpec = tween(300), label = "size"
    )
    Box(
        modifier = Modifier
            .padding(2.dp)
            .clip(CircleShape)
            .background(animatedColor)
            .size(animatedWidth)


    )

}


private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (1 - fraction) * start + fraction * stop
}

// TODO: Move the restaurant card to its own file
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RestaurantCard(
    modifier: Modifier = Modifier,
    boxModifier: Modifier = Modifier,
    restaurant: Restaurant = Restaurant(),
    onRestaurantClick: ((String) -> Unit)? = null,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier.fillMaxWidth()

    ) {

        Box(modifier = boxModifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable {
                if (onRestaurantClick != null) {
                    onRestaurantClick(restaurant.restaurantId)
                }
            }

        ) {


            GlideImage(
                modifier = Modifier,
                model = restaurant.coverImageUrl ?: R.drawable.wallpaperflare_com_wallpaper,
                contentDescription = "restaurant image",
                contentScale = ContentScale.Crop,
                transition = CrossFade(tween(500)),

                )

            Icon(Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = colorScheme.surface,
                modifier = Modifier
                    .padding(16.dp)
                    .shadow(elevation = 16.dp, shape = CircleShape, ambientColor = Color.Black)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .background(colorScheme.primaryContainer.copy(alpha = 0.6f))
                    .clickable {
//                        TODO: add restaurant to favorites
                        d("error", "added restaurant to favorites")
                    }
                    .padding(5.dp)
                    .size(24.dp))


            CustomBadge(
                text = "${restaurant.timeToDeliver} min",

                Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )

        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(
                    restaurant.restaurantName,
                    style = Typography.titleLarge,
                )
                Text(
                    restaurant.restaurantAddress, style = TextStyle(
                        color = gray2, fontSize = 16.sp, fontFamily = inter
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Rounded.Star, tint = colorScheme.primary, contentDescription = null)
                Text(
                    restaurant.restaurantRating, style = Typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun CustomBadge(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .shadow(16.dp)
            .background(Color.White, shape = RoundedCornerShape(100))
            .padding(6.dp)
    ) {
        Text(text, style = TextStyle(fontFamily = interBold))
    }
}

@Preview
@Composable
fun HomePrev() {
    MainScreen {}
}
