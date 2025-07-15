package com.nezuko.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems


private val TAG = "MainRoute"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainRoute(
    vm: MainViewModel = hiltViewModel(),
    navigateToDetails: (Int) -> Unit,
    navigateToSearch: () -> Unit
) {
    val query by vm.query.collectAsState()
    val characters = vm.characters.collectAsLazyPagingItems()

    MainScreen(
        characters = characters,
        onCharacterClick = navigateToDetails,
        onSearchClick = navigateToSearch,
        text = query.name
    )
}