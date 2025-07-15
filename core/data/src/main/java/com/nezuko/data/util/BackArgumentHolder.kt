package com.nezuko.data.util

import com.nezuko.data.model.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

private val TAG = "BackArgumentHolder"

@Singleton
class BackArgumentHolder @Inject constructor() {
    private val _query = MutableStateFlow(Query())
    val query = _query.asStateFlow()

    suspend fun emitQuery(query: Query) {
        _query.emit(query)
    }

    fun setQuery(query: Query) {
        _query.value = query
    }

}