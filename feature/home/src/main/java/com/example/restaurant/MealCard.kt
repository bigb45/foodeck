package com.example.restaurant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.core.ui.theme.Typography
import com.example.fooddeliver.home.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MealCard(
    modifier: Modifier = Modifier,
    meal: Meal
) {
    Box(
        modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(110.dp)

    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
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

            Column {
                Text(meal.name, style = Typography.titleLarge)
                Text(meal.contents, style = Typography.bodyMedium)
                Text("${meal.currency}${meal.price}", style = Typography.labelLarge)
            }
        }
    }
}

data class Meal(
    val name: String,
    val imageUrl: String?,
    val contents: String,
    val price: String,
    val currency: String
)