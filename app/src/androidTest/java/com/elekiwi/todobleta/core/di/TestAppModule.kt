package com.elekiwi.todobleta.core.di

import android.app.Application
import androidx.room.Room
import com.elekiwi.todobleta.core.data.local.TodoDao
import com.elekiwi.todobleta.core.data.local.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(application: Application): TodoDatabase {
        return Room.inMemoryDatabaseBuilder(
            application,
            TodoDatabase::class.java
        ).build()
    }

}