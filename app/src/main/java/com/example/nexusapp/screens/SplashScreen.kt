package com.example.nexusapp.screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.nexusapp.MainActivity
import com.example.nexusapp.R
import com.example.nexusapp.Storage.SharedPrefManager

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({

            if(SharedPrefManager(baseContext).token != null){
                startActivity(Intent(baseContext, MainActivity::class.java))
                finish()

            }else{
                startActivity(Intent(baseContext, loginPage::class.java))
                finish()}

        }, 3000)

    }
    }
