package com.example.moodbloom.presentation.screens.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
 ) : ViewModel() {


    private val _splashState = mutableStateOf<SplashState>(SplashState.Loading)
    val splashState: State<SplashState> = _splashState

    fun startSplash() {
        viewModelScope.launch {
            _splashState.value= SplashState.Loading
            delay(4000)
            _splashState.value=SplashState.Success("")
        }
    }


}
sealed class SplashState {
    data object Loading : SplashState()
    data class Success(val configurationData: String) : SplashState() // Pass configuration data
    data class Error(val message: String) : SplashState()
}

