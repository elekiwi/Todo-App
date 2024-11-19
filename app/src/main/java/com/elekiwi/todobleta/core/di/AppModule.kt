package com.elekiwi.todobleta.core.di

import android.app.Application
import androidx.room.Room
import com.elekiwi.todobleta.add_todo.domain.use_case.UpsertTodo
import com.elekiwi.todobleta.core.data.local.TodoDatabase
import com.elekiwi.todobleta.core.data.repository.TodoRepositoryImpl
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

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db)
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

    @Provides
    @Singleton
    fun provideUpsertNoteUseCase(todoRepository: TodoRepository): UpsertTodo {
        return UpsertTodo(todoRepository)
    }
}