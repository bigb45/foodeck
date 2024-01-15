package com.example.restaurant

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.compose.gray1
import com.example.core.ui.theme.Typography
import com.example.core.ui.theme.interBold
import com.example.fooddeliver.home.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MealCard(
    modifier: Modifier = Modifier,
    meal: Meal,
//    state: MealState
) {

        Row(
            modifier = modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .height(110.dp)
                .fillMaxSize()
                    ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(64.dp)
            ) {
                GlideImage(model = meal.imageUrl ?: R.drawable.cover, contentDescription = "Food")
            }


            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(
                    space = 4.dp, alignment = Alignment.CenterVertically
                )
            ) {
                Text(meal.name, style = Typography.titleMedium)
                Text(meal.contents, style = Typography.bodyLarge.copy(color = gray1), maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(
                    "${meal.currency}${meal.price}",
                    style = Typography.titleMedium.copy(fontFamily = interBold),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .weight(0.2f)

            ) {
            if(false) //show delete box and/or number of items, if(state.isInCart)
                IconButton(onClick = { /*TODO*/ }, Modifier.align(Alignment.Center)) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }


    }
}

data class Meal(
    val id: String? = "0",
    val name: String,
    val imageUrl: String?,
    val contents: String,
    val price: String,
    val currency: String,
)