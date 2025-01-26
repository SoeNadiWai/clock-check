package com.personalprojects.clockcheck.results.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personalprojects.clockcheck.database.dao.ResultDAO
import com.personalprojects.clockcheck.database.entities.ResultEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(private val dao: ResultDAO) : ViewModel() {
    private val _results = MutableStateFlow<List<ResultEntity>>(emptyList())
    val results: StateFlow<List<ResultEntity>> = _results

    init {
        println("ramen = viewmodel")
        fetchResults()
    }

    private fun fetchResults() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = dao.getAll()
            _results.value = data
        }
    }
}