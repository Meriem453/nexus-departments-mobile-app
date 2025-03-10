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
                            progressBar.visibility = View.GONE
                        }
                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                            if(response.body()?.token!=null){
                                sharedPrefManager = SharedPrefManager(this@loginPage)
                                val token = response.body()?.token!!
                                sharedPrefManager.token = token

                                progressBar.visibility = View.GONE

                                val intent = Intent(applicationContext, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }else{
                                progressBar.visibility = View.GONE
                                val errorMessage = response.errorBody()?.string()
                                Toast.makeText(applicationContext, response.message()+":"+ errorMessage, Toast.LENGTH_LONG).show()
                            }
                        }
                    })

            }
            }

        }


    }
