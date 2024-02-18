package com.example.nexusapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexusapp.R
import com.example.nexusapp.models.MeetingResponse
import com.example.nexusapp.screens.components.Header
import com.ramcosta.composedestinations.annotation.Destination
@Preview
@Destination
@Composable
fun MeetingsPage() {
Column(modifier = Modifier
    .fillMaxSize()
    .background(colorResource(id = R.color.gray))
) {
    Header(
        title = "Meetings",
        icon = painterResource(id = R.drawable.add)
    ) {
        //TODO("add new meeting")
    }
val list= listOf(
    MeetingResponse(1,"New meeting","4/5/2024",2),
    MeetingResponse(1,"New meeting","4/5/2024",2),
    MeetingResponse(1,"New meeting","4/5/2024",2),
    MeetingResponse(1,"New meeting","4/5/2024",2),
    MeetingResponse(1,"New meeting","4/5/2024",2),
)
    LazyColumn(
        modifier= Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
            itemsIndexed(list){position,item->
                Column(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(colorResource(R.color.card_bg))
                        .border(
                           width = 2.dp,
                            brush = Brush.horizontalGradient(
                                listOf(
                                    colorResource(id = R.color.green),
                                    colorResource(id = R.color.gray),
                                ), startX = .5f
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    verticalArrangement = Arrangement.SpaceEvenly
                ){
                    Text(text = item.title,
                        fontSize = 12.sp,
                        color = Color.White,
                        modifier = Modifier.padding(20.dp)
                        )
                    Text(text = item.date,
                        fontSize = 7.sp,
                        color = Color.White,
                        modifier = Modifier.padding(20.dp)
                    )
                }

            }
    }
}
}