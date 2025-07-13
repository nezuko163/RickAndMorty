package com.nezuko.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nezuko.data.model.Character
import com.nezuko.data.repository.RickAndMortyRepository
import com.nezuko.data.source.CharacterPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: RickAndMortyRepository
) : ViewModel() {
    private val TAG = "MainViewModel"

    data class Query(
        val name: String = "",
        val status: String? = null,
        val species: String = "",
        val type: String = "",
        val gender: String? = null
    )

    private val _query = MutableStateFlow(Query())
    val query = _query.asStateFlow()

    fun updateName(name: String) = updateQuery { this.copy(name = name) }
    fun updateStatus(status: String?) = updateQuery { this.copy(status = status) }
    fun updateSpecies(species: String) = updateQuery { this.copy(species = species) }
    fun updateType(type: String) = updateQuery { this.copy(type = type) }
    fun updateGender(gender: String?) = updateQuery { this.copy(gender = gender) }

    private fun updateQuery(transform: Query.() -> Query) {
        _query.value = _query.value.transform()
    }


    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val characters: Flow<PagingData<Character>> = _query
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            Log.i(TAG, "charaters: $query")
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = {
                    CharacterPagingSource { page ->
                        Log.i(TAG, "factory: $page")
                        repo.getCharacters(
                            page = page,
                            name = query.name.ifBlank { null },
                            status = query.status,
                            species = query.species.ifBlank { null },
                            type = query.type.ifBlank { null },
                            gender = query.gender
                        )
                    }
                }
            ).flow
        }
        .cachedIn(viewModelScope)
}