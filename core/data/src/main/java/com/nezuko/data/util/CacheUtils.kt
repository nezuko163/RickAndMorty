package com.nezuko.data.util

import android.R.id.message
import android.util.Log
import com.nezuko.data.exceptions.EmptyResultException
import com.nezuko.data.model.ResultModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

private val TAG = "CacheUtils"
suspend fun <T> safeRoomRequest(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    request: suspend () -> T
): ResultModel<T> = withContext(dispatcher) {
    try {
        val result = request()
        ResultModel.success(result)
    } catch (e: Exception) {
        ResultModel.failure(RuntimeException("Ошибка чтения из базы данных: ${e.localizedMessage ?: "Неизвестная ошибка"}"))
    }
}

fun <T> safeRoomFlow(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    request: suspend () -> T
): Flow<ResultModel<T>> = flow {
    emit(ResultModel.loading())
    emit(safeRoomRequest(dispatcher, request))
}.flowOn(dispatcher)

suspend fun <T> networkQueryWithCache(
    queryCache: suspend () -> ResultModel<T>,
    fetchNetwork: suspend () -> ResultModel<T>,
    saveFetch: suspend (T) -> Unit,
    isCacheFirst: Boolean = false
): ResultModel<T> = withContext(Dispatchers.IO) {
    val firstResult = if (isCacheFirst) {
        queryCache()
    } else {
        fetchNetwork()
    }

    Log.i(TAG, "networkQueryWithCache: first - $firstResult")

    when (firstResult) {
        is ResultModel.Success -> {
            if (!isCacheFirst) {
                try {
                    saveFetch(firstResult.data)
                } catch (e: Exception) {
                    Log.e(TAG, "networkQueryWithCache: room exc", e)
                }
            }
            ResultModel.success(firstResult.data)
        }

        is ResultModel.Failure -> {
            if (firstResult.e is EmptyResultException) return@withContext firstResult
            val cached = queryCache()
            when (cached) {
                is ResultModel.Success -> cached
                is ResultModel.Failure -> {
                    val message = "${firstResult.e.message}\n${cached.e.message}"
                    ResultModel.failure(message)
                }

                else -> ResultModel.failure(firstResult.e.message ?: "Неизвестная ошибка")
            }
        }

        is ResultModel.None -> ResultModel.failure("Недопустимое состояние")

        is ResultModel.Loading -> {
            ResultModel.failure("Unexpected loading state")
        }
    }.also { Log.i(TAG, "networkQueryWithCache: res - $it") }
}
