package com.example.moodbloom.data.repository

import android.content.Context
import android.util.Log
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.LogMoodsRequest
import com.example.moodbloom.domain.models.LogMoodsResponseModel
import com.example.moodbloom.domain.repository.MoodLogsRepo
import com.example.moodbloom.utils.extension.ResponseStates
import com.example.moodbloom.utils.extension.isNetworkAvailable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MoodLogRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : MoodLogsRepo {
    private val moodsCollection = firestore.collection("moods")
    override suspend fun checkTodayMoodExists(userId: String, date: String): LogMoodsResponseModel? {
        return try {
            if (context.isNetworkAvailable()) {
                val querySnapshot: QuerySnapshot = moodsCollection
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("date", date)
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    querySnapshot.documents.first().toObject(LogMoodsResponseModel::class.java)
                } else {
                  null
                }
            }else{
                Log.e("MoodLogRepoImpl", "Check your Internet connection")
               null
            }
        } catch (e: Exception) {
            Log.e("MoodLogRepoImpl", "Error checking today's mood: ${e.message}")
           null
        }
    }


    override suspend fun addOrUpdateMood(request: LogMoodsRequest): ResponseStates<String> {
        return try {
            if (context.isNetworkAvailable()) {
                val existingMood = checkTodayMoodExists(request.userId, request.date)

                if (existingMood != null) {
                    val querySnapshot = moodsCollection
                        .whereEqualTo("userId", request.userId)
                        .whereEqualTo("date", request.date)
                        .get()
                        .await()

                    querySnapshot.documents.first().reference.set(request, SetOptions.merge()).await()
                   ResponseStates.Success(200, "Your mood entry has been successfully updated for today.")
                } else {
                    moodsCollection.add(request).await()
                    ResponseStates.Success(200,   "Your mood entry has been successfully logged for today.")
                }
            }else{
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            Log.e("MoodLogRepoImpl", "Error checking today's mood: ${e.message}")
            ResponseStates.Failure(999, "${e.localizedMessage}")
        }
    }
    override suspend fun getUserMoodsList(userId: String): ResponseStates<List<LogMoodsResponseModel>> {
        return try {
            if (context.isNetworkAvailable()) {
                val querySnapshot = moodsCollection
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()
                ResponseStates.Success(
                    200,
                    querySnapshot.documents.mapNotNull { it.toObject(LogMoodsResponseModel::class.java) })
            }else{
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            Log.e("MoodLogRepoImpl", "Error fetching user moods: ${e.message}")
            ResponseStates.Failure(999, "${e.localizedMessage}")
        }
    }

}