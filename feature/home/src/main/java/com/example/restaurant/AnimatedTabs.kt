package com.example.restaurant

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.compose.gray2
import com.example.core.ui.theme.interBold

@Composable
fun TabSync(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    selectedTabIndex: Int,
    setSelectedTabIndex: (Int) -> Unit,
) {

    TabSyncInternal(
        modifier = modifier.fillMaxWidth(),
        categories,
        selectedTabIndex
    ) { index, _ -> setSelectedTabIndex(index) }

}

@Composable
internal fun TabSyncInternal(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    selectedTabIndex: Int,
    onTabSelect: (Int, Category) -> Unit,
) {
    ScrollableTabRow(modifier = modifier, selectedTabIndex = selectedTabIndex, edgePadding = 0.dp) {
        categories.forEachIndexed { index, category ->
            Tab(selected = index == selectedTabIndex,
                onClick = { onTabSelect(index, category) },
                text = {
                    Text(
                        text = category.categoryName,
                        style = TextStyle(
                            color = if(index == selectedTabIndex) colorScheme.primary else gray2,
                            fontFamily = interBold
                        )
                    )
                })
        }
    }
}

data class Category(val categoryName: String, val items: List<Meal>)

