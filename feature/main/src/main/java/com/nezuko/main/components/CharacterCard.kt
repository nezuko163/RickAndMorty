package com.nezuko.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nezuko.data.model.Character
import com.nezuko.data.source.LocationInfo
import com.nezuko.ui.Image
import com.nezuko.ui.Spacing

private val TAG = "CharacterCard"

@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    character: Character,
    onCardClick: (Int) -> Unit = {}
) {
    val statusColor = remember(character.color) {
        when (character.color) {
            Character.StatusColor.ALIVE -> Color(0xFF4CAF50)
            Character.StatusColor.DEAD -> Color(0xFFF44336)
            else -> Color.Gray
        }
    }

    ElevatedCard(
        modifier = modifier
            .clickable { onCardClick(character.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                url = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Row(
                Modifier
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(topStart = 20.dp))
                    .background(Color.White)
                    .padding(Spacing.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(statusColor)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = character.status,
                    style = MaterialTheme.typography.bodySmall,
                    color = statusColor,
                    maxLines = 1
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(vertical = Spacing.small)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row {
                Text(
                    text = character.gender,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,

                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "|",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = Spacing.small)
                )

                Text(
                    text = character.species,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start,

                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Preview
@Composable
private fun CharacterCardPrev() {
    CharacterCard(
        character = Character(
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
    )
}