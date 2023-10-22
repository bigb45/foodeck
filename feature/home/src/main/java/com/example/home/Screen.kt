package com.example.home

sealed class Screen(val route: String) {
    object Welcome: Screen("welcome")

}