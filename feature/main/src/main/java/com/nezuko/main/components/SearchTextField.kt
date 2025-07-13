package com.nezuko.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: String = "Search...",
    onValueChange: (String) -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
            },
            trailingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onFilterClick) {
                        Icon(
                            Icons.Outlined.Settings,
                            contentDescription = "Clear",
                            tint = Color.Gray
                        )
                    }
                    if (value.isNotEmpty()) {
                        IconButton(onClick = { onValueChange("") }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = Color.Gray
                            )
                        }
                    } else {
                        IconButton({}) {
                            Icon(
                                Icons.Default.ThumbUp,
                                contentDescription = null,
                                tint = Color.LightGray
                            )
                        }
                    }
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}
