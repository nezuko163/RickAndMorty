package com.nezuko.search

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SearchRoute(
    navigateBack: () -> Unit,
    vm: SearchViewModel = hiltViewModel()
) {
    SearchScreen(
        name = vm.query.name,
        onNameChanged = vm::updateName,
        status = vm.query.status,
        onStatusChange = vm::updateStatus,
        species = vm.query.species,
        onSpeciesChange = vm::updateSpecies,
        type = vm.query.type,
        onTypeChange = vm::updateType,
        gender = vm.query.gender,
        onGenderChange = vm::updateGender,
        clearFilter = vm::clear,
        onApply = {
            vm.applyFilters()
            navigateBack()
        },
        onArrowBackClick = navigateBack
    )
}