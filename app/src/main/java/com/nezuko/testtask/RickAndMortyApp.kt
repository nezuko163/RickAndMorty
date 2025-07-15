package com.nezuko.testtask

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nezuko.details.navigation.detailsScreen
import com.nezuko.details.navigation.navigateToDetails
import com.nezuko.main.navigation.Main
import com.nezuko.main.navigation.mainScreen
import com.nezuko.search.navigation.navigateToSearch
import com.nezuko.search.navigation.searchScreen

private val TAG = "RacingApp"

@OptIn(ExperimentalLayoutApi::class)
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
        modifier = modifier
            .safeDrawingPadding(),
        navController = navController,
        startDestination = startDestination
    ) {
        mainScreen(
            navigateToDetails = navController::navigateToDetails,
            navController::navigateToSearch
        )
        detailsScreen(onNavigateBack = navController::popBackStack)
        searchScreen(navigateBack = navController::popBackStack)
    }

}