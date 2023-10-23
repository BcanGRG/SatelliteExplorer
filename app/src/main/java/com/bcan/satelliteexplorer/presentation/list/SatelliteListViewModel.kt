package com.bcan.satelliteexplorer.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcan.satelliteexplorer.data.model.SatelliteResponseModel
import com.bcan.satelliteexplorer.data.repository.SatelliteRepository
import com.bcan.satelliteexplorer.data.util.FileResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SatelliteListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class SatelliteListViewModel @Inject constructor(
    private val repository: SatelliteRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<SatelliteListUiState> =
        MutableStateFlow(SatelliteListUiState())
    val uiState: StateFlow<SatelliteListUiState> = _uiState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _satellites: MutableStateFlow<List<SatelliteResponseModel>?> =
        MutableStateFlow(emptyList())
    val satellites = _searchText
        .debounce(500L)
        .onEach { _uiState.update { state -> state.copy(isLoading = true) } }
        .combine(_satellites) { text, satellites ->
            if (text.isBlank()) {
                satellites
            } else {
                //delay(3000L) // isLoading durumunun gözükmesini kontrol etmek için aktif edilebilir
                satellites?.filter { it.doesMatchSearchQuery(text.trim()) }
            }
        }
        .onEach { _uiState.update { state -> state.copy(isLoading = false) } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _satellites.value
        )

    init {
        getSatelliteList()
    }

    private fun getSatelliteList() {
        viewModelScope.launch {
            repository.getSatelliteList().collectLatest { result ->
                when (result) {
                    is FileResult.OnLoading -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }

                    is FileResult.OnSuccess -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = null,
                            )
                        }
                        _satellites.value = result.data
                    }

                    is FileResult.OnError -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}