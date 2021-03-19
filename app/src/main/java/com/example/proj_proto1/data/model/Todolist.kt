package com.example.proj_proto1.data.model

import com.example.proj_proto1.MainActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.Serializable
import java.util.*
import kotlin.collections.ArrayList

@Serializable
data class Todolist(
    var id: String? = null,
    var todo_name: String? = null,
    var members: ArrayList<Member>? = null,
    var list: HashMap<String, Todo>? = null
){

    @Serializable
    data class Member(
        var email: String? = null,
    )

    @Serializable
    data class Todo(
        var task: String? = null,
        var date: String? = null,
        var priority: Int? = null,
    )

    companion object{
        fun parseMembers(csv: String) : ArrayList<Member>{
            val reader = Scanner(csv)
            reader.useDelimiter("[,]")
            val members = arrayListOf<Member>()
            while(reader.hasNext()){
                val email = reader.next()
                members.add(Member(email.trim()))
            }
            MainActivity.mAuth.currentUser?.let {
                members.add(Member(it.email))
            }
            return members
        }
    }
}