package com.nezuko.testtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nezuko.ui.TestTaskTheme
import dagger.hilt.android.AndroidEntryPoint

private val TAG = "MainActivity!"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestTaskTheme {
                RickAndMortyApp()
            }
        }
    }
}

