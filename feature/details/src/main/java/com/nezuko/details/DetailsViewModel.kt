package com.nezuko.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.data.model.Character
import com.nezuko.data.model.ResultModel
import com.nezuko.data.repository.RickAndMortyRepository
import com.nezuko.data.source.Translator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository
) : ViewModel() {
    private val _details = MutableStateFlow<ResultModel<Character>>(ResultModel.none())
    val details = _details.asStateFlow()

    fun load(id: Int) {
        viewModelScope.launch {
            rickAndMortyRepository.getCharacterById(id)
                .collect {
                    _details.emit(it)
                }
        }
    }

    fun clear() {
        viewModelScope.launch {
            _details.emit(ResultModel.none())
        }
    }
}