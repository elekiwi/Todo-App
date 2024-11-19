package com.elekiwi.todobleta.core.di

import android.app.Application
import androidx.room.Room
import com.elekiwi.todobleta.core.data.local.TodoDatabase
import com.elekiwi.todobleta.core.data.repository.FakeAndroidTodoRepository
import com.elekiwi.todobleta.core.domain.repository.TodoRepository
import com.elekiwi.todobleta.todo_list.domain.use_case.DeleteTodo
import com.elekiwi.todobleta.todo_list.domain.use_case.GetAllTodos
import com.elekiwi.todobleta.todo_list.domain.use_case.UpdateTodo
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

    @Provides
    @Singleton
    fun provideTodoRepository(): TodoRepository {
        return FakeAndroidTodoRepository()
    }
    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(todoRepository: TodoRepository): GetAllTodos {
        return GetAllTodos(todoRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteTodoUseCase(todoRepository: TodoRepository): DeleteTodo {
        return DeleteTodo(todoRepository)
    }

    @Provides
    @Singleton
    fun provideUpdaTodoUseCase(todoRepository: TodoRepository): UpdateTodo {
        return UpdateTodo(todoRepository)
    }


}