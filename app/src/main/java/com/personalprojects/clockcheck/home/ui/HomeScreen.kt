package com.personalprojects.clockcheck.home.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.personalprojects.clockcheck.R
import com.personalprojects.clockcheck.home.ValidationResult
import com.personalprojects.clockcheck.home.viewmodel.EndTimeUIState
import com.personalprojects.clockcheck.home.viewmodel.SearchTimeUIState
import com.personalprojects.clockcheck.home.viewmodel.StartTimeUIState
import com.personalprojects.clockcheck.ui.theme.ClockCheckTheme

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    searchTimeUIState: SearchTimeUIState = SearchTimeUIState(),
    startTimeUIState: StartTimeUIState = StartTimeUIState(),
    endTimeUIState: EndTimeUIState = EndTimeUIState(),
    updateSearchTime: (String) -> Unit = {},
    updateStartTime: (String) -> Unit = {},
    updateEndTime: (String) -> Unit = {},
    onCheckClicked: () -> Unit = {},
    shouldShowDialog: Boolean = false,
    onDismissDialog: () -> Unit = {},
    emitSearchTimeIsUnfocused: () -> Unit = {},
    emitStartTimeIsUnfocused: () -> Unit = {},
    emitEndTimeIsUnfocused: () -> Unit = {},
    isTimeWithinRange: Boolean = false,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("results") }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = "Navigate to Details"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .padding(16.dp)
        ) {
            Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.text_check_title),
                    style = MaterialTheme.typography.headlineLarge,
                )
                Spacer(modifier = Modifier.height(20.dp))
                SimpleOutlinedTextFieldSample(
                    searchTimeUIState.searchTime,
                    R.string.text_search_time,
                    searchTimeUIState.isValid,
                    searchTimeUIState.canValidate,
                    searchTimeUIState.validationResult,
                    updateSearchTime,
                    emitSearchTimeIsUnfocused
                )
                SimpleOutlinedTextFieldSample(
                    startTimeUIState.startTime,
                    R.string.text_start_time,
                    startTimeUIState.isValid,
                    startTimeUIState.canValidate,
                    startTimeUIState.validationResult,
                    updateStartTime,
                    emitStartTimeIsUnfocused
                )
                SimpleOutlinedTextFieldSample(
                    endTimeUIState.endTime,
                    R.string.text_end_time,
                    endTimeUIState.isValid,
                    endTimeUIState.canValidate,
                    endTimeUIState.validationResult,
                    updateEndTime,
                    emitEndTimeIsUnfocused
                )

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(R.string.text_warning),
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray),
                )

                Button(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .fillMaxWidth()
                        .height(46.dp),
                    onClick = onCheckClicked,
                    enabled = searchTimeUIState.isValid && startTimeUIState.isValid && endTimeUIState.isValid,
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Text(stringResource(R.string.text_check_button), fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    if (shouldShowDialog) {
        ResultDialog(
            onDismissRequest = onDismissDialog,
            onConfirmation = {},
            isCheckSuccess = isTimeWithinRange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleOutlinedTextFieldSample(
    value: String?,
    @StringRes stringResId: Int,
    isValid: Boolean,
    canValidate: Boolean,
    validationResult: ValidationResult,
    onValueChanged: (String) -> Unit,
    emitInputIsUnfocused: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    @StringRes val errorMessageId = when (validationResult) {
        ValidationResult.SUCCESS -> null
        ValidationResult.ERROR_LIMIT -> R.string.error_over_limit
        ValidationResult.ERROR_NOT_NUMBER -> R.string.error_not_number
        ValidationResult.ERROR_GREATER_THAN_24 -> R.string.error_greater_than_24
        ValidationResult.EMPTY_INPUT -> R.string.error_empty_input
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(stringResId),
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier
                .width(100.dp)
                .padding(end = 16.dp)
        )
        OutlinedTextField(
            value = value ?: "",
            onValueChange = onValueChanged,
            modifier = Modifier
                .width(220.dp)
                .onFocusChanged {
                    if (!it.hasFocus && isFocused) {
                        emitInputIsUnfocused()
                    }
                    isFocused = it.isFocused
                },
            isError = canValidate && !isValid,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.Transparent,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                cursorColor = MaterialTheme.colorScheme.primary,
                errorCursorColor = MaterialTheme.colorScheme.error,
                errorTrailingIconColor = MaterialTheme.colorScheme.error
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = {
                Text(
                    stringResource(R.string.text_input_hint),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            trailingIcon = {
                if (canValidate && !isValid) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            supportingText = {
                if (canValidate && !isValid && errorMessageId != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(errorMessageId),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClockCheckTheme {
        HomeScreen(
            navController = NavHostController(LocalContext.current),
            onCheckClicked = {},
            searchTimeUIState = SearchTimeUIState(),
            startTimeUIState = StartTimeUIState(),
            endTimeUIState = EndTimeUIState(),
            updateSearchTime = {},
            updateStartTime = {},
            updateEndTime = {},
            shouldShowDialog = false,
            onDismissDialog = {},
            emitSearchTimeIsUnfocused = {},
            emitStartTimeIsUnfocused = {},
            emitEndTimeIsUnfocused = {},
            isTimeWithinRange = true
        )
    }
}
