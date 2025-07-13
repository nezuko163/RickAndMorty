package com.nezuko.testtask

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nezuko.details.navigation.detailsScreen
import com.nezuko.details.navigation.navigateToDetails
import com.nezuko.main.navigation.Main
import com.nezuko.main.navigation.mainScreen

private val TAG = "RacingApp"

@Composable
fun RickAndMortyApp(
    modifier: Modifier = Modifier,
    startDestination: Any = Main,
    navController: NavHostController = rememberNavController()
) {
    NavHost(

        popExitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        modifier = modifier.fillMaxSize(), navController = navController,
        startDestination = startDestination
    ) {
        mainScreen(navigateToDetails = navController::navigateToDetails)
        detailsScreen(onNavigateBack = navController::popBackStack)
    }

}