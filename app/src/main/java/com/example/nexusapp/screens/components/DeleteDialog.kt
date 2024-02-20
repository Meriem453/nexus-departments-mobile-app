package com.example.nexusapp.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.nexusapp.R

@Composable
fun DeleteDialog(title: String, desc: String, cancel: () -> Unit, delete: () -> Unit) {
    Dialog(onDismissRequest = { cancel() }) {


    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(RoundedCornerShape(7.dp))
            .background(colorResource(id = R.color.gray)),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = title,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.padding(16.dp)

            )
        Text(text = "Are you sure you want to delete this ${desc} ?",
            fontSize = 15.sp,
            color = Color.White,
            modifier = Modifier.padding(16.dp)

        )
        Row (
            Modifier
                .fillMaxWidth()
                .padding(16.dp), horizontalArrangement = Arrangement.End){
            Text(text = "Cancel",
                fontSize = 15.sp,
                color = Color.Gray,
                modifier = Modifier.clickable { cancel() }
            )
            Text(text = "Delete",
                fontSize = 15.sp,
                color = colorResource(id = R.color.pink),
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable { delete() }
            )
        }
    }
}}