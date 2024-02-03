package com.example.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.gray2
import com.example.core.ui.theme.interBold
import com.example.restaurant.Meal

// TODO: remove this function, it is redundant
@Composable
fun AnimatedTabs(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    selectedTabIndex: Int,
    setSelectedTabIndex: (Int) -> Unit,
) {

    TabSync(
        modifier = modifier,
        categories = categories,
        selectedTabIndex = selectedTabIndex
    ) { index, _ -> setSelectedTabIndex(index) }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TabSync(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    selectedTabIndex: Int,
    onTabSelect: (Int, Category) -> Unit,
) {
    PrimaryScrollableTabRow(modifier = modifier, selectedTabIndex = selectedTabIndex, edgePadding = 0.dp) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onTabSelect(index, category) },
                text = {
                    Text(
                        text = category.categoryName,
                        style = TextStyle(
//                            fontSize = 18.sp,
                            color = if(index == selectedTabIndex) colorScheme.primary else gray2,
                            fontFamily = interBold
                        )
                    )
                })
        }
    }
}

data class Category(val categoryName: String, val items: List<Meal>)

