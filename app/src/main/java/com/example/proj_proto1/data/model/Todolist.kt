package com.example.proj_proto1.data.model

import android.util.Log
import com.example.proj_proto1.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.SignInMethodQueryResult
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.Serializable
import java.util.*


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
        fun parseMembers(csv: String, key: String) : ArrayList<Member>{
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
            val userRef = Firebase.database.reference.child("users")
            userRef.get().addOnSuccessListener {
                for (mem in members){
                    val name = mem.email.toString().substringBefore("@")
                    if (it.hasChild(name)){
                        userRef.child(name).child("keys").child(key).setValue(true)
                    }
                }
            }
            return members
        }
    }
}