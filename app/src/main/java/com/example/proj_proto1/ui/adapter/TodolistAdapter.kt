package com.example.proj_proto1.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.IssueDialog
import com.example.proj_proto1.R
import com.example.proj_proto1.data.model.Todolist
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TodolistAdapter(var todolist: Todolist) : RecyclerView.Adapter<TodolistAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.todolist_title)
        val members = itemView.findViewById<TextView>(R.id.members)
        val checkbox = itemView.findViewById<CheckBox>(R.id.todoCheckBox)

        fun bind(todo: Todolist.Todo){

            title.text = todo.task
            members.text = todo.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.todo_card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        todolist.list?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return if (todolist.list != null) todolist.list!!.size else 0
    }
}