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
import com.nezuko.data.source.Translator
import com.nezuko.data.util.BackArgumentHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: RickAndMortyRepository,
    private val backArgumentHolder: BackArgumentHolder,
    private val translator: Translator
) : ViewModel() {
    private val TAG = "MainViewModel"

    val query = backArgumentHolder.query


    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val characters: Flow<PagingData<Character>> = query
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
                            status = translator.translate(query.status).ifBlank { null },
                            species = translator.translate(query.species).ifBlank { null },
                            type = translator.translate(query.type).ifBlank { null },
                            gender = translator.translate(query.gender).ifBlank { null }
                        )
                    }
                }
            ).flow
        }
        .cachedIn(viewModelScope)
}