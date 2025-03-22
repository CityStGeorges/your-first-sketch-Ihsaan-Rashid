package com.example.moodbloom.data.repository

import android.content.Context
import android.util.Log
import com.example.moodbloom.R
import com.example.moodbloom.domain.models.ConfigurationModel
import com.example.moodbloom.domain.repository.ConfigurationRepo
import com.example.moodbloom.utils.extension.ResponseStates
import com.example.moodbloom.utils.extension.isNetworkAvailable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ConfigurationRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : ConfigurationRepo {
    private val moodsCollection = firestore.collection("configs")
    override suspend fun checkConfigExists(userId: String): ConfigurationModel? {
        return try {
            if (context.isNetworkAvailable()) {
                val querySnapshot: QuerySnapshot = moodsCollection
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    querySnapshot.documents.first().toObject(ConfigurationModel::class.java)
                } else {
                    null
                }
            } else {
                Log.e("MoodLogRepoImpl", "Check your Internet connection")
                null
            }
        } catch (e: Exception) {
            Log.e("MoodLogRepoImpl", "Error checking today's mood: ${e.message}")
            null
        }
    }


    override suspend fun addOrUpdateConfig(request: ConfigurationModel): ResponseStates<String> {
        return try {
            if (context.isNetworkAvailable()) {
                val existingMood = checkConfigExists(request.userId)

                if (existingMood != null) {
                    val querySnapshot = moodsCollection
                        .whereEqualTo("userId", request.userId)
                        .get()
                        .await()
                    querySnapshot.documents.first().reference.set(request, SetOptions.merge())
                        .await()
                    ResponseStates.Success(200, "Your Configuration Updated Successfully.")
                } else {
                    moodsCollection.add(request).await()
                    ResponseStates.Success(200, "Your Configuration Updated Successfully.")
                }
            } else {
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            Log.e("RepoImpl", "Error checking: ${e.message}")
            ResponseStates.Failure(999, "${e.localizedMessage}")
        }
    }

    override suspend fun getUserConfig(userId: String): ResponseStates<ConfigurationModel> {
        return try {
            if (context.isNetworkAvailable()) {
                val querySnapshot = moodsCollection
                    .whereEqualTo("userId", userId)
                    .get()
                    .await()
                if (querySnapshot.documents.isNotEmpty()){
                    querySnapshot.documents.first().toObject(ConfigurationModel::class.java)?.let {
                        ResponseStates.Success(
                            200, it
                        )
                    } ?: run {
                        val config = ConfigurationModel(userId = userId)
                        addOrUpdateConfig(config)
                        ResponseStates.Success(
                            200, config
                        )
                    }
                }else{
                    val config = ConfigurationModel(userId = userId)
                    addOrUpdateConfig(config)
                    ResponseStates.Success(
                        200, config
                    )
                }
            } else {
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            Log.e("RepoImpl", "Error fetching: ${e.message}")
            ResponseStates.Failure(999, "${e.localizedMessage}")
        }
    }
    private val requests = firestore.collection("requests")
    override suspend fun requestToDeleteAccount(userId: String): ResponseStates<String> {
        return try {
            if (context.isNetworkAvailable()) {
                val req = hashMapOf(
                    "uid" to userId,
                    "request" to "Request for delete account")
                    requests.add(req).await()
                    ResponseStates.Success(200, "We have received your account deletion request. We will process your request within 24 hours.")
            } else {
                ResponseStates.Failure(
                    111,
                    context.getString(R.string.connected_to_the_internet_and_try_again)
                )
            }
        } catch (e: Exception) {
            Log.e("RepoImpl", "Error checking: ${e.message}")
            ResponseStates.Failure(999, "${e.localizedMessage}")
        }
    }

}