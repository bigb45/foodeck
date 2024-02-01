package com.example.custom_toolbar

interface ToolbarState {
    val offset: Float // the list offset (how much the user has scrolled)
    val height: Float // the current height of the toolbar
    val progress: Float // ?
    val consumed: Float // how much of ? has been consumed
    var scrollTopLimitReached: Boolean // has the toolbar 'bottomed-out'
    var scrollOffset: Float
    fun collapse()
}