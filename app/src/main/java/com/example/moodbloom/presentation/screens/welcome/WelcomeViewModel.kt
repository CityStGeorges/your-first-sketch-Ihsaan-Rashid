package com.example.moodbloom.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.domain.usecases.auth.SignInWithGoogleUseCase
import com.example.moodbloom.extension.ResponseStates
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase
) : ViewModel() {


    private val _userState = MutableStateFlow<ResponseStates<FirebaseUser?>>(ResponseStates.Idle)
    val userState: StateFlow<ResponseStates<FirebaseUser?>> = _userState
    fun googleSignIn(idToken: String) {
        viewModelScope.launch {
            _userState.value = ResponseStates.Loading
            _userState.value = signInWithGoogleUseCase.invoke(idToken)
        }
    }

    fun clearState(){
        _userState.value = ResponseStates.Idle
    }

}



