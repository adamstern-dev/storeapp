package com.drund.storedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.drund.storedemo.navigation.StoreNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreApp()
        }
    }
}

@Composable
fun StoreApp() {
    val navController = rememberNavController()
    StoreNavigation(navController = navController)
}
