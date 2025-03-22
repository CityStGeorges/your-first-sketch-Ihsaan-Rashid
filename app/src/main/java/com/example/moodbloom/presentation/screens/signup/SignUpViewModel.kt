package com.example.moodbloom.presentation.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.domain.models.auth.RegisterUserRequestModel
import com.example.moodbloom.domain.usecases.auth.RegisterUseCase
import com.example.moodbloom.utils.extension.ResponseStates
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _userState = MutableStateFlow<ResponseStates<FirebaseUser?>>(ResponseStates.Idle)
    val userState: StateFlow<ResponseStates<FirebaseUser?>> = _userState

    fun registerUser(request: RegisterUserRequestModel) {
        viewModelScope.launch {
            _userState.value = ResponseStates.Loading
            _userState.value = registerUseCase.invoke(request)
        }
    }

    fun clearState(){
        _userState.value = ResponseStates.Idle
    }

}


