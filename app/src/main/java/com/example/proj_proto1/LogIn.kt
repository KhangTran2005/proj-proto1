package com.example.proj_proto1

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_log_in.*
import kotlinx.android.synthetic.main.fragment_register.*

class LogIn : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = MainActivity.mAuth.currentUser

        if (user != null && user.isEmailVerified){
            logIn()
        }
        else if (user != null && !user.isEmailVerified){
            Snackbar.make(login_btn, "Go verify your email address", Snackbar.LENGTH_SHORT).show()
            MainActivity.mAuth.signOut()
        }

        login_btn.setOnClickListener{
            val email = email_input.text.toString()
            val password = password_input.text.toString()

            Log.d("debug", "$email : $password")

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(context, "Fields must not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            activity?.let{
                MainActivity.mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(it){ task ->
                        if (task.isSuccessful){
                            if (MainActivity.mAuth.currentUser?.isEmailVerified != true){
                                Toast.makeText(context, "Email isn't verified", Toast.LENGTH_SHORT).show()
                                MainActivity.mAuth.signOut()
                            }
                            else{
                                Toast.makeText(context, "Sign In Success", Toast.LENGTH_SHORT).show()
                                logIn()
                            }
                        }
                        else {
                            Toast.makeText(
                                context,
                                "Sign In Failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        newuser_btn.setOnClickListener{
            val action = LogInDirections.sendToRegister()
            findNavController().navigate(action)
        }
    }

    private fun logIn(){
        val action = LogInDirections.logIn()
        findNavController().navigate(action)
    }
}