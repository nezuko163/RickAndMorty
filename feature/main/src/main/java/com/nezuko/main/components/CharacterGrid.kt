package com.nezuko.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import com.nezuko.data.exceptions.EmptyResultException
import com.nezuko.data.model.Character
import com.nezuko.ui.theme.Spacing

private val TAG = "CharacterGrid"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterGrid(
    modifier: Modifier = Modifier,
    characters: LazyPagingItems<Character>,
    onCardClick: (Int) -> Unit
) {
    val refreshing = characters.loadState.refresh is LoadState.Loading
    val pullState = rememberPullToRefreshState()

    val refreshState = characters.loadState.refresh
    val isEmptyError =
        refreshState is LoadState.Error && refreshState.error is EmptyResultException

    PullToRefreshBox(
        state = pullState,
        modifier = modifier,
        isRefreshing = refreshing,
        onRefresh = { characters.refresh() },
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                state = pullState,
                isRefreshing = refreshing,
                color = Color.Cyan,
                containerColor = Color.White
            )
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                horizontal = Spacing.large,
                vertical = Spacing.medium
            ),
            horizontalArrangement = Arrangement.spacedBy(Spacing.medium),
            verticalArrangement = Arrangement.spacedBy(Spacing.medium)
        ) {
            if (isEmptyError) {
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
                return@LazyVerticalGrid
            }

            if (refreshState is LoadState.Loading) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
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
                        modifier = Modifier
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

            val appendErrorState = characters.loadState.append as? LoadState.Error
            appendErrorState?.let { state ->
                if (state.error !is EmptyResultException) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Spacing.large),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Ошибка загрузки: ${state.error.localizedMessage}",
                                color = Color.Red
                            )
                            Spacer(modifier = Modifier.height(Spacing.small))
                            Button(onClick = { characters.retry() }) {
                                Text("Повторить")
                            }
                        }
                    }
                }
            }
        }
    }
}