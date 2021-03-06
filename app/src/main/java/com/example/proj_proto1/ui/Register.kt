package com.example.proj_proto1.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proj_proto1.MainActivity
import com.example.proj_proto1.R
import kotlinx.android.synthetic.main.fragment_register.*


class Register : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        register_btn.setOnClickListener{

            val email = regi_email_input.text.toString()
            val password = regi_password_input.text.toString()
            val confirm_password = confirm_password_input.text.toString()

            if (!password.equals(confirm_password)){
                regi_password_input.setText("")
                confirm_password_input.setText("")

                Toast.makeText(context, "Confimation Password does not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            activity?.let {
                MainActivity.mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(it) { task ->
                        if (task.isSuccessful){

                            //send email verification
                            val user = MainActivity.mAuth.currentUser
                            if (user != null){
                                activity?.let{
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(it) { task ->
                                                if (task.isSuccessful){
                                                    Log.d("debug", "Email verification sent")
                                                }
                                                else{
                                                    Log.d("debug", "Email verification failed to send")
                                                }
                                            }
                                }
                            }

                            findNavController().navigate(R.id.logIn, bundleOf("username" to regi_name_input.text.toString()))
                        }
                        else{
                            Toast.makeText(
                                context,
                                "Register Failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}