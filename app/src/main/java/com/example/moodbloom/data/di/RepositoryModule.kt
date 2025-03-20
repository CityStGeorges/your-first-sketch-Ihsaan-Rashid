package com.example.moodbloom.data.di


import android.content.Context
import com.example.moodbloom.data.repository.AiRepoImp
import com.example.moodbloom.data.repository.AuthRepoImpl
import com.example.moodbloom.data.repository.ConfigurationRepoImpl
import com.example.moodbloom.data.repository.HabitTrackerRepoImpl
import com.example.moodbloom.data.repository.MoodLogRepoImpl
import com.example.moodbloom.data.service.OpenAIApiService
import com.example.moodbloom.domain.repository.AiRepo
import com.example.moodbloom.domain.repository.AuthRepo
import com.example.moodbloom.domain.repository.ConfigurationRepo
import com.example.moodbloom.domain.repository.HabitTrackerRepo
import com.example.moodbloom.domain.repository.MoodLogsRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Singleton
    @Provides
    fun provideAuthRepo(
        @ApplicationContext context: Context,
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): AuthRepo {
        return AuthRepoImpl(
            context,
            firestore,firebaseAuth
        )
    }

    @Singleton
    @Provides
    fun provideMoodLogsRepo(
        @ApplicationContext context: Context,
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): MoodLogsRepo {
        return MoodLogRepoImpl(
            context,
            firestore,firebaseAuth
        )
    }

    @Singleton
    @Provides
    fun provideHabitTrackerRepo(
        @ApplicationContext context: Context,
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): HabitTrackerRepo {
        return HabitTrackerRepoImpl(
            context,
            firestore,firebaseAuth
        )
    }

    @Singleton
    @Provides
    fun provideConfigurationRepo(
        @ApplicationContext context: Context,
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): ConfigurationRepo {
        return ConfigurationRepoImpl(
            context,
            firestore,firebaseAuth
        )
    }

    @Singleton
    @Provides
    fun provideAiRepo(
        @ApplicationContext context: Context,
        aiApiService: OpenAIApiService
    ): AiRepo {
        return AiRepoImp(
            context,
            aiApiService
        )
    }

}