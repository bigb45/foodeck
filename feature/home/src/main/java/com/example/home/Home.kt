package com.example.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.theme.inter
import com.example.fooddeliver.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val number = 5
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var query by remember { mutableStateOf("") }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AddressTopAppBar(
                address = "Ankar, Kecioren, Baglarbasi Mahllesi", scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            BadgedFab(number)
        },
        bottomBar = {
            BottomNavBar()
        }

    ) { padding ->
        Column(
            Modifier.padding(padding)
        ) {
            CustomSearchBox(query = query, onValueChange = { query = it })
            Column(
                Modifier.fillMaxSize()
            ) {
                BentoSection()

            }
        }
    }


}


@Composable
private fun BentoSection() {
    val gradientBrush = Brush.verticalGradient(
        0.5f to Color.Transparent, 1F to Color.Black
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)

    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(8.dp)
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
                    Text("Candy", style = TextStyle(color = Color.White, fontSize = 20.sp))
                    Text("Sweet tooth!", style = TextStyle(color = Color.White, fontSize = 16.sp))

                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(Modifier.weight(1f)) {

                    Image(

                        painter = painterResource(id = R.drawable.cover),
                        contentDescription = "Food image",
                        Modifier
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
                        Text("Salty", style = TextStyle(color = Color.White, fontSize = 20.sp))
                        Text(
                            "Fill up on Sodium!",
                            style = TextStyle(color = Color.White, fontSize = 16.sp)
                        )

                    }
                }
                Box(Modifier.weight(1f)) {
                    Image(
                        painter = painterResource(id = R.drawable.cover),
                        contentDescription = "Food image",
                        Modifier
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
                        Text(
                            "Sweet & Sour", style = TextStyle(color = Color.White, fontSize = 20.sp)
                        )
                        Text(
                            "Order authentic chinese food",
                            style = TextStyle(color = Color.White, fontSize = 16.sp)
                        )

                    }
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
    ),
        title = {


            TextButton(
                onClick = { Log.d("error", "edit address") },
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
                        modifier = Modifier.weight(0.5f)
                    )
                    Text(
                        text = address,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 20.sp,
                        ),
                        modifier = Modifier.weight(4f)
                    )
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "open dropdown menu",
                        Modifier
                            .size(32.dp)
                            .weight(0.5f)
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
    query: String,
    onValueChange: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.primaryContainer)
//            .align(Alignment.CenterHorizontally)
    ) {
        OutlinedTextField(

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
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Outlined.Search, null) },
            label = { Text("Discover") })
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.FavoriteBorder, null) },
            label = { Text("Saved") })
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.Notifications, null) },
            label = { Text("Notifications") })
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.PersonOutline, null) },
            label = { Text("Profile") })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BadgedFab(number: Int) {
    Box {


        FloatingActionButton(
            onClick = { /*TODO*/ },
            Modifier.padding(5.dp)
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


@Composable
fun RestaurantCard() {

}

@Preview
@Composable
fun HomePrev() {
    HomeScreen()
}