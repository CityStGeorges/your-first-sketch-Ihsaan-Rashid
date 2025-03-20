package com.example.moodbloom.data.repository

import android.content.Context
import android.util.Log
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.HabitTrackerModel
import com.example.moodbloom.domain.repository.HabitTrackerRepo
import com.example.moodbloom.extension.ResponseStates
import com.example.moodbloom.extension.isNetworkAvailable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HabitTrackerRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : HabitTrackerRepo {
    private val habitsCollection = firestore.collection("habits")


    override suspend fun insertHabit(request: HabitTrackerModel): ResponseStates<String> {
        return try {
            if (context.isNetworkAvailable()) {
                    val existingHabits = habitsCollection
                        .whereEqualTo("userId", request.userId)
                        .whereEqualTo("title", request.title)
                        .get()
                        .await()

                    if (!existingHabits.isEmpty) {
                        Log.e("HabitRepo", "Your ${request.title} Habit already exists")
                        return ResponseStates.Failure(111,"Your ${request.title} Habit already exists in tracker. delete it then you can add again")
                    }

                    val habitDoc = habitsCollection.document()

                    habitDoc.set(request).await()

                    Log.d("HabitRepo", "Habit inserted successfully: ${request.title}")
                    ResponseStates.Success(200,"Your ${request.title} Habit added in tracker successfully.")
            }else{
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            Log.e("RepoImp", "Error inserting habit in tracker: ${e.message}")
            ResponseStates.Failure(999, "${e.localizedMessage}")
        }
    }

    override suspend fun updateHabit(request: HabitTrackerModel): ResponseStates<String> {
        return try {
            if (context.isNetworkAvailable()) {
                val habitQuery = habitsCollection
                    .whereEqualTo("userId", request.userId)
                    .whereEqualTo("title", request.title)
                    .get()
                    .await()

                if (habitQuery.isEmpty) {
                    Log.e("HabitRepo", "Your ${request.title} Habit not exists in tracker")
                    return ResponseStates.Failure(111,"Your ${request.title} Habit no exists in tracker. insert it first")
                }

                val habitDoc = habitQuery.documents.first().reference
                habitDoc.set(request).await()

                Log.d("HabitRepo", "Habit inserted successfully: ${request.title}")
                ResponseStates.Success(200,"Your ${request.title} Habit tracker updated.")
            }else{
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            Log.e("RepoImp", "Error updating habit in tracker: ${e.message}")
            ResponseStates.Failure(999, "${e.localizedMessage}")
        }
    }

    override suspend fun deleteHabit(habitTitle: String, userId: String): ResponseStates<String> {
        return try {
            if (context.isNetworkAvailable()) {
                val habitQuery = habitsCollection
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("title", habitTitle)
                    .get()
                    .await()

                if (habitQuery.isEmpty) {
                    Log.e("HabitRepo", "Habit not found: $habitTitle")
                    return ResponseStates.Failure(111,"$habitTitle Habit not found")
                }

                val habitDoc = habitQuery.documents.first().reference
                habitDoc.delete().await()
                Log.d("HabitRepo", "Habit deleted successfully: $habitTitle")
                ResponseStates.Success(200,"$habitTitle Habit has been removed from tracker.")
            }else{
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            Log.e("RepoImp", "Error deleting habit from tracker: ${e.message}")
            ResponseStates.Failure(999, "${e.localizedMessage}")
        }
    }

    override suspend fun getAllHabits(userId: String): ResponseStates<List<HabitTrackerModel>> {
        return try {
            if (context.isNetworkAvailable()) {
                val snapshot = habitsCollection
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()
                ResponseStates.Success(200, snapshot.documents.mapNotNull { it.toObject(HabitTrackerModel::class.java) })
            }else{
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            Log.e("RepoImp", "Error fetching habits: ${e.message}")
            ResponseStates.Failure(999, "${e.localizedMessage}")
        }
    }

}