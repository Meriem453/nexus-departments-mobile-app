package com.example.nexusapp.screens

import android.widget.Toast
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nexusapp.R
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.MeetingResponse
import com.example.nexusapp.models.ProjectResponse
import com.example.nexusapp.screens.components.DeleteDialog
import com.example.nexusapp.screens.components.Header
import com.example.nexusapp.viewmodels.MeetingsVM
import com.ramcosta.composedestinations.annotation.Destination
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogButtons
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Destination
@Composable
fun MeetingsPage() {

Column(modifier = Modifier
    .fillMaxSize()
    .background(colorResource(id = R.color.gray)),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    val c = LocalContext.current
    val viewModel= hiltViewModel<MeetingsVM>()
    var showAdd by remember {
        mutableStateOf(false)
    }
    var showDelete by remember {
        mutableStateOf(false)
    }
    var currentItem by remember {
        mutableStateOf<MeetingResponse?>(null)
    }
    var isSheetOpen by remember {
        mutableStateOf(false)
    }
    Header(
        title = "Meetings",
        icon = painterResource(id = R.drawable.add),
        {
            //TODO("back")
             }
    ) {
        showAdd=true

    }
    if(viewModel.meetings is Resource.Loading){

        val infiniteTransition = rememberInfiniteTransition(label = "")
        val rotationValue by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = InfiniteRepeatableSpec(
                animation = tween(2000),
                repeatMode = RepeatMode.Restart
            ), label = ""
        )
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {

            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .rotate(rotationValue),
                color = colorResource(id = R.color.green)
            )
        }
    }
    if(viewModel.meetings  is Resource.Failed){
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = viewModel.meetings.message!!,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
    if(viewModel.meetings  is Resource.Success) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            itemsIndexed(viewModel.meetings.data?: emptyList()) { position, item ->
                var dismissState by remember {
                    mutableStateOf(DismissState(DismissValue.Default))
                }
                if (dismissState.currentValue == DismissValue.DismissedToEnd) {
                    currentItem = item
                    showAdd = true
                }
                if (dismissState.currentValue == DismissValue.DismissedToStart) {
                    currentItem = item
                    showDelete = true
                }
                if (dismissState.currentValue != DismissValue.Default)
                    dismissState = DismissState(DismissValue.Default)


                SwipeToDismiss(state = dismissState,
                    background = {

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    if (dismissState.dismissDirection == DismissDirection.StartToEnd)
                                        colorResource(id = R.color.green)
                                    else colorResource(id = R.color.pink)
                                )
                        ) {
                            Text(
                                text = "Edit",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .alpha(
                                        if (dismissState.dismissDirection == DismissDirection.StartToEnd) 1f
                                        else 0f
                                    )
                            )
                            Text(
                                text = "Delete",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .alpha(
                                        if (dismissState.dismissDirection == DismissDirection.StartToEnd) 0f
                                        else 1f
                                    )
                            )
                        }
                    }, dismissContent = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(colorResource(R.color.card_bg))
                                .clickable {
                                    currentItem = item
                                    isSheetOpen = true
                                }
                                .border(
                                    width = 2.dp,
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            Color(item.team_color.toLong(16)),
                                            colorResource(id = R.color.gray),
                                        ), startX = .5f
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                ),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = item.title,
                                    fontSize = 16.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    //modifier =Modifier.padding(start = 16.dp)
                                )
                                //Image(painter = painterResource(id = R.drawable.power), contentDescription = "")
                                //Image(painter = painterResource(id = R.drawable.power), contentDescription = "")
                            }

                            Text(
                                text = item.date,
                                fontSize = 15.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                            )
                        }
                    })
            }
        }
    }

    if(showAdd){

        var title by remember {
            mutableStateOf(
                currentItem?.title ?: ""
            )
        }
        var date by remember {
            mutableStateOf(currentItem?.date ?: "")
        }
        var time by remember {
            mutableStateOf(currentItem?.time ?: "")
        }
        var desc by remember {
            mutableStateOf(currentItem?.description ?: "")
        }
        var team by remember {
            mutableStateOf(currentItem?.team_id ?: -1)
        }
        var team_name = try{viewModel.teams.data!!.filter { it.id==team }[0].name}catch(e:Exception){""}
        var expanded by remember {
            mutableStateOf(false)
        }
        val datePicker= rememberMaterialDialogState()
        val timePicker= rememberMaterialDialogState()
        Dialog(onDismissRequest = {
            showAdd=false
            currentItem=null
        }) {

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
                    Text(text = "${if(currentItem!=null) "Edit" else "Add new"} meeting",
                        fontSize = 20.sp,
                        color = Color.White,

                        )
                    Image(painter = painterResource(id = R.drawable.polygon), contentDescription = "")

                }

                Text(text = "Meeting title",
                    fontSize = 15.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 10.dp)

                )
                TextField(value = title , onValueChange ={if (it.length <= 20)title=it}, isError = title=="", maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    placeholder = {
                        Text(text = "Enter the meeting's title",
                            fontSize = 15.sp,
                            color = Color.Gray,

                            )}
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row (Modifier.fillMaxWidth()){
                    TextField(value = date , onValueChange ={date=it}, isError = date=="",
                        readOnly = true,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 20.dp, end = 5.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        placeholder = {
                            Text(text = "Date",
                                fontSize = 15.sp,
                                color = Color.Gray,
                                modifier = Modifier
                                    .clickable { datePicker.show() }
                                    .fillMaxWidth()

                                )},
                        trailingIcon = {
                            Icon(painter = painterResource(id = R.drawable.calendar), contentDescription ="", tint = Color.Gray , modifier = Modifier.clickable { datePicker.show() })
                        },
                        maxLines = 1
                    )
                    TextField(value = time , onValueChange ={time=it}, isError = time=="",
                        readOnly = true,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp, end = 20.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        placeholder = {
                            Text(text = "Time",
                                fontSize = 15.sp,
                                color = Color.Gray,
                                modifier = Modifier
                                    .clickable { timePicker.show() }
                                    .fillMaxWidth()

                                )},
                        trailingIcon = {
                            Icon(painter = painterResource(id = R.drawable.access_time), contentDescription ="", tint = Color.Gray , modifier = Modifier.clickable { timePicker.show() })
                        },
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Meeting team",
                    fontSize = 15.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 10.dp)

                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {


                    TextField(value = team_name?:"", onValueChange = {  }, isError = team==-1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .padding(start = 20.dp, end = 20.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        placeholder = {
                            Text(
                                text = "Enter meeting's team",
                                fontSize = 15.sp,
                                color = Color.Gray,

                                )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        }, readOnly = true

                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                        modifier = Modifier.background(colorResource(id = R.color.gray))
                    ) {
                        if(viewModel.teams is Resource.Success) {
                            viewModel.teams.data!!.forEachIndexed() { position, selectionOption ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = selectionOption.name,
                                            color = Color.White,
                                            fontSize = 16.sp
                                        )
                                    },
                                    onClick = {
                                        team =  selectionOption.id
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Meeting description",
                    fontSize = 15.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 10.dp)

                )
                TextField(value = desc , onValueChange ={if (it.length <= 100)desc=it}, isError = desc=="",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    placeholder = {
                        Text(text = "Enter meeting's description",
                            fontSize = 15.sp,
                            color = Color.Gray,

                            )},
                    minLines = 4
                )
                Button(onClick = {

                    if(currentItem==null){
                        /*TODO(" add meeting")*/
                        viewModel.addMeeting(
                            MeetingResponse(0, title, date, time,team, description = desc)
                        )

                    }else{
                        /*TODO(" edit meeting")*/
                        viewModel.updateMeeting(
                            MeetingResponse(
                                currentItem!!.id,title,date,time,team,currentItem!!.team_color,desc
                            ),
                            team
                        )
                    }
                    showAdd=false
                    currentItem=null

                },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .padding(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.green)
                    ),
                    enabled = title!="" && time!="" && date!="" && team!=-1 && desc!=""
                ) {
                    Text(
                        text = "${if(currentItem!=null) "Edit" else "Add"} meeting",
                        fontSize = 15.sp,
                        color = colorResource(id = R.color.gray),
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
        MaterialDialog(
            dialogState = datePicker,
            backgroundColor = colorResource(id = R.color.gray),

        ) {
            var selectedDate by remember { mutableStateOf(LocalDate.now()) }
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick a date",
                colors = DatePickerDefaults.colors(
                    dateActiveBackgroundColor = colorResource(id = R.color.green),
                    dateActiveTextColor = colorResource(id = R.color.gray),
                    headerBackgroundColor = colorResource(id = R.color.gray),
                    headerTextColor = colorResource(id = R.color.green),
                    dateInactiveTextColor = Color.White,
                    dateInactiveBackgroundColor = colorResource(id = R.color.gray),
                    calendarHeaderTextColor =colorResource(id = R.color.green)

                )
            ){
                selectedDate = it
            }
            Text(
                text = "Save",
                fontSize = 16.sp,
                color = colorResource(id = R.color.green),
                modifier = Modifier
                    .clickable {
                        date =
                            "${selectedDate.dayOfMonth}/${selectedDate.monthValue}/${selectedDate.year}"
                        datePicker.hide()

                    }
                    .align(Alignment.End)
                    .padding(16.dp))
        }
        MaterialDialog(
            dialogState = timePicker,
            backgroundColor = colorResource(id = R.color.gray)
        ) {
            var selectedTime by remember {
                mutableStateOf(LocalTime.now())
            }
            timepicker(
                initialTime = LocalTime.now(),
                title = "Pick a time",
                colors = TimePickerDefaults.colors(
                    activeTextColor = colorResource(id = R.color.gray),
                    activeBackgroundColor = colorResource(id = R.color.green),
                    inactiveTextColor = Color.White,
                    headerTextColor = colorResource(id = R.color.green),
                    selectorColor = colorResource(id = R.color.green),
                    selectorTextColor = colorResource(id = R.color.green)
                )
            ){
                selectedTime=it
            }
            Text(
                text = "Save",
                fontSize = 16.sp,
                color = colorResource(id = R.color.green),
                modifier = Modifier
                    .clickable {
                        time =
                            "${selectedTime.hour}:${selectedTime.minute}"
                        datePicker.hide()

                    }
                    .align(Alignment.End)
                    .padding(16.dp))

        }
    }
    if(showDelete){
        DeleteDialog(
            "Delete Meeting",
            "meeting",
            {showDelete=false},
            {

                //TODO("delete meeting")
                viewModel.deleteMeeting(currentItem!!.id)
                showDelete=false
            }
        )
    }
    if(isSheetOpen){
        ModalBottomSheet(
            onDismissRequest = {
                currentItem=null
                isSheetOpen=false
                               },
            containerColor = colorResource(id = R.color.gray),
             ) {
            Text(
                text = currentItem!!.title,
                fontSize = 20.sp,
                color= Color.White,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = currentItem!!.description,
                fontSize = 15.sp,
                color= Color.White,
                modifier = Modifier.padding(16.dp),
                minLines = 7
            )
        }
    }
}}