package com.elekiwi.todobleta.core.data.mapper

import com.elekiwi.todobleta.core.data.local.TodoEntity
import com.elekiwi.todobleta.core.domain.model.TodoItem

fun TodoItem.toTodoEntityForInsert(): TodoEntity {
    return TodoEntity(
        title = title,
        description = description,
        isDone = isDone
    )
}
fun TodoItem.toEditedTodoEntity(): TodoEntity = TodoEntity(
    id = id,
    title = title,
    description = description,
    isDone = isDone
)


fun TodoItem.toTodoEntityForDelete(): TodoEntity {
    return TodoEntity(
        id = id,
        title = title,
        description = description,
        isDone = isDone
    )
}

fun TodoEntity.toTodoItem(): TodoItem {
    return TodoItem(
        id = id ?: 0,
        title = title,
        description = description ?: "",
        isDone = isDone
    )
}