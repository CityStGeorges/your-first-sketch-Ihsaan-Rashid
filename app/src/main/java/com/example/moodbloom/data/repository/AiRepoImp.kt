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
import com.example.moodbloom.utils.extension.ResponseStates
import com.example.moodbloom.utils.extension.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class AiRepoImp @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: OpenAIApiService
) : AiRepo {
    override suspend fun generateInsights(
        userName:String,
        habits: List<HabitTrackerModel>,
        moods: List<ChartDataModel>
    ): ResponseStates<String> {
        return try {
            if (context.isNetworkAvailable()) {
                val prompt = """
    My name is $userName. I have been tracking my habits and moods.  
    Below is my recorded data:
    
    - **Habits:** ${habits.joinToString()}  
    - **Moods:** ${moods.joinToString()}  

    Please analyze my habits and mood patterns to provide insightful observations.  
    - What trends or correlations do you notice?  
    - Are there any patterns in how my habits affect my moods?  
    - What recommendations can you give to improve my well-being?  
""".trimIndent()

                val request = GeminiRequest(
                    contents = listOf(Content(parts = listOf(Part(text = prompt))))
                )

                return try {
                    val response = apiService.generateContent(NetworkModule.GEMINI_API_KEY, request)
                    response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No response"
                    ResponseStates.Success(
                        200, response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No response"
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
        userName:String,
        lastDays:String,
        moods: List<ChartDataModel>
    ): ResponseStates<String> {
        return try {
            if (context.isNetworkAvailable()) {
                /*val prompt =
                    "My Name is ${userName} Analyze my moods List of Last ${lastDays} and provide insightful observations: " +
                            "\nMoods: ${moods.joinToString()}" +
                            "\nWhat type of mood you observe? What recommendations can you give?"*/
                val prompt = """
    My name is $userName. I have been tracking my moods for the last $lastDays days. 
    Below is a list of my recorded moods:
    
    Moods: ${moods.joinToString()}
    
    Please analyze my mood patterns and provide insightful observations. 
    - What general mood trends do you observe?  
    - Are there any recurring patterns or shifts?  
    - What possible reasons might be influencing my moods?  
    - What actionable recommendations can you give to improve my emotional well-being?  
""".trimIndent()
                val request = GeminiRequest(
                    contents = listOf(Content(parts = listOf(Part(text = prompt))))
                )

                return try {
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





