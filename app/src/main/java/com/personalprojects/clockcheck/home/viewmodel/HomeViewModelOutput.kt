package com.personalprojects.clockcheck.home.viewmodel

import com.personalprojects.clockcheck.home.Validation
import com.personalprojects.clockcheck.home.ValidationResult
import kotlinx.coroutines.flow.StateFlow

interface HomeViewModelOutput {
    val state: StateFlow<HomeViewState>
}

data class HomeViewState(
    val searchTimeUIState: SearchTimeUIState = SearchTimeUIState(),
    val endTimeUIState: EndTimeUIState = EndTimeUIState(),
    val startTimeUIState: StartTimeUIState = StartTimeUIState(),
    val isLoading: Boolean = false,
    val isCheckSuccess: Boolean = false,
    val shouldShowDialog: Boolean = false
)

data class SearchTimeUIState(
    val searchTime: String = "",
    val isMinLengthOvered: Boolean = false,
    val isUnFocusedOnce: Boolean = false
) {
    val canValidate: Boolean = isMinLengthOvered || isUnFocusedOnce
    val validationResult = Validation.validateInput(searchTime)
    val isValid = validationResult == ValidationResult.SUCCESS
}

data class StartTimeUIState(
    val startTime: String = "",
    val isMinLengthOvered: Boolean = false,
    val isUnFocusedOnce: Boolean = false,
) {
    val canValidate: Boolean = isMinLengthOvered || isUnFocusedOnce
    val validationResult = Validation.validateInput(startTime)
    val isValid = validationResult == ValidationResult.SUCCESS
}

data class EndTimeUIState(
    val endTime: String = "",
    val isMinLengthOvered: Boolean = false,
    val isUnFocusedOnce: Boolean = false,
) {
    val canValidate: Boolean = isMinLengthOvered || isUnFocusedOnce
    val validationResult = Validation.validateInput(endTime)
    val isValid = validationResult == ValidationResult.SUCCESS
}
