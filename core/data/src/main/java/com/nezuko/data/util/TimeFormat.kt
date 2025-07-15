package com.nezuko.data.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDateTime(isoString: String): String {
    val zdt = ZonedDateTime.parse(isoString, DateTimeFormatter.ISO_ZONED_DATE_TIME)

    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale("ru"))

    return zdt.format(formatter)
}