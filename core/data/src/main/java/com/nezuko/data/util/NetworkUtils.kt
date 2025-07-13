package com.nezuko.data.util

import com.nezuko.data.model.ResultModel
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.io.IOException

suspend fun <T> safeRequest(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    request: suspend () -> T
): ResultModel<T> = withContext(dispatcher) {
    try {
        val response = request()
        ResultModel.success(response)
    } catch (e: Exception) {
        ResultModel.failure(mapError(e))
    }
}

private fun mapError(e: Throwable): String {
    return when (e) {
        is ClientRequestException -> "Ошибка клиента: ${e.response.status.description}"
        is ServerResponseException -> "Ошибка сервера: ${e.response.status.description}"
        is RedirectResponseException -> "Ошибка редиректа: ${e.response.status.description}"
        is TimeoutCancellationException -> "Превышено время ожидания"
        is IOException -> "Проблема с подключением к интернету"
        else -> "Неизвестная ошибка: ${e.localizedMessage ?: "Нет сообщения"}"
    }
}

fun getPageFromUrl(url: String): Int? {
    val query = url.substringAfter("?", "")
    val params = query.split("&").map { it.split("=") }
        .associate { it[0] to it.getOrNull(1) }
    return params["page"]?.toIntOrNull()
}