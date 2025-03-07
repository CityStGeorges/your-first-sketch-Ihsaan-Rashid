package com.example.moodbloom.presentation.components

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class PromptsViewModel @Inject constructor() : ViewModel() {
    private val _currentPrompt = MutableStateFlow<PromptTypeShow?>(null)
    val currentPrompt: StateFlow<PromptTypeShow?> = _currentPrompt

    fun updatePrompt(promptTypeShow:PromptTypeShow?) {
        promptTypeShow?.let {
            if (promptTypeShow.title=="401"){

            }else{
                if (currentPrompt.value==null){
                    _currentPrompt.value = promptTypeShow
                }
            }
        }?:run {
            _currentPrompt.value = null
        }
    }


    fun dismissPrompt() {
        _currentPrompt.value = null
    }
}