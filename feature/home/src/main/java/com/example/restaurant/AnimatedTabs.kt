package com.example.restaurant

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
                        category.categoryName
                    )
                })
        }
    }
}

data class Category(val categoryName: String, val items: List<String>)

data class Item(
    val imageUrl: String,
    val name: String,
    val price: String,
    val contents: String
)