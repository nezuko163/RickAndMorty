package com.nezuko.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import com.nezuko.data.model.Character
import com.nezuko.data.source.LocationInfo
import com.nezuko.main.components.CharacterCard
import com.nezuko.main.components.CharacterGrid
import com.nezuko.main.components.SearchTextField
import com.nezuko.ui.Spacing

private const val TAG = "MainScreen"

@Composable
fun MainScreen(
    characters: LazyPagingItems<Character>,
    onCharacterClick: (Int) -> Unit,
    onValueChange: (String) -> Unit,
    text: String
) {
    Scaffold(
        topBar = {
            SearchTextField(
                modifier = Modifier.statusBarsPadding(),
                value = text,
                onValueChange = onValueChange,
                onFilterClick = {}
            )
        }
    ) { padding ->
        CharacterGrid(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            characters = characters,
            onCardClick = onCharacterClick
        )
    }
}

@Preview
@Composable
private fun TestTextField() {
    SearchTextField(value = "asd") {

    }
}

@Composable
fun MainScreenTest(
    characters: List<Character>
) {
    Column {
        SearchTextField() {

        }

        Spacer(Modifier.height(Spacing.medium))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(Spacing.tiny),
            verticalArrangement = Arrangement.spacedBy(Spacing.tiny)

        ) {
            items(characters) { character ->
                CharacterCard(character = character)
            }
        }
    }
}

@Preview
@Composable
private fun MainScreenPrev() {
    val character = Character(
        id = 1,
        name = "Rick",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        origin = LocationInfo("Earth", "url"),
        location = LocationInfo("Earth", "url"),
        image = "image_url",
        episode = listOf("episode1"),
        url = "url",
        created = "date"
    )

    MainScreenTest(characters = listOf(character, character, character, character))
}