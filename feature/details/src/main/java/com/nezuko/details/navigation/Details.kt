package com.nezuko.details.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.nezuko.details.DetailsRoute
import kotlinx.serialization.Serializable

@Serializable
data class Details(val id: Int)

fun NavController.navigateToDetails(
    id: Int,
    navOptions: NavOptionsBuilder.() -> Unit = {
    }
) = navigate(Details(id), navOptions)

fun NavGraphBuilder.detailsScreen(
    onNavigateBack: () -> Unit
) = composable<Details> { backStackEntry ->
    val route: Details = backStackEntry.toRoute()
    DetailsRoute(route.id, onNavigateBack)
}