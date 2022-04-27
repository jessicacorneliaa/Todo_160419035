package com.ubaya.todo.model

import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg todo:Todo)
    // suspend berarti function/prosesnya bisa di pause oleh thread (tergantung oleh thread-nya)

    @Query("SELECT*FROM todo WHERE is_done=0 ORDER BY priority DESC")
    suspend fun selectAllTodo(): List<Todo>
    // menghasilkan banyak to do

    @Query("SELECT*FROM todo WHERE uuid= :id")
    suspend fun selectTodo(id:Int) :Todo
    // menghasilkan satu to do

    @Query("UPDATE todo SET title=:title, notes=:notes, priority=:priority WHERE uuid = :id")
    suspend fun update(title:String, notes:String, priority:Int, id:Int)

    @Query("UPDATE todo SET is_done=:is_done WHERE uuid = :id")
    suspend fun updateIsDone(is_done:Int, id:Int)


    @Delete
    suspend fun deleteTodo(todo:Todo)
}