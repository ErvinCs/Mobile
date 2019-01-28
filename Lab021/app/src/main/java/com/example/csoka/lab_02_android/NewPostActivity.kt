package com.example.csoka.lab_02_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.csoka.lab_02_android.R
import com.example.csoka.lab_02_android.model.Post
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class NewPostActivity : AppCompatActivity() {
    final val postsUrl = "http://10.0.2.2:8080/posts" //"http://127.0.0.1:8080/posts"

    final val EXTRA_POST_REPLY = "com.example.csoka.lab_02_android.POST_REPLY"

    lateinit var editName : EditText
    lateinit var editMemberLimit : EditText
    lateinit var editDate : EditText
    lateinit var editTime : EditText
    lateinit var editLocation : EditText
    lateinit var editDescription : EditText
    lateinit var createButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
        createButton = findViewById(R.id.createButton)
        createButton.setOnClickListener { createActivity()}
    }

    private fun createActivity() {
        editName = findViewById(R.id.editName)
        editMemberLimit = findViewById(R.id.editMemberLimit)
        editDate = findViewById(R.id.editDate)
        editTime = findViewById(R.id.editTime)
        editLocation = findViewById(R.id.editLocation)
        editDescription = findViewById(R.id.editDescription)
        createButton = findViewById(R.id.createButton)

        val replyIntent : Intent = Intent()
        if (editName.text.isEmpty() || editMemberLimit.text.isEmpty() ||
                editDate.text.isEmpty() || editTime.text.isEmpty() ||
                editLocation.text.isEmpty()) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            val post = Post(
                "TODO - username", editName.text.toString(),
                editMemberLimit.text.toString().toInt(), editDate.text.toString(),
                editTime.text.toString(), editLocation.text.toString()
            )
            post.setId(0);  //treat 0 as not set
            replyIntent.putExtra(EXTRA_POST_REPLY, post)
            setResult(Activity.RESULT_OK, replyIntent)
        }
        finish()
    }
}