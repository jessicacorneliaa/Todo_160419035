package com.ubaya.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ubaya.todo.model.Todo
import com.ubaya.todo.model.TodoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application):AndroidViewModel(application), CoroutineScope {
    val todoLD= MutableLiveData<List<Todo>>()
    val todoLoadErrorLD= MutableLiveData<Boolean>()
    val loadingLD= MutableLiveData<Boolean>()

    private var job= Job()

    override val coroutineContext: CoroutineContext
    get()= job  + Dispatchers.Main

    fun refresh(){
        loadingLD.value= true
        todoLoadErrorLD.value= false
        launch {
            val db= Room.databaseBuilder(
                getApplication(),
                TodoDatabase::class.java, "tododb"
            ).build()
            todoLD.value= db.todoDao().selectAllTodo()
        }
    }

    fun clearTask(todo: Todo){
        launch {
            val db= Room.databaseBuilder(
                getApplication(),
                TodoDatabase::class.java, "tododb"
            ).build()
            db.todoDao().deleteTodo(todo)
            todoLD.value= db.todoDao().selectAllTodo()
        }
    }
    fun checkTask(is_done:Int, id:Int){
        launch {
            val db= Room.databaseBuilder(
                getApplication(),
                TodoDatabase::class.java, "tododb"
            ).build()
            db.todoDao().updateIsDone(is_done, id)
            todoLD.value= db.todoDao().selectAllTodo()
        }
    }
}

