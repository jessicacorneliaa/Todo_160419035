package com.ubaya.todo.view

import android.view.View
import android.webkit.WebSettings
import android.widget.CompoundButton
import com.ubaya.todo.model.Todo

interface TodoCheckedChangeListener{
    fun onCheckedChanged( cb: CompoundButton, isChecked: Boolean, obj: Todo)
}

interface TodoEditClickListener{
    fun onTodoEditClick(view:View)
}

interface TodoRadioButtonListener{
    fun onTodoRadioClick(view: View, priority: Int, obj: Todo)
}

interface TodoSaveChangesListener{
    fun onTodoSaveChangeClicl(view: View, obj: Todo)
}