package com.elekiwi.todobleta.core.di

import android.app.Application
import androidx.room.Room
import com.elekiwi.todobleta.core.data.local.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(application: Application): TodoDatabase {
        return Room.databaseBuilder(
            application,
            TodoDatabase::class.java,
            "todos_db.db"
        ).build()
    }
}