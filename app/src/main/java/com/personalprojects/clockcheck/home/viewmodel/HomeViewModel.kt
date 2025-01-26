package com.personalprojects.clockcheck.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personalprojects.clockcheck.database.dao.ResultDAO
import com.personalprojects.clockcheck.database.entities.ResultEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dao: ResultDAO
) : ViewModel(), HomeViewModelOutput {

    private val _state = MutableStateFlow(HomeViewState())
    override val state: StateFlow<HomeViewState> = _state.asStateFlow()

    fun updateSearchTime(input: String) {
        _state.update {
            it.copy(
                searchTimeUIState = it.searchTimeUIState.copy(
                    searchTime = input
                )
            )
        }
    }

    fun updateStartTime(input: String) {
        _state.update { it.copy(startTimeUIState = it.startTimeUIState.copy(startTime = input)) }
    }

    fun updateEndTime(input: String) {
        _state.update { it.copy(endTimeUIState = it.endTimeUIState.copy(endTime = input)) }
    }

    fun updateSearchTimeIsUnfocused() {
        _state.update { it.copy(searchTimeUIState = it.searchTimeUIState.copy(isUnFocusedOnce = true)) }
    }

    fun updateStartTimeIsUnfocused() {
        _state.update { it.copy(startTimeUIState = it.startTimeUIState.copy(isUnFocusedOnce = true)) }
    }

    fun updateEndTimeIsUnfocused() {
        _state.update { it.copy(endTimeUIState = it.endTimeUIState.copy(isUnFocusedOnce = true)) }
    }

    private fun showDialog(isTimeWithinRange: Boolean) {
        _state.update { it.copy(shouldShowDialog = true, isCheckSuccess = isTimeWithinRange) }
    }

    fun dismissDialog() {
        _state.update { it.copy(shouldShowDialog = false) }
        clearInput()
    }

    fun isTimeWithinRange() {
        val searchTime = _state.value.searchTimeUIState.searchTime.toInt()
        val startTime = _state.value.startTimeUIState.startTime.toInt()
        val endTime = _state.value.endTimeUIState.endTime.toInt()

        val isTimeWithinRange = if (startTime < endTime) {
            // Simple case where time range does not span midnight
            searchTime in startTime until endTime
        } else {
            // Handle cases where the interval spans midnight (e.g., 23 to 5)
            searchTime >= startTime || searchTime < endTime
        }
        showDialog(isTimeWithinRange)
        saveResult(isTimeWithinRange)
    }

    private fun saveResult(isTimeWithinRange: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertAll(
                ResultEntity(
                    searchTime = _state.value.searchTimeUIState.searchTime,
                    startTime = _state.value.startTimeUIState.startTime,
                    endTime = _state.value.endTimeUIState.endTime,
                    isSuccess = isTimeWithinRange
                )
            )
        }
    }

    private fun clearInput() {
        _state.update { HomeViewState() }
    }
}
