package com.example.lab3

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.example.proj_proto1.R
import com.example.proj_proto1.data.model.Todolist
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.issue_dialog.*
import org.w3c.dom.Text
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Suppress("DEPRECATION")
class IssueDialog() : DialogFragment() {

    private var bp: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (getDialog() != null && getDialog()?.getWindow() != null) {
            getDialog()?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            getDialog()?.getWindow()?.requestFeature(Window.FEATURE_NO_TITLE);
        }
        return inflater.inflate(R.layout.issue_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        submit_btn.setOnClickListener{
            if (TextUtils.isEmpty(todoNameET.text) || TextUtils.isEmpty(membersET.text)){
                Toast.makeText(requireContext(), "empty fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val client = Firebase.database.reference
                .child("todolists")
                .push()
            client.setValue(Todolist(client.key, todoNameET.text.toString(), Todolist.parseMembers(membersET.text.toString(), client.key.toString()), hashMapOf()))
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "todolist submitted", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                .addOnFailureListener{
                    Toast.makeText(requireContext(), "failed to submit", Toast.LENGTH_SHORT).show()
                }
        }
    }

    companion object{
        const val TAG = "issue_dialog"
    }
}