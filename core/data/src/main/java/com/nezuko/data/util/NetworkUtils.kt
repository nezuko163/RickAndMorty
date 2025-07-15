package com.nezuko.data.util

import com.nezuko.data.exceptions.EmptyResultException
import com.nezuko.data.exceptions.InternetException
import com.nezuko.data.model.ResultModel
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
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

    } catch (e: EmptyResultException) {
        ResultModel.failure(e)
    } catch (e: Exception) {
        ResultModel.failure(mapError(e))
    }
}

fun <T> safeFlow(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    request: suspend () -> T
): Flow<ResultModel<T>> = flow {
    try {
        val response = request()
        emit(ResultModel.success(response))

    } catch (e: EmptyResultException) {
        emit(ResultModel.failure(e))
    } catch (e: Exception) {
        emit(ResultModel.failure(mapError(e)))
    }
}.flowOn(dispatcher)

private fun mapError(e: Throwable): Exception {
    return when (e) {
        is IOException, is TimeoutCancellationException, is RedirectResponseException, is ServerResponseException, is ClientRequestException -> InternetException()
        else -> Exception("Неизвестная ошибка: ${e.localizedMessage ?: "Нет сообщения"}")
    }
}

fun getPageFromUrl(url: String): Int? {
    val query = url.substringAfter("?", "")
    val params = query.split("&").map { it.split("=") }
        .associate { it[0] to it.getOrNull(1) }
    return params["page"]?.toIntOrNull()
}