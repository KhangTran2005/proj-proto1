package com.example.proj_proto1.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.TodoDialog
import com.example.proj_proto1.R
import com.example.proj_proto1.data.model.Todolist
import com.example.proj_proto1.ui.adapter.TodolistAdapter
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_todolist_detail.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class TodolistDetail : Fragment() {
    private lateinit var todolist: Todolist

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        todolist = Json.decodeFromString(requireArguments().getString("todolist").toString())

        return inflater.inflate(R.layout.fragment_todolist_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val options = FirebaseRecyclerOptions.Builder<Todolist.Todo>()
            .setLifecycleOwner(viewLifecycleOwner)
            .setQuery(
                Firebase.database.reference.child("todolists").child("${todolist.id}").child("list"),
                Todolist.Todo::class.java
            )
            .build()

        Log.d("debug", "${todolist.id}")

        val firebaseAdapter = object: FirebaseRecyclerAdapter<Todolist.Todo, TodolistDetail.ViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodolistDetail.ViewHolder {
                val view = layoutInflater.inflate(R.layout.todo_card, parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Todolist.Todo) {
                holder.bind(model)
            }
        }

        todolist_recycler.apply{
            adapter = firebaseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        fabTodo.setOnClickListener{
            TodoDialog(todolist).show(childFragmentManager, TodoDialog.TAG)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.todolist_title)
        val members = itemView.findViewById<TextView>(R.id.members)
        val checkbox = itemView.findViewById<CheckBox>(R.id.todoCheckBox)

        fun bind(todo: Todolist.Todo){

            checkbox.isChecked = false

            checkbox.setOnClickListener{
                Firebase.database.reference.child("todolists")
                    .child("${todolist.id}").child("list")
                    .child(todo.task.toString()).removeValue()
            }

            title.text = todo.task
            members.text = todo.date
        }
    }
}