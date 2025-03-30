package com.example.moodbloom.utils


import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

class TextToSpeechHelper(context: Context,  onInitialize: ((Boolean) -> Unit), private val onComplete: (() -> Unit)? = null) {
    private var textToSpeech: TextToSpeech? = null

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                onInitialize(true)
                textToSpeech?.language = Locale.US
                textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        Log.d("TTS", "Speech started")
                    }

                    override fun onDone(utteranceId: String?) {
                        Log.d("TTS", "Speech completed")
                        onComplete?.invoke() // Invoke callback when TTS finishes
                    }

                    override fun onError(utteranceId: String?) {
                        Log.e("TTS", "Speech error")
                    }
                })
            } else {
                onInitialize(false)
                Log.e("TTS", "Initialization failed!")
            }
        }
    }

    fun speak(text: String,context: Context) {
        if (textToSpeech == null) {
            // Reinitialize TTS if it was shut down
            textToSpeech = TextToSpeech(context) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech?.language = Locale.US
                    textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onStart(utteranceId: String?) {
                            Log.d("TTS", "Speech started")
                        }

                        override fun onDone(utteranceId: String?) {
                            Log.d("TTS", "Speech completed")
                            onComplete?.invoke()
                        }

                        override fun onError(utteranceId: String?) {
                            Log.e("TTS", "Speech error")
                        }
                    })
                    textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "UniqueID")
                } else {
                    Log.e("TTS", "Reinitialization failed!")
                }
            }
        } else {
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "UniqueID")
        }
    }


    fun shutdown() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }
}

