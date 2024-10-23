package com.example.flow_fundamentals.exam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var startValue: Int = 0

    private val _countdownFlow = MutableStateFlow(0)

    //    val countdownFlow: StateFlow<Int> get() = _countdownFlow
    val countdownFlow: StateFlow<Int> = _countdownFlow.asStateFlow()

    fun startCountdown(start: Int) {
        startValue = start

        if (_countdownFlow.value != startValue) {
            viewModelScope.launch {
                countdownFlow(startValue).collect {
                    _countdownFlow.value = it
                }
            }
        }
    }

    private fun countdownFlow(start: Int): Flow<Int> = flow {
        var currentValue = start
        emit(currentValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }
}