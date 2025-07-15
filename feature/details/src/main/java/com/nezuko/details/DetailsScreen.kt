package com.nezuko.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.nezuko.data.model.Character
import com.nezuko.data.util.formatDateTime
import com.nezuko.ui.components.Image
import com.nezuko.ui.theme.Spacing

private val TAG = "DetailsScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    character: Character,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
) {
    val uriHandle = LocalUriHandler.current

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Image(
                url = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow(label = "Статус", value = character.status, character.color)
                InfoRow(label = "Вид", value = character.species)
                InfoRow(label = "Тип", value = character.gender)
                InfoRow(label = "Происхождение", value = character.origin.name)
                InfoRow(label = "Локация", value = character.location.name)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Эпизоды",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))


                EpisodesString(char = character) {
                    uriHandle.openUri(it)
                }


                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Создан: ${formatDateTime(character.created)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}


@Composable
private fun InfoRow(label: String, value: String, valueColor: Character.StatusColor? = null) {
    val statusColor = remember(valueColor) {
        when (valueColor) {
            Character.StatusColor.ALIVE -> Color(0xFF4CAF50)
            Character.StatusColor.DEAD -> Color(0xFFF44336)
            null -> Color.Black
            else -> Color.Gray
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = statusColor
        )
    }
}

@Composable
fun EpisodesString(
    modifier: Modifier = Modifier,
    char: Character,
    onItemClick: (String) -> Unit
) {
    Column(modifier.padding(Spacing.small)) {
        char.episode.forEachIndexed { index, item ->
            Text(
                text = char.episodesNumbers[index],
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = TextDecoration.Underline
                ),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(Spacing.tiny)
                    .clickable {
                        onItemClick(item)
                    }
            )
        }
    }
}
