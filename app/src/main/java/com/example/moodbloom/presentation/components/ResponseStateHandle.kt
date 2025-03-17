package com.example.moodbloom.presentation.components


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.moodbloom.extension.ResponseStates
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

@Composable
fun <T> HandleApiStates(
    state: ResponseStates<T>,
    updatePrompt:(PromptTypeShow?)->Unit,
    onFailure: @Composable ((String) -> Unit)?=null,
    onLoading: @Composable (() -> Unit)?=null,
    onUnauthorized: @Composable (() -> Unit)?=null,
    onSuccess: @Composable ((T) -> Unit)
) {
    when (state) {
        is ResponseStates.Loading -> {
            onLoading?.let { loading->
                loading()
            }?:run {
                ShowLoader()
            }
        }
        is ResponseStates.Failure -> {
            onFailure?.let { failureState->
                failureState(state.error)
            }?: run {
                LaunchedEffect(Unit) {
                    updatePrompt(
                        PromptTypeShow.Error(
                            message = state.error.takeIf { it.isNotBlank() }?:"We are unable to process your request at this time. Please try again later.",
                            onButtonClick = {},
                            onDismiss = { updatePrompt(null) }
                        )
                    )
                }
            }
        }
        is ResponseStates.Unauthorized -> {
            onUnauthorized?.let { unauthorized->
                unauthorized()
            }?: run {
                LaunchedEffect(Unit) {
                    updatePrompt(
                        PromptTypeShow.UnAthorized(title = "401")
                    )
                }
            }
        }
        is ResponseStates.Success -> {
            onSuccess(state.data)
        }
        else -> {

        }
    }
}
fun Any.toJsonObject(): JsonObject {
    val gson = Gson()
    val jsonString = gson.toJson(this)
    return JsonParser.parseString(jsonString).asJsonObject
}