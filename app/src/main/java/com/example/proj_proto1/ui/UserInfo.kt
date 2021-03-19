package com.example.proj_proto1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.proj_proto1.MainActivity
import com.example.proj_proto1.R
import kotlinx.android.synthetic.main.fragment_user_info.*

class UserInfo : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_user_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout_btn.setOnClickListener{
            MainActivity.mAuth.signOut()
            val action = UserInfoDirections.logOut()
            findNavController().navigate(action)
        }
    }
}