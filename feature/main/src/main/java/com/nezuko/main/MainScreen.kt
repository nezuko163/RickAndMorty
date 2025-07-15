package com.nezuko.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.nezuko.data.model.Character
import com.nezuko.main.components.CharacterGrid
import com.nezuko.ui.components.SearchTextField
import com.nezuko.ui.theme.Spacing

private const val TAG = "MainScreen"

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreen(
    characters: LazyPagingItems<Character>,
    onCharacterClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    text: String,
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            SearchTextField(
                modifier = Modifier.padding(Spacing.medium),
                onSearchTextFieldClick = onSearchClick,
                value = text,
            )
        },
    ) { padding ->
        CharacterGrid(
            modifier = Modifier.padding(padding),
            characters = characters,
            onCardClick = onCharacterClick
        )
    }
}