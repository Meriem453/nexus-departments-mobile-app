package com.example.nexusapp.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexusapp.R

@Composable
fun Header(
     title:String,
     icon:Painter,
     onBackPressed:()->Unit
) {
    Row (
        Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.gray)), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
        Icon(painter = painterResource(id = R.drawable.arrow_back),
            contentDescription ="" ,
            tint = Color.White,
            modifier = Modifier
                .padding(16.dp)
                .clickable { onBackPressed() }
        )
        Text(text = title,
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
        Icon(painter = icon,
            contentDescription ="" ,
            tint = Color.White,
            modifier = Modifier.padding(16.dp))


    }
}