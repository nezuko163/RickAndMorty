package com.nezuko.main.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.nezuko.data.model.Character
import com.nezuko.ui.Spacing

private val TAG = "CharacterGrid"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterGrid(
    modifier: Modifier = Modifier,
    characters: LazyPagingItems<Character>,
    onCardClick: (Int) -> Unit,
    onRefresh: () -> Unit = {}
) {
    val refreshing = characters.loadState.refresh is LoadState.Loading
    val state = rememberPullToRefreshState()
    PullToRefreshBox(
        state = state,
        modifier = modifier,
        isRefreshing = refreshing,
        onRefresh = {
            characters.refresh()
        },
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                state = state,
                isRefreshing = refreshing,
                color = Color.Cyan,
                containerColor = Color.White
            )
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = Spacing.large),
            horizontalArrangement = Arrangement.spacedBy(Spacing.medium),
            verticalArrangement = Arrangement.spacedBy(Spacing.medium)
        ) {
            Log.i(TAG, "CharacterGrid: ${characters.loadState}")
            if (characters.itemCount == 0
                && characters.loadState.refresh is LoadState.Error
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Spacing.large),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Ничего не найдено",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }

            if (characters.loadState.refresh is LoadState.Loading) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = Spacing.large),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            items(
                count = characters.itemCount,
                key = characters.itemKey { it.id }
            ) { index ->
                characters[index]?.let { char ->
                    CharacterCard(
                        character = char,
                        onCardClick = onCardClick
                    )
                }
            }

            if (characters.loadState.append is LoadState.Loading) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = Spacing.medium),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            val errorState = characters.loadState.refresh as? LoadState.Error
                ?: characters.loadState.append as? LoadState.Error
            errorState?.let { state ->
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(Spacing.large),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Ошибка загрузки: ${state.error.localizedMessage}", color = Color.Red)
                        Spacer(Modifier.height(Spacing.small))
                        Button(onClick = { characters.retry() }) {
                            Text("Повторить")
                        }
                    }
                }
            }
        }
    }
}