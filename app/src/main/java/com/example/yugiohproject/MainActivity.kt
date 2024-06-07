package com.example.yugiohproject

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.yugiohproject.ui.theme.YugiohProjectTheme
import com.example.yugiohproject.ui.theme.YugiohViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<YugiohViewModel>()
        setContent {
            YugiohProjectTheme {
                Navigation(viewModel)
            }
        }
    }
}
