package com.example.proj_proto1.ui

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3.IssueDialog
import com.example.proj_proto1.R
import com.example.proj_proto1.data.model.Todolist
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Home : Fragment() {

    private val database: FirebaseDatabase by lazy{
        FirebaseDatabase.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener{
            IssueDialog().show(childFragmentManager, IssueDialog.TAG)
        }

        val options = FirebaseRecyclerOptions.Builder<Todolist>()
            .setLifecycleOwner(viewLifecycleOwner)
            .setQuery(database.reference.child("todolists"), Todolist::class.java)
            .build()

        val firebaseAdapter = object: FirebaseRecyclerAdapter<Todolist, Home.ViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = layoutInflater.inflate(R.layout.todolist_card, parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Todolist) {
                holder.bind(model)
            }
        }

        homeRecycler.apply {
            adapter = firebaseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val title = item.findViewById<TextView>(R.id.todolist_title)
        val members = item.findViewById<TextView>(R.id.members)

        fun bind (list: Todolist){

            itemView.setOnClickListener{
                findNavController().navigate(R.id.todolistDetail)
            }

            title.text = list.todo_name
            members.text = if (list.members != null) "${list.members?.size.toString()} members" else "0 members"
        }
    }
}