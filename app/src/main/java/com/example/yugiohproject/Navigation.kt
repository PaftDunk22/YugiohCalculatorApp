package com.example.yugiohproject

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.yugiohproject.ui.theme.YugiohViewModel
import screens.Home
import screens.Logs
import screens.Tools

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    viewModel: YugiohViewModel
){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {

        composable(Screen.MainScreen.route){
            Home(
                navController= navController,
            )
        }

        composable(Screen.ToolScreen.route){
            Tools(navController)
        }

        composable(Screen.LogScreen.route){
            Logs(
                navController= navController,
                viewModel = viewModel
            )
        }

    }

}