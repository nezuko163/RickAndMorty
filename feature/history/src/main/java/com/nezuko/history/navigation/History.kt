package com.nezuko.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.nezuko.history.HistoryRoute
import kotlinx.serialization.Serializable

@Serializable
object History

fun NavController.navigateToHistory(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(History, navOptions)

fun NavGraphBuilder.historyScreen(
) = composable<History>(
) { backStackEntry ->
    HistoryRoute()
}