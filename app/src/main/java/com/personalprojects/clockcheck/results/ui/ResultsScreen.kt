package com.personalprojects.clockcheck.results.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.personalprojects.clockcheck.R
import com.personalprojects.clockcheck.database.entities.ResultEntity
import com.personalprojects.clockcheck.results.viewmodel.ResultsViewModel
import com.personalprojects.clockcheck.ui.theme.ClockCheckTheme

@Composable
fun ResultsScreen(viewModel: ResultsViewModel = hiltViewModel()) {
    val resultList by viewModel.results.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.text_result_title),
                style = MaterialTheme.typography.headlineLarge
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    TableHeader()
                }
                items(resultList) { result ->
                    ResultRow(result)
                }
            }
        }
    }
}

@Composable
fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp)
    ) {
        TableCell(
            stringResource(R.string.text_search_time),
            Modifier.width(100.dp),
            Color.White,
            FontWeight.Bold
        )
        TableCell(
            stringResource(R.string.text_start_time),
            Modifier.width(100.dp),
            Color.White,
            FontWeight.Bold
        )
        TableCell(
            stringResource(R.string.text_end_time),
            Modifier.width(100.dp),
            Color.White,
            FontWeight.Bold
        )
        TableCell(
            stringResource(R.string.text_result),
            Modifier.width(100.dp),
            Color.White,
            FontWeight.Bold
        )
    }
}

@Composable
fun TableCell(
    text: String,
    modifier: Modifier,
    textColor: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Box(
        modifier = modifier.padding(all = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            fontWeight = fontWeight,
            maxLines = 1
        )
    }
}

@Composable
fun ResultRow(result: ResultEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        TableCell(result.searchTime + " 時", Modifier.width(100.dp))
        TableCell(result.startTime + " 時", Modifier.width(100.dp))
        TableCell(result.endTime + " 時", Modifier.width(100.dp))
        IconTableCell(result.isSuccess, Modifier.width(100.dp))
    }
}

@Composable
fun IconTableCell(
    isSuccess: Boolean?,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier.size(24.dp)
) {
    Box(
        modifier = modifier.padding(all = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isSuccess == true) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Success",
                tint = MaterialTheme.colorScheme.primary,
                modifier = iconModifier
            )
        } else {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Failure",
                tint = MaterialTheme.colorScheme.error,
                modifier = iconModifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
@Preview
fun ResultsScreenPreview() {
    ClockCheckTheme {
        ResultsScreen()
    }
}
