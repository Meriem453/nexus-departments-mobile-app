package com.example.nexusapp.screens

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexusapp.R
import com.example.nexusapp.Storage.SharedPrefManager
import com.example.nexusapp.screens.components.Header

@Composable
fun SettingsPage(){
    val context=LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray))
    ) {
        Header(title = "Settings", icon = painterResource(id = R.drawable.add), onBackPressed = { /*TODO*/ }) {}
       Text(text = "Logout",
           modifier = Modifier
               .padding(20.dp)
               .clickable {
                   SharedPrefManager(context).token = null
                   val intent = Intent(context, loginPage::class.java)
                   context.startActivity(intent)
                   (context as? ComponentActivity)?.finish()

               }
           ,
           fontSize = 20.sp,
           color = Color.White
           )
    }
}