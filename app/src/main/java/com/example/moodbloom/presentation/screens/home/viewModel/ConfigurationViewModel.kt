package com.example.moodbloom.presentation.screens.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodbloom.domain.models.ConfigurationModel
import com.example.moodbloom.domain.usecases.config.AdOrUpdateConfigUseCase
import com.example.moodbloom.domain.usecases.config.GetUserConfigUseCase
import com.example.moodbloom.utils.extension.ResponseStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ConfigurationViewModel @Inject constructor(
    private val adOrUpdateConfigUseCase: AdOrUpdateConfigUseCase,
    private val getUserConfigUseCase: GetUserConfigUseCase
) : ViewModel() {
    private val _adOrUpdateConfigState =
        MutableStateFlow<ResponseStates<String>>(ResponseStates.Idle)
    val adOrUpdateConfigState: StateFlow<ResponseStates<String>> = _adOrUpdateConfigState

    fun adOrUpdateConfig(request: ConfigurationModel) {
        viewModelScope.launch {
            _adOrUpdateConfigState.value = ResponseStates.Loading
            _adOrUpdateConfigState.value = adOrUpdateConfigUseCase.invoke(request)
        }
    }

    private val _getUserConfigState =
        MutableStateFlow<ResponseStates<ConfigurationModel>>(ResponseStates.Idle)
    val getUserConfigState: StateFlow<ResponseStates<ConfigurationModel>> = _getUserConfigState

    fun getUserConfig(userId: String) {
        viewModelScope.launch {
            _getUserConfigState.value = ResponseStates.Loading
            _getUserConfigState.value = getUserConfigUseCase.invoke(userId)
        }
    }


}


