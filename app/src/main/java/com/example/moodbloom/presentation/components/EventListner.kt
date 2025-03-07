package com.example.moodbloom.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun EventListener(onEvent : (event: Lifecycle.Event) -> Unit) {

    val eventHandler = rememberUpdatedState(newValue = onEvent)
    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value ){
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver{source, event ->
            eventHandler.value(event)
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun OnStartEventListener(onCreateEvent : () -> Unit) {
    EventListener { listener->
        if(listener==Lifecycle.Event.ON_START){
            onCreateEvent()
        }
    }
}
@Composable
fun OnCreateEventListener(onCreateEvent : () -> Unit) {
    EventListener { listener->
        if(listener==Lifecycle.Event.ON_CREATE){
            onCreateEvent()
        }
    }
}
@Composable
fun OnResumeEventListener(onCreateEvent : () -> Unit) {
    EventListener { listener->
        if(listener==Lifecycle.Event.ON_RESUME){
            onCreateEvent()
        }
    }
}

@Composable
fun OnPauseEventListener(onCreateEvent : () -> Unit) {
    EventListener { listener->
        if(listener==Lifecycle.Event.ON_PAUSE){
            onCreateEvent()
        }
    }
}