package com.nezuko.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.nezuko.search.SearchRoute
import kotlinx.serialization.Serializable

@Serializable
object Search

fun NavController.navigateToSearch(
    navOptions: NavOptionsBuilder.() -> Unit = {}
) = navigate(Search, navOptions)

fun NavGraphBuilder.searchScreen(
    navigateBack: () -> Unit
) = composable<Search>(
) { backStackEntry ->
    SearchRoute(navigateBack)
}