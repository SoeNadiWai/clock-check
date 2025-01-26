package com.personalprojects.clockcheck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.personalprojects.clockcheck.home.ui.HomeScreen
import com.personalprojects.clockcheck.home.viewmodel.HomeViewModel
import com.personalprojects.clockcheck.results.ui.ResultsScreen
import com.personalprojects.clockcheck.ui.theme.ClockCheckTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.state.collectAsState()
            ClockCheckTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            navController = navController,
                            searchTimeUIState = uiState.searchTimeUIState,
                            startTimeUIState = uiState.startTimeUIState,
                            endTimeUIState = uiState.endTimeUIState,
                            shouldShowDialog = uiState.shouldShowDialog,
                            isTimeWithinRange = uiState.isCheckSuccess,
                            onCheckClicked = { viewModel.isTimeWithinRange() },
                            updateSearchTime = { viewModel.updateSearchTime(it) },
                            updateStartTime = { viewModel.updateStartTime(it) },
                            updateEndTime = { viewModel.updateEndTime(it) },
                            onDismissDialog = { viewModel.dismissDialog() },
                            emitSearchTimeIsUnfocused = { viewModel.updateSearchTimeIsUnfocused() },
                            emitStartTimeIsUnfocused = { viewModel.updateStartTimeIsUnfocused() },
                            emitEndTimeIsUnfocused = { viewModel.updateEndTimeIsUnfocused() }
                        )
                    }
                    composable("results") {
                        ResultsScreen()
                    }
                }
            }
        }
    }
}