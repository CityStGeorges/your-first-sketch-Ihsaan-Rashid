package com.example.moodbloom.data.service

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface OpenAIApiService {
    @Headers("Content-Type: application/json")
    @POST("v1/completions")
    suspend fun getInsights(@Body request: OpenAIRequest): OpenAIResponse

    @Headers("Content-Type: application/json")
    @POST("v1beta/models/gemini-2.0-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}
data class GeminiRequest(
    val contents: List<Content>
)

data class Content(
    val parts: List<Part>
)

data class Part(
    val text: String
)

data class GeminiResponse(
    val candidates: List<Candidate>?
)

data class Candidate(
    val content: Content
)

data class OpenAIRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<Map<String, String>>,
    val max_tokens: Int = 150
)

data class OpenAIResponse(val choices: List<Choice>)
data class Choice(val message: Map<String, String>)