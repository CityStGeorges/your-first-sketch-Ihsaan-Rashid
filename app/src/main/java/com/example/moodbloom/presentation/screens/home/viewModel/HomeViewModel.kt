package com.example.moodbloom.presentation.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.domain.usecases.config.DeleteAccountUseCase
import com.example.moodbloom.extension.ResponseStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {


    private val _deleteAccountState =
        MutableStateFlow<ResponseStates<String>>(ResponseStates.Idle)
    val deleteAccountState: StateFlow<ResponseStates<String>> = _deleteAccountState

    fun deleteAccount(userId: String) {
        viewModelScope.launch {
            _deleteAccountState.value = ResponseStates.Loading
            _deleteAccountState.value = deleteAccountUseCase.invoke(userId)
        }
    }
}


