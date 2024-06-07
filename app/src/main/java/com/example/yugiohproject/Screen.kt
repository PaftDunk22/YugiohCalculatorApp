package com.example.yugiohproject

sealed class Screen(val route:String) {
    object MainScreen : Screen("main_screen")
    object ToolScreen : Screen("tool_screen")
    object LogScreen : Screen("log_screen")

}