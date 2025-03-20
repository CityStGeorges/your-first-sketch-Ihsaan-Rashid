package com.example.moodbloom.data.repository

import android.content.Context
import com.example.moodbloom.R
import com.example.moodbloom.data.di.NetworkModule
import com.example.moodbloom.data.service.Content
import com.example.moodbloom.data.service.GeminiRequest
import com.example.moodbloom.data.service.OpenAIApiService
import com.example.moodbloom.data.service.OpenAIRequest
import com.example.moodbloom.data.service.Part
import com.example.moodbloom.domain.models.ChartDataModel
import com.example.moodbloom.domain.models.HabitTrackerModel
import com.example.moodbloom.domain.models.LogMoodsResponseModel
import com.example.moodbloom.domain.repository.AiRepo
import com.example.moodbloom.extension.ResponseStates
import com.example.moodbloom.extension.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class AiRepoImp @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: OpenAIApiService
) : AiRepo {
    override suspend fun generateInsights(
        habits: List<HabitTrackerModel>,
        moods: List<ChartDataModel>
    ): ResponseStates<String> {
        return try {
            if (context.isNetworkAvailable()) {
                val prompt =
                    "Analyze the following user habits and moods, and provide insightful observations: " +
                            "\nHabits: ${habits.joinToString()}" +
                            "\nMoods: ${moods.joinToString()}" +
                            "\nWhat trends do you observe? What recommendations can you give?"

                val request = OpenAIRequest(
                    messages = listOf(mapOf("role" to "user", "content" to prompt))
                )

                return try {
                    val response = apiService.getInsights(request)
                    response.choices.firstOrNull()?.message?.get("content")
                        ?: "No insights generated."
                    ResponseStates.Success(
                        200, response.choices.firstOrNull()?.message?.get("content")
                            ?: "No insights generated."
                    )
                } catch (e: Exception) {
                    "Error generating insights: ${e.message}"
                    ResponseStates.Failure(
                        999,
                        "Error generating insights: ${e.message}"
                    )
                }
            } else {
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            "Error generating insights: ${e.message}"
            ResponseStates.Failure(
                999,
                "Error generating insights: ${e.message}"
            )
        }
    }


    override suspend fun generateMoodsInsights(
        moods: List<ChartDataModel>
    ): ResponseStates<String> {
        return try {
            if (context.isNetworkAvailable()) {
                val prompt =
                    "Analyze the following user moods and provide insightful observations: " +
                            "\nMoods: ${moods.joinToString()}" +
                            "\nWhat trends do you observe? What recommendations can you give?"

               /* val request = OpenAIRequest(
                    messages = listOf(mapOf("role" to "user", "content" to prompt))
                )*/

                val request = GeminiRequest(
                    contents = listOf(Content(parts = listOf(Part(text = prompt))))
                )

                return try {
                  /*  val response = apiService.getInsights(request)
                    response.choices.firstOrNull()?.message?.get("content")
                        ?: "No insights generated."*/

                    val response = apiService.generateContent(NetworkModule.GEMINI_API_KEY, request)
                    response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No response"
                    ResponseStates.Success(
                        200, response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No response"
                    )
                } catch (e: Exception) {
                    "Error generating insights: ${e.message}"
                    ResponseStates.Failure(
                        999,
                       " ${e.message}"
                    )
                }
            } else {
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            "Error generating insights: ${e.message}"
            ResponseStates.Failure(
                999,
                " ${e.message}"
            )
        }
    }
}





