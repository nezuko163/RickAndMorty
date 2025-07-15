package com.nezuko.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.data.model.Query
import com.nezuko.data.source.Translator
import com.nezuko.data.util.BackArgumentHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val backArgumentHolder: BackArgumentHolder,
    private val translator: Translator
) : ViewModel() {
    var query by mutableStateOf(backArgumentHolder.query.value)
    fun updateName(name: String) {
        query = query.copy(name = name)
    }

    fun updateStatus(status: String) {
        query = query.copy(status = status)
    }

    fun updateSpecies(species: String) {
        query = query.copy(species = species)
    }

    fun updateType(type: String) {
        query = query.copy(type = type)
    }

    fun updateGender(gender: String) {
        query = query.copy(gender = gender)
    }

    fun applyFilters() {
        viewModelScope.launch {
            backArgumentHolder.emitQuery(
                query.also { Log.i("SearchViewModel", "applyFilters: $it") }
            )
        }
    }

    fun clear() {
        query = Query()
    }
}