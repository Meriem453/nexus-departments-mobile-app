package com.example.nexusapp.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.nexusapp.MainActivity
import com.example.nexusapp.R

class loginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        findViewById<Button>(R.id.signInBtnId).setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}