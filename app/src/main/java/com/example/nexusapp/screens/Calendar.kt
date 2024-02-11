package com.example.nexusapp.screens

import android.preference.PreferenceActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.geometry.Offset
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
import com.example.nexusapp.ui.theme.NexusAppTheme
data class day(val num:Int,val letter:String,val state:Boolean)
@Composable
fun Calendar() {

    var days by remember {
        mutableStateOf(
            listOf(
                day(23,"M",true),
                day(24,"T",true),
                day(25,"W",false),
                day(26,"T",false),
                day(27,"F",false),
                day(28,"S",true),
                day(29,"S",false),
                day(30,"M",false),
                day(31,"T",false),

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
       Header()
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
                Brush.horizontalGradient(startX = .5f,colors = listOf( colorResource(id = R.color.gray), colorResource(id = R.color.green)
                )
            ),
                RoundedCornerShape(12.dp)
            )
    ){
         itemsIndexed(days){position,day->
             Column(
                 horizontalAlignment = Alignment.CenterHorizontally,
                 modifier = Modifier
                     .padding(15.dp)
                     .clip(RoundedCornerShape(13.dp))
                     .background(
                         if (day.state) colorResource(id = R.color.green)
                         else colorResource(id = R.color.gray)
                     )
             ) {
                Text(text = day.num.toString(),
                    fontSize = 16.sp,
                    color = if (day.state) colorResource(id = R.color.gray)
                    else Color.White,
                    modifier = Modifier.padding(top=15.dp, start = 15.dp, end = 15.dp)
                    )
                 Spacer(modifier = Modifier
                     .fillMaxWidth()
                     .size(10.dp))
                 Text(text = day.letter,
                     fontSize = 16.sp,
                     color = if (day.state) colorResource(id = R.color.gray)
                     else Color.White,
                     modifier = Modifier.padding(bottom =15.dp, start = 15.dp, end = 15.dp)
                 )
             }

         }
    }
}

@Composable
fun Header(){
    Row (
        Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.gray)), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
       Icon(painter = painterResource(id = R.drawable.arrow_back),
           contentDescription ="" ,
           tint = Color.White,
           modifier = Modifier.padding(16.dp))
        Text(text = "Calendar",
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
            )
        Icon(painter = painterResource(id = R.drawable.calendar),
            contentDescription ="" ,
            tint = Color.White,
            modifier = Modifier.padding(16.dp))


    }
}
@Preview(showBackground = true)
@Composable
fun Preview() {
    NexusAppTheme {
       Calendar()
    }
}