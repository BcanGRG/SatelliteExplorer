package com.bcan.satelliteexplorer.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcan.satelliteexplorer.data.model.SatelliteResponseModel
import com.bcan.satelliteexplorer.data.repository.SatelliteRepository
import com.bcan.satelliteexplorer.data.util.FileResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SatelliteListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val list: List<SatelliteResponseModel>? = null
)

@HiltViewModel
class SatelliteListViewModel @Inject constructor(
    private val repository: SatelliteRepository
) : ViewModel() {


    private val _uiState: MutableStateFlow<SatelliteListUiState> =
        MutableStateFlow(SatelliteListUiState())
    val uiState: StateFlow<SatelliteListUiState> = _uiState.asStateFlow()

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
                                list = result.data
                            )
                        }
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
}