package com.ubaya.todo.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ubaya.todo.util.MIGRATION_1_2
import com.ubaya.todo.util.MIGRATION_2_3

@Database(entities = arrayOf(Todo::class), version = 3)
abstract class TodoDatabase:RoomDatabase() {
//diciptakan untuk turunannya
    abstract fun todoDao(): TodoDao

    companion object{
        @Volatile private var instance:TodoDatabase?=null
        // volatile -> data instance dapat otomatis diakses/dibaca oleh thread lain ketika dibutuhkan
        private val LOCK= Any()

        //Membuat database
        private fun buildDatabase(context:Context) = Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java, "tododb")
            .addMigrations(MIGRATION_2_3)
            .build()


        // Memastikan bahwa object tododb adalah sigleton
        operator fun invoke(context: Context){
            if(instance!=null){
                synchronized(LOCK){
                    instance?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}