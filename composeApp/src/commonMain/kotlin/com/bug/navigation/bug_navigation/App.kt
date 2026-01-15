package com.bug.navigation.bug_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        //The bug is here, on iOS a black square is animated (moving to right-bottom corner) when the app starts.
        navController.navigate(Screen2Red) {
            popUpTo(Screen1Blue) { inclusive = true }
        }

        // However if you launch it via another coroutine, then the bug is not present
//        scope.launch {
//            navController.navigate(Screen2Red) {
//                popUpTo(Screen1Blue) { inclusive = true }
//            }
//        }
    }

    MaterialTheme {
        NavHost(
            navController = navController, startDestination = Screen1Blue
        ) {
            composable<Screen1Blue> {
                Column(
                    modifier = Modifier
                        .background(Color.Blue)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button({
                        navController.navigate(Screen2Red) {
                            popUpTo(Screen1Blue) { inclusive = true }
                        }
                    }) {
                        Text("Go to Screen 2 Red")
                    }
                }
            }
            composable<Screen2Red> {
                Column(
                    modifier = Modifier
                        .background(Color.Red)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button({
                        navController.navigate(Screen1Blue) {
                            popUpTo(Screen2Red) { inclusive = true }
                        }
                    }) {
                        Text("Go to Screen 1 Blue")
                    }
                }
            }
        }
    }
}

@Serializable
data object Screen1Blue
@Serializable
data object Screen2Red
