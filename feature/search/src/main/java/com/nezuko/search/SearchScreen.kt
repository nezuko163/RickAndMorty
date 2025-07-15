package com.nezuko.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nezuko.ui.components.SearchTextField
import com.nezuko.ui.theme.Spacing

val statusOptions = listOf("Живой", "Мёртвый", "Неизвестно")
val genderOptions = listOf("Женский", "Мужской", "Без гендера", "Неизвестно")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    name: String,
    onNameChanged: (String) -> Unit,
    status: String,
    onStatusChange: (String) -> Unit,
    species: String = "",
    onSpeciesChange: (String) -> Unit = {},
    type: String = "",
    onTypeChange: (String) -> Unit = {},
    gender: String,
    onGenderChange: (String) -> Unit,
    clearFilter: () -> Unit,
    onApply: () -> Unit,
    onArrowBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    SearchTextField(
                        value = name,
                        onValueChange = onNameChanged,
                        enabled = true,
                        onSearch = onApply
                    )
                },
                navigationIcon = {
                    IconButton(onArrowBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .imePadding()
                .padding(it)
                .padding(Spacing.medium)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(Spacing.small)
        ) {
            Row {
                Text(
                    "Применить",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .clickable { onApply() }
                )

                Spacer(Modifier.weight(1f))

                Text(
                    "Очистить",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .clickable { clearFilter() }
                )
            }


            Text("Статус", style = MaterialTheme.typography.labelMedium)
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                statusOptions.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { onStatusChange(option) }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = status == option,
                            onClick = { onStatusChange(option) }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(option)
                    }
                }
            }

            Spacer(Modifier.height(Spacing.medium))

            Text("Гендер", style = MaterialTheme.typography.labelMedium)
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                genderOptions.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { onGenderChange(option) }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = gender == option,
                            onClick = { onGenderChange(option) }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(option)
                    }
                }
            }

            Spacer(Modifier.height(Spacing.medium))

            OutlinedTextField(
                value = species,
                onValueChange = onSpeciesChange,
                placeholder = { Text("Вид (писать на англ.)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (species.isNotEmpty()) {
                        IconButton(onClick = { onSpeciesChange("") }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = Color.Gray
                            )
                        }
                    }
                }
            )

            OutlinedTextField(
                value = type,
                onValueChange = onTypeChange,
                placeholder = { Text("Тип (писать на англ.)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (type.isNotEmpty()) {
                        IconButton(onClick = { onTypeChange("") }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = Color.Gray
                            )
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun SearchScreenPrev() {
    SearchScreen(
        name = "",
        onNameChanged = {},
        status = "",
        onStatusChange = {},
        species = "",
        onSpeciesChange = {},
        type = "",
        onTypeChange = {},
        gender = "",
        onGenderChange = {},
        clearFilter = {},
        onApply = {},
        onArrowBackClick = {}
    )

}