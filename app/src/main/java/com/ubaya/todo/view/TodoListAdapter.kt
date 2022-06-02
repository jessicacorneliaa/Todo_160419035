package com.ubaya.todo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.todo.R
import com.ubaya.todo.databinding.TodoItemLayoutBinding
import com.ubaya.todo.model.Todo
import kotlinx.android.synthetic.main.todo_item_layout.*
import kotlinx.android.synthetic.main.todo_item_layout.view.*

class TodoListAdapter (val todoList:ArrayList<Todo>, val adapterOnClick: (Todo)-> Unit):RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(),
    TodoCheckedChangeListener, TodoEditClickListener {
    class TodoViewHolder(var view:TodoItemLayoutBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        // val view = inflater.inflate(R.layout.todo_item_layout, parent, false)
        val view = TodoItemLayoutBinding.inflate(inflater,parent, false)

        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo= todoList[position]
        holder.view.todo= todo
        holder.view.checkboxListener= this
        holder.view.editListener= this
//        val item= todoList[position]

//        with(holder.view) {
//            todo= item
//            val priority = when (todo.priority) {
//                1 -> "Low"
//                2 -> "Medium"
//                else -> "HIGH"
//            }
//
//            checkTask.text= "[$priority] ${todoList[position].title}"
//            checkTask.setOnCheckedChangeListener { compoundButton, b ->
//                if(b)adapterOnClick(todo)
//            }
//            imgEdit.setOnClickListener {
//                val action= TodoListFragmentDirections.actionEditTodoFragment(todo.uuid)
//                Navigation.findNavController(it).navigate(action)
//            }
//
//            checkTask.setOnClickListener {
//                val action= TodoListFragmentDirections.actionTodoListFragmentSelf(1)
//                Navigation.findNavController(it).navigate(action)
//            }
//        }
    }

    override fun getItemCount()= todoList.size

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun onCheckedChanged(cb: CompoundButton, isChecked: Boolean, obj: Todo) {
        if(isChecked) adapterOnClick(obj)
    }

    override fun onTodoEditClick(view: View) {
        val uuid= view.tag.toString().toInt()
        val action= TodoListFragmentDirections.actionEditTodoFragment(uuid)
        Navigation.findNavController(view).navigate(action)
    }
}