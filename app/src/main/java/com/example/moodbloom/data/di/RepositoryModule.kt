package com.example.moodbloom.data.di


import android.content.Context
import com.example.moodbloom.data.repository.AuthRepoImpl
import com.example.moodbloom.domain.repository.AuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

   /* @Singleton
    @Provides
    fun provideLoginLocalPrefsRepo(
        datastorePreferences: DatastorePreferences,
        securityConfigProvider: SecurityConfigProvider,
    ): CustomerLocalPrefsRepo {
        return CustomerLocalPrefsRepoImpl(datastorePreferences,securityConfigProvider)
    }*/

    @Singleton
    @Provides
    fun provideAuthRepo(
        @ApplicationContext context: Context,
    ): AuthRepo {
        return AuthRepoImpl(
            context
        )
    }

}