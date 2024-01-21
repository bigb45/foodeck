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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.data.models.RestaurantDto


@Composable
fun RestaurantInfo(
    modifier: Modifier = Modifier,
    restaurant: RestaurantDto,
) {
    Box(
        modifier = modifier
//            .shadow(10.dp, spotColor = Color.Transparent)
            .fillMaxWidth()
            .height(100.dp)
            .background(colorScheme.surface)
            .padding(horizontal = 24.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(30.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()

        ) {
            InfoItem(Modifier
                .weight(1f)) {
                Icon(
                    imageVector = Icons.Rounded.StarBorder,
                    contentDescription = "Restaurant rating",
                    modifier = Modifier
                        .size(24.dp)
                )
                Text(restaurant.restaurantRating)
            }

            InfoItem(Modifier
                .weight(1f)){
                Icon(
                    imageVector = Icons.Outlined.AccessTime, contentDescription = "Time to deliver",
                )
                Text(restaurant.timeToDeliver)
            }

            InfoItem(Modifier
                .weight(1f)){
                Icon(
                    imageVector = Icons.Outlined.LocationOn, contentDescription = "Distance",
                )
                Text("1.4km")
            }
        }

    }
}

@Composable
fun InfoItem(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { }
            .height(80.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
    ) {
        content()
    }
}