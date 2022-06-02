package com.ubaya.todo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubaya.todo.R
import com.ubaya.todo.databinding.FragmentEditTodoBinding
import com.ubaya.todo.model.Todo
import com.ubaya.todo.viewmodel.DetailTodoViewModel
import kotlinx.android.synthetic.main.fragment_create_todo.*

class EditTodoFragment : Fragment(), TodoRadioButtonListener, TodoSaveChangesListener {
    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var dataBinding: FragmentEditTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // dataBinding= FragmentEditTodoBinding.inflate(inflater, container, false)
        dataBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_edit_todo, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        val uuid= EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        observeViewModel()

        /*
        btnAdd.setOnClickListener {
            val radio= view.findViewById<RadioButton>(radioGroupPriority.checkedRadioButtonId)
            viewModel.update(

                txtTitle.text.toString(),
                txtNotes.text.toString(),
                radio.tag.toString().toInt(),
                uuid
            )
            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
        */

        //Instantiate listener
        dataBinding.radioListener= this
        dataBinding.saveListener= this
    }

    private fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner){
            dataBinding.todo= it
            /*
            txtTitle.setText(it.title)
            txtNotes.setText((it.notes))
            when(it.priority){
                1->radioLow.isChecked= true
                2->radioMedium.isChecked= true
                else ->radioHigh.isChecked= true
            }
            */
        }
    }

    override fun onTodoRadioClick(view: View, priority: Int, obj: Todo) {
        obj.priority= priority
    }

    override fun onTodoSaveChangeClicl(view: View, obj: Todo) {
        viewModel.update(obj)
        Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(view).popBackStack()
    }
}