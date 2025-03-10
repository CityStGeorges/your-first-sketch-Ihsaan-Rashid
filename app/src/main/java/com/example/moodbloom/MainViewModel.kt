package com.example.moodbloom

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

    private val _backState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val backState: StateFlow<Boolean> = _backState.asStateFlow()

    fun goToBack(state:Boolean){
        _backState.value=state

    }
}