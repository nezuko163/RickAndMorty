package com.nezuko.testtask

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.nezuko.ui.TestTaskTheme
import dagger.hilt.android.AndroidEntryPoint

private val TAG = "MainActivity!"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TestTaskTheme {
                RickAndMortyApp()
            }
        }
    }
}

