package com.nezuko.history

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.nezuko.ui.toPrettyString

private val TAG = "HistoryScreen"

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    rounds: LazyPagingItems<Round>
) {
    Log.i(TAG, "HistoryScreen: ")
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            count = rounds.itemCount,
            key = rounds.itemKey { it.id }
        ) { index ->
            val round = rounds[index]
            round?.let {
                RoundCard(round = round)
            }
        }
    }
}

@Composable
fun RoundCard(
    modifier: Modifier = Modifier,
    round: Round
) {
    ElevatedCard(
        modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp),
    ) {
        Column(
            modifier
//                .background(Color.White)
                .padding(15.dp)
                .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Победил конь ${round.winner}")
            Text(round.createdAt.toPrettyString())
            Text("За ${round.durationMillis.toFloat() / 1000} секунд")
        }
    }
}