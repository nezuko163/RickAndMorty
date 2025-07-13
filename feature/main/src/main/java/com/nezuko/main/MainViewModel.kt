package com.nezuko.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nezuko.data.repository.RickAndMortyRepository
import com.nezuko.data.model.Character
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

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    fun onSearchTextChange(new: String) {
        _searchText.value = new
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val characters: Flow<PagingData<Character>> = _searchText
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
                            name = query.ifBlank { null },
                            status = null,
                            species = null,
                            type = null,
                            gender = null
                        )
                    }
                }
            ).flow
        }
        .cachedIn(viewModelScope)
}