package com.example.moodbloom.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.domain.models.auth.LoginRequestModel
import com.example.moodbloom.domain.models.auth.RegisterUserRequestModel
import com.example.moodbloom.domain.usecases.LoginUseCase
import com.example.moodbloom.extension.ResponseStates
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<ResponseStates<FirebaseUser?>>(ResponseStates.Idle)
    val userState: StateFlow<ResponseStates<FirebaseUser?>> = _userState

    fun loginUser(request: LoginRequestModel) {
        viewModelScope.launch {
            _userState.value = loginUseCase.invoke(request)
        }
    }

    fun clearState(){
        _userState.value = ResponseStates.Idle
    }

}


