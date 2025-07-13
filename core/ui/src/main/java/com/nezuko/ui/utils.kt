package com.nezuko.ui

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Instant.toPrettyString(): String {
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm", Locale("ru"))
    return formatter.format(this.atZone(ZoneId.systemDefault()))
}