package com.bcan.satelliteexplorer.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcan.satelliteexplorer.data.model.Positions
import com.bcan.satelliteexplorer.data.model.PositionsList
import com.bcan.satelliteexplorer.data.model.SatelliteDetail
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

data class SatelliteDetailUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val item: SatelliteDetail? = null,
    val positionsList: PositionsList? = null
)

data class PositionsState(
    val positionsList: PositionsList? = null,
    val currentPositionIndex: Int = 0
)

@HiltViewModel
class SatelliteDetailViewModel @Inject constructor(
    private val repository: SatelliteRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<SatelliteDetailUiState> =
        MutableStateFlow(SatelliteDetailUiState())
    val uiState: StateFlow<SatelliteDetailUiState> = _uiState.asStateFlow()

    private val _positions: MutableStateFlow<PositionsState> = MutableStateFlow(PositionsState())
    val positions = _positions.asStateFlow()

    private val _currentPosition: MutableStateFlow<Positions?> = MutableStateFlow(Positions())
    val currentPosition = _currentPosition.asStateFlow()

    fun getSatelliteDetail(id: Int) {
        viewModelScope.launch {
            repository.getSatelliteDetail(id).collectLatest { result ->
                when (result) {
                    is FileResult.OnLoading -> {
                        _uiState.update { state ->
                            state.copy(isLoading = true, errorMessage = null)
                        }
                    }

                    is FileResult.OnSuccess -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = null,
                                item = result.data
                            )
                        }
                    }

                    is FileResult.OnError -> {
                        _uiState.update { state ->
                            state.copy(isLoading = false, errorMessage = result.message)
                        }
                    }
                }
            }
        }
    }

    fun getSatellitePositions(id: Int) {
        viewModelScope.launch {
            repository.getSatellitePositions(id).collectLatest { result ->
                when (result) {
                    is FileResult.OnLoading -> {
                        _uiState.update { state ->
                            state.copy(isLoading = true, errorMessage = null)
                        }
                    }

                    is FileResult.OnSuccess -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = null,
                            )
                        }
                        _positions.update { state ->
                            state.copy(
                                positionsList = result.data
                            )
                        }
                        //_currentPosition.value = _positions.value?.positions?.get(0)
                    }

                    is FileResult.OnError -> {
                        _uiState.update { state ->
                            state.copy(isLoading = false, errorMessage = result.message)
                        }
                    }
                }
            }
        }
    }

}