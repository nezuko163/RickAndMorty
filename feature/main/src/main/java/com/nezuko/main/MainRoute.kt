package com.nezuko.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.nezuko.main.components.FilterBottomSheet


private val TAG = "MainRoute"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainRoute(
    vm: MainViewModel = hiltViewModel(),
    navigateToDetails: (Int) -> Unit,
) {
    val query by vm.query.collectAsState()
    val characters = vm.characters.collectAsLazyPagingItems()


    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        FilterBottomSheet(
            onDismiss = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            scope = scope,
            status = query.status ?: "",
            onStatusChange = vm::updateStatus,
            species = query.species,
            onSpeciesChange = vm::updateSpecies,
            type = query.type,
            onTypeChange = vm::updateType,
            gender = query.gender ?: "",
            onGenderChange = vm::updateGender,
        )
    }

    MainScreen(
        characters,
        onCharacterClick = navigateToDetails,
        onValueChange = vm::updateName,
        onFilterClick = { showBottomSheet = true },
        text = query.name
    )
}