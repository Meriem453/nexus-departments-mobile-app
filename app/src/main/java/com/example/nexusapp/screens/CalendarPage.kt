package com.example.nexusapp.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexusapp.R
import com.example.nexusapp.screens.components.Header
import com.example.nexusapp.ui.theme.NexusAppTheme
import com.ramcosta.composedestinations.annotation.Destination

data class day(val num:Int,val letter:String)
@Destination
@Composable
fun Calendar() {

    var days by remember {
        mutableStateOf(
            listOf(
                day(23,"M"),
                day(24,"T"),
                day(25,"W"),
                day(26,"T"),
                day(27,"F"),
                day(28,"S"),
                day(29,"S"),
                day(30,"M"),
                day(31,"T"),

                ))
    }
Scaffold(
    Modifier
        .fillMaxSize()
        ) {
    it
    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray)), horizontalAlignment = Alignment.CenterHorizontally) {
       Header(
           "Calendar",
           painterResource(id = R.drawable.calendar),
           {
               //TODO("idk what to do")
           }
       ){
           //TODO("naviagte to main menu")
       }
        Text(text = "February",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 50.dp, bottom = 15.dp),
            textAlign = TextAlign.Center
        )
        TimeLine(days)
        Event()

    }
}
}
@Composable
fun Event() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.radialGradient(
                listOf(
                    Color(0xFF76E494),
                    colorResource(id = R.color.gray),
                ),
                // radius = .8f
            )
        )
    ){
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(text = "event",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(text = "Nexuzero",
                color = Color.White,
                fontSize = 48.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Text(text = "CTF",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
fun TimeLine(days:List<day>) {
    LazyRow (
        Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 0.dp,
                    topStart = 0.dp,
                    topEnd = 10.dp,
                    bottomEnd = 10.dp,

                    )
            )
            .padding(end = 16.dp)
            .border(
                2.dp,
                Brush.horizontalGradient(
                    startX = .5f, colors = listOf(
                        colorResource(id = R.color.gray), colorResource(id = R.color.green)
                    )
                ),
                RoundedCornerShape(12.dp)
            )
    ){
         itemsIndexed(days){position,day->
             var selectedDay by remember {
                 mutableStateOf(0)
             }
             Column(
                 horizontalAlignment = Alignment.CenterHorizontally,
                 modifier = Modifier
                     .padding(15.dp)
                     .clip(RoundedCornerShape(13.dp))
                     .background(
                         if (selectedDay==position) colorResource(id = R.color.green)
                         else colorResource(id = R.color.gray)
                     )
                     .clickable { selectedDay = position }
             ) {
                Text(text = day.num.toString(),
                    fontSize = 16.sp,
                    color = if (position==selectedDay) colorResource(id = R.color.gray)
                    else Color.White,
                    modifier = Modifier.padding(top=15.dp, start = 15.dp, end = 15.dp)
                    )
                 Spacer(modifier = Modifier
                     .fillMaxWidth()
                     .size(10.dp))
                 Text(text = day.letter,
                     fontSize = 16.sp,
                     color = if (position==selectedDay) colorResource(id = R.color.gray)
                     else Color.White,
                     modifier = Modifier.padding(bottom =15.dp, start = 15.dp, end = 15.dp)
                 )
             }

         }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    NexusAppTheme {
       Calendar()
    }
}