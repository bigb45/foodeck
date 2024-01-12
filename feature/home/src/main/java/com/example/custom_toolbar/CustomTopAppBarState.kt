package com.example.custom_toolbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy

class CustomTopAppBarState(
    heightRange: IntRange, // extended and collapsed toolbar height
    scrollOffset: Float = 0f, // ?
) : ToolbarState {

    init {
        require(heightRange.first >= 0 && heightRange.last >= heightRange.first) {
            "The lowest height value must be >= 0 and the highest height value must be >= the lowest value."
        }
    }


    val maxHeight: Int
    val minHeight: Int

    init {
        maxHeight = heightRange.last
        minHeight = heightRange.first
    }

    private val rangeDifference = maxHeight - minHeight
    private var _consumed: Float = 0f

    private var _scrollOffset by mutableStateOf(
        value = scrollOffset.coerceIn(0f, maxHeight.toFloat()), policy = structuralEqualityPolicy()
    )

    override val infoSectionHeight: Float
        get() = (250 - scrollOffset)

    override val offset: Float
        get() = if (scrollOffset > rangeDifference) {
            -(scrollOffset - rangeDifference).coerceIn(0f, minHeight.toFloat())
        } else 0f

    override val height: Float
        get() = (maxHeight - scrollOffset).coerceIn(minHeight.toFloat(), maxHeight.toFloat())

    override val progress: Float
        get() = 1 - (maxHeight - height) / rangeDifference

    override val consumed: Float
        get() = _consumed

    override var scrollTopLimitReached: Boolean = true

    override var scrollOffset: Float
        get() = _scrollOffset
        set(value) {
            val oldOffset = _scrollOffset
            _scrollOffset = if (scrollTopLimitReached) {
                value.coerceIn(0f, maxHeight.toFloat())
            } else {
                value.coerceIn(rangeDifference.toFloat(), maxHeight.toFloat())
            }
            _consumed = oldOffset - _scrollOffset
        }

    companion object {
        val Saver = run {
            val minHeightKey = "MinHeight"
            val maxHeightKey = "MaxHeight"
            val scrollOffsetKey = "ScrollOffset"

            mapSaver(save = {
                mapOf(
                    minHeightKey to it.minHeight,
                    maxHeightKey to it.maxHeight,
                    scrollOffsetKey to it.scrollOffset
                )
            }, restore = {
                CustomTopAppBarState(
                    heightRange = (it[minHeightKey] as Int)..(it[maxHeightKey] as Int),
                    scrollOffset = it[scrollOffsetKey] as Float
                )
            })
        }
    }
}





