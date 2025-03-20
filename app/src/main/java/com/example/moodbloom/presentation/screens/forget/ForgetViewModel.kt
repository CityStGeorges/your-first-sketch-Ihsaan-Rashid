package com.example.moodbloom.presentation.screens.forget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.domain.usecases.auth.SendPasswordResetEmailUseCase
import com.example.moodbloom.extension.ResponseStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForgetViewModel @Inject constructor(
    private val useCase: SendPasswordResetEmailUseCase,
) : ViewModel() {

    private val _resetEmail = MutableStateFlow<ResponseStates<Boolean>>(ResponseStates.Idle)
    val resetEmail: StateFlow<ResponseStates<Boolean>> = _resetEmail

    fun sendPasswordResetEmail(request: String) {
        viewModelScope.launch {
            _resetEmail.value = ResponseStates.Loading
            _resetEmail.value = useCase.invoke(request)
        }
    }


    fun clearState(){
        _resetEmail.value = ResponseStates.Idle
    }

}


