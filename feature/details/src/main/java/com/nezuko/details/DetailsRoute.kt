package com.nezuko.details

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.nezuko.data.model.Character
import com.nezuko.data.model.ResultModel

@Composable
fun DetailsRoute(
    id: Int,
    onNavigateBack: () -> Unit,
    vm: DetailsViewModel = hiltViewModel()
) {
    vm.load(id)
    val character by vm.details.collectAsState()
    BackHandler {
        onNavigateBack()
    }
    if (character is ResultModel.Success) {
        DetailsScreen(
            character = (character as ResultModel.Success<Character>).data,
            onBackClick = onNavigateBack
        )
    }
}