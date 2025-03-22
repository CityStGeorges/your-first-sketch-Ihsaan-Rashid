package com.example.moodbloom.utils


import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

class TextToSpeechHelper(context: Context, private val onComplete: (() -> Unit)? = null) {
    private var textToSpeech: TextToSpeech? = null

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
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
                Log.e("TTS", "Initialization failed!")
            }
        }
    }

    fun speak(text: String) {
        val params = HashMap<String, String>()
        params[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "UniqueID"
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, params)
    }

    fun shutdown() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }
}

