package com.nezuko.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems


private val TAG = "MainRoute"

@Composable
fun MainRoute(
    vm: MainViewModel = hiltViewModel(),
    navigateToDetails: (Int) -> Unit,
) {
    val text by vm.searchText.collectAsState()
    val characters = vm.characters.collectAsLazyPagingItems()
    MainScreen(
        characters,
        onCharacterClick = navigateToDetails,
        onValueChange = vm::onSearchTextChange,
        text = text
    )
}