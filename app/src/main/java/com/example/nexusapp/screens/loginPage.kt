package com.example.nexusapp.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nexusapp.MainActivity
import com.example.nexusapp.R
import com.example.nexusapp.Storage.SharedPrefManager
import com.example.nexusapp.api.RetrofitClient
import com.example.nexusapp.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class loginPage : AppCompatActivity() {
    private lateinit var etEmail : EditText
    private lateinit var etPass : EditText
    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        etEmail = findViewById(R.id.username)
        etPass = findViewById(R.id.password)
        progressBar = findViewById(R.id.login_progressBar)
        findViewById<Button>(R.id.signInBtnId).setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPass.text.toString()
            if(email.isEmpty()||password.isEmpty()){
                if(email.isEmpty()){
                    etEmail.error = "please enter an email!"
                }
                if(password.isEmpty()){
                    etPass.error = "please enter a password!"
                }
            }else{
                progressBar.visibility = View.VISIBLE
                RetrofitClient.instance.userLogin(email, password)
                    .enqueue(object: Callback<LoginResponse>{
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                            etEmail.setText(t.message)
                        }override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                            if(response.body()?.token!!.toString()!="User not found" && response.body()?.token!!.toString()!="Check your credentials"){

                                sharedPrefManager = SharedPrefManager(this@loginPage)
                                val token = response.body()?.token!!
                                sharedPrefManager.token = token

                                Toast.makeText(applicationContext,sharedPrefManager.token , Toast.LENGTH_SHORT).show()
                                progressBar.visibility = View.GONE

                                val intent = Intent(applicationContext, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)


                            }else{
                                progressBar.visibility = View.GONE
                                Toast.makeText(applicationContext, response.body()?.token!!, Toast.LENGTH_LONG).show()
                            }

                        }
                    })

            }
            }

        }

    override fun onStart() {
        super.onStart()
        sharedPrefManager = SharedPrefManager(this@loginPage)
        // Check if token is available
        val token = sharedPrefManager.token
        if (token != null && token.isNotEmpty()) {
            // Token is available, navigate to main screen
            Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
    }
