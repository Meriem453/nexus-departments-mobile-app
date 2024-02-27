package com.example.nexusapp.screens


import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.destinations.CheckInPageDestination
import com.example.destinations.AllParticipantsPageDestination
import com.example.nexusapp.R
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.EventResponse
import com.example.nexusapp.screens.components.Header
import com.example.nexusapp.ui.theme.NexusAppTheme
import com.example.nexusapp.viewmodels.EventsVM
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

data class day(val num:Int,val letter:String)

@Destination
@Composable
fun Calendar(navigator: DestinationsNavigator) {
    val eventsViewModel = hiltViewModel<EventsVM>()

    var loading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var showAdd by remember {
        mutableStateOf(false)
    }
    val coroutine = rememberCoroutineScope()

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
                    //TODO("naviagte to main menu")
                }
            ){
                showAdd=true
            }

                    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

                        if(eventsViewModel.eventsList is Resource.Loading){

                            val infiniteTransition = rememberInfiniteTransition(label = "")
                            val rotationValue by infiniteTransition.animateFloat(
                                initialValue = 0f,
                                targetValue = 360f,
                                animationSpec = InfiniteRepeatableSpec(
                                    animation = tween(2000),
                                    repeatMode = RepeatMode.Restart
                                ), label = ""
                            )
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(50.dp)
                                    .rotate(rotationValue)

                                ,
                                color = colorResource(id = R.color.green)
                            )}
                        if(eventsViewModel.eventsList is Resource.Failed){

                                Text(
                                    text = "Error" + eventsViewModel.eventsList.message,
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            }
                        if(eventsViewModel.eventsList is Resource.Success){

                                var currentEvent by remember {
                                    mutableStateOf(eventsViewModel.eventsList.data!![0])
                                }
                                /*Text(text = "February",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 50.dp, bottom = 15.dp),
                                    textAlign = TextAlign.Center
                                )*/
                                TimeLine(eventsViewModel.eventsList.data!!){
                                  currentEvent=eventsViewModel.eventsList.data!![it]
                                }


                                Event(currentEvent,navigator)
                            }



        }
        }
    }

    if(showAdd){
        Dialog(onDismissRequest = { showAdd=false }) {

            var name by remember {
                mutableStateOf("")
            }
            var date by remember {
                mutableStateOf("")
            }
            var details by remember {
                mutableStateOf("")
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(colorResource(id = R.color.gray)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ){
                    Text(text = "Add new event",
                        fontSize = 20.sp,
                        color = Color.White,

                        )
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(24.dp)
                                .padding(4.dp),
                            color = Color.White
                        )}
                    Image(painter = painterResource(id = R.drawable.polygon), contentDescription = "")

                }

                Text(text = "Event name",
                    fontSize = 15.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 10.dp)

                )
                TextField(value = name , onValueChange ={name=it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    placeholder = {
                        Text(text = "Enter the event's name",
                            fontSize = 15.sp,
                            color = Color.Gray,

                            )}
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Event date",
                    fontSize = 15.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 10.dp)

                )
                TextField(value = date, onValueChange ={date=it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    placeholder = {
                        Text(text = "Select date",
                            fontSize = 15.sp,
                            color = Color.Gray,

                            )}
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Event details",
                    fontSize = 15.sp,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 10.dp)

                )
                TextField(value = details, onValueChange ={details=it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    placeholder = {
                        Text(text = "Event details",
                            fontSize = 15.sp,
                            color = Color.Gray,

                            )},
                    minLines = 4
                )
                Button(onClick = {
                    coroutine.launch {
                        eventsViewModel.addEvent(EventResponse(0,name, date, details)).collect{ result ->
                            if(result is Resource.Loading){
                                loading = true
                            }

                            if (result is Resource.Failed) {
                                Toast.makeText(
                                    context,
                                    result.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                loading = false
                                showAdd = false
                            }
                            if(result is Resource.Success){
                                Toast.makeText(context, "added succussfully :", Toast.LENGTH_SHORT)
                                    .show()
                                loading = false
                                showAdd = false
                            }
                        }

                    }


                },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .padding(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.green)
                    )
                ) {


                        Text(
                            text = "Add event",
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.gray),
                            modifier = Modifier.padding(5.dp)
                        )


                }
            }
        }
    }

}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Event(currentEvent: EventResponse,navigator: DestinationsNavigator) {
    var tab by remember {
        mutableStateOf(0)
    }
    val pager = rememberPagerState {
        2
    }

    LaunchedEffect(pager.currentPage,pager.isScrollInProgress){
        if(!pager.isScrollInProgress){
            tab=pager.currentPage
        }
    }
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Icon(
                painter = painterResource(id = R.drawable.point), contentDescription = "",
                tint = if (tab == 0) {
                    colorResource(id = R.color.green)
                } else colorResource(id = R.color.card_bg),
                modifier = Modifier
                    .size(20.dp)
                    .padding(5.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.point), contentDescription = "",
                tint = if (tab == 1) {
                    colorResource(id = R.color.green)
                } else colorResource(id = R.color.card_bg),
                modifier = Modifier
                    .size(20.dp)
                    .padding(5.dp)
            )
        }



        HorizontalPager(state = pager) {
            if (it == 0) {
                Box(
                    modifier = Modifier
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
                ) {
                    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "event",
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = currentEvent.name,
                            color = Color.White,
                            fontSize = 48.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = currentEvent.date,
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = {
                               navigator.navigate(AllParticipantsPageDestination(currentEvent.id))

                        },
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .padding(top = 20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.green)
                            )
                        ) {
                            Text(
                                text = "Check In",
                                fontSize = 20.sp,
                                color = colorResource(id = R.color.gray),
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                            )
                        }
                    }
                }
            } else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Details",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 20.sp,
                        color = Color.White,

                        )
                    Text(
                        text = currentEvent.details,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxSize(),
                        fontSize = 16.sp,
                        color = Color.White,

                        )
                }


            }
        }
    }
}



@Composable
fun TimeLine(days:List<EventResponse>,selected:(selectedDay:Int)->Unit) {
    var selectedDay by remember {
        mutableStateOf(0)
    }
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

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(
                        if (selectedDay == position) colorResource(id = R.color.green)
                        else colorResource(id = R.color.gray)
                    )
                    .clickable {
                        selectedDay = position
                        selected(selectedDay)
                    }
            ) {
                Text(text = day.date.split("/")[0],
                    fontSize = 16.sp,
                    color = if (position==selectedDay) colorResource(id = R.color.gray)
                    else Color.White,
                    modifier = Modifier.padding(top=15.dp, start = 15.dp, end = 15.dp)
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .size(10.dp))
                Text(text = "L",
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

    }
}

