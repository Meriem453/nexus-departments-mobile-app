package com.example.nexusapp.screens

import android.util.Log
import android.widget.RadioGroup
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Start
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nexusapp.R
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.ProjectResponse
import com.example.nexusapp.models.TaskResponse
import com.example.nexusapp.screens.components.Header
import com.example.nexusapp.viewmodels.TasksVM
import com.example.nexusapp.viewmodels.TeamsVM
import com.google.android.material.progressindicator.LinearProgressIndicatorSpec
import com.ramcosta.composedestinations.annotation.Destination
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalDate

//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun TasksPage(project:ProjectResponse){
    val viewModel = hiltViewModel<TasksVM>()
    viewModel.project_id=project.id
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(true){
        coroutine.launch {
            viewModel.getAllTasks(project.id)
        }
    }

    var showAdd by remember{
        mutableStateOf(false)
    }
    var currentItem by remember {
        mutableStateOf<TaskResponse?>(null)
    }
    val scroll = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(
            title = project.title,
            icon = painterResource(id = R.drawable.add),
            onBackPressed = { /*TODO("back")*/ }) {
            showAdd = true
        }
        Spacer(modifier = Modifier.height(20.dp))
        if(viewModel.tasks is Resource.Loading){

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
        if(viewModel.tasks is Resource.Failed){

            Text(
                text = "Error" + viewModel.tasks.message,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
        if(viewModel.tasks is Resource.Success) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = scroll)
            ) {

                Text(
                    text = "ToDo",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
                LazyRow(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    itemsIndexed(viewModel.tasks.data?: emptyList()) { position, item ->
                        if (item.status == "ToDo") {
                            Card(
                                modifier = Modifier
                                    .padding(7.dp)
                                    .width(150.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.card_bg)),
                                onClick = {
                                    currentItem = item
                                    showAdd = true
                                }
                            ) {
                                Column(Modifier.padding(10.dp)) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.point),
                                            contentDescription = "",
                                            tint = Color(item.team_color.toLong(16)),
                                            modifier = Modifier
                                                .size(15.dp)
                                                .padding(2.dp)
                                        )
                                        Text(
                                            text = item.team_name,
                                            fontSize = 10.sp,
                                            color = Color.Gray
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = item.title,
                                        fontSize = 20.sp,
                                        color = Color.White,
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.point),
                                            contentDescription = "",
                                            tint = colorResource(id = R.color.green),
                                            modifier = Modifier
                                                .size(15.dp)
                                                .padding(2.dp)
                                        )
                                        Text(
                                            text = "Till ${item.deadline}",
                                            fontSize = 10.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))

                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "In progress",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                ) {

                    viewModel.tasks.data?: emptyList<TaskResponse>().forEachIndexed { position, item ->

                        if (item.status == "In Progress") {
                            val dismissState = rememberDismissState(DismissValue.Default)
                            if (dismissState.currentValue == DismissValue.DismissedToEnd) {
                                //currentItem=item
                                //TODO("done task")
                                item.status="Done"
                                viewModel.updateTask(item)
                            }

                            SwipeToDismiss(modifier = Modifier
                                .clickable {
                                    Log.d("edit", showAdd.toString())
                                    currentItem = item
                                    showAdd = true
                                },
                                directions = setOf(DismissDirection.StartToEnd),
                                state = dismissState,
                                background = {

                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(10.dp)
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(colorResource(id = R.color.green))
                                    ) {
                                        Text(
                                            text = "Done",
                                            color = Color.White,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    }
                                },
                                dismissContent = {

                                    Card(
                                        modifier = Modifier
                                            .padding(bottom = 7.dp, top = 7.dp)
                                            .fillMaxWidth(),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = colorResource(
                                                id = R.color.card_bg
                                            )
                                        )
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {


                                            Column(
                                                Modifier
                                                    .padding(15.dp)
                                                    .weight(1f)
                                            ) {
                                                Spacer(modifier = Modifier.height(5.dp))
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.point),
                                                        contentDescription = "",
                                                        tint = Color(item.team_color.toLong(16)),
                                                        modifier = Modifier
                                                            .size(15.dp)
                                                            .padding(2.dp)
                                                    )
                                                    Text(
                                                        text = item.team_name,
                                                        fontSize = 10.sp,
                                                        color = Color.Gray
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(5.dp))
                                                Text(
                                                    text = item.title,
                                                    fontSize = 20.sp,
                                                    color = Color.White,
                                                )
                                                Spacer(modifier = Modifier.height(5.dp))
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.point),
                                                        contentDescription = "",
                                                        tint = colorResource(id = R.color.green),
                                                        modifier = Modifier
                                                            .size(15.dp)
                                                            .padding(2.dp)
                                                    )
                                                    Text(
                                                        text = "Till ${item.deadline}",
                                                        fontSize = 10.sp,
                                                        color = Color.Gray
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(5.dp))

                                            }
                                            Box(
                                                modifier = Modifier,
                                                contentAlignment = Alignment.Center
                                            ) {
                                                CircularProgressIndicator(
                                                    progress = item.progress.toFloat() / 100,
                                                    color = colorResource(id = R.color.green),
                                                    modifier = Modifier
                                                        .size(100.dp)
                                                        .padding(15.dp),
                                                    strokeWidth = 5.dp,
                                                    trackColor = colorResource(
                                                        id = R.color.gray
                                                    )
                                                )
                                                Text(
                                                    text = "${item.progress}%",
                                                    fontSize = 20.sp,
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center
                                                )
                                            }
                                        }
                                    }
                                })

                        }
                    }

                }

            }
        }
    }
    if(showAdd){
        var title by remember {
            mutableStateOf(currentItem?.title ?: "")
        }
        var desc by remember {
            mutableStateOf(currentItem?.description ?: "")
        }
        var deadline by remember {
            mutableStateOf(currentItem?.deadline ?: "")
        }
        var team by remember {
            mutableStateOf(currentItem?.team_id ?: -1)
        }
        var team_name = try{viewModel.teams.data!!.filter { it.id==team }[0].name}catch(e:Exception){""}
        var progress by remember {
            mutableStateOf(currentItem?.progress?: 0)
        }
        var status by remember {
            mutableStateOf(currentItem?.status ?: "ToDo")
        }
        var expanded by remember {
            mutableStateOf(false)
        }
        val datePicker= rememberMaterialDialogState()
        Dialog(onDismissRequest = {
            currentItem=null
            showAdd=false
        }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(colorResource(id = R.color.gray))
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ){
                    Text(text = "${if(currentItem!=null) "Edit" else "Add new"} task",
                        fontSize = 20.sp,
                        color = Color.White,

                        )
                    Image(painter = painterResource(id = R.drawable.polygon), contentDescription = "")

                }

                Text(text = "Task title",
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
                        Text(text = "Enter the tasks's title",
                            fontSize = 15.sp,
                            color = Color.Gray,

                            )}
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Task deadline",
                    fontSize = 15.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 10.dp)

                )
                TextField(value = deadline , onValueChange ={deadline=it}, isError = deadline=="",
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    placeholder = {
                        Text(text = "Enter the tasks's deadline",
                            fontSize = 15.sp,
                            color = Color.Gray,

                            )},
                    trailingIcon = {
                        Icon(painter = painterResource(id = R.drawable.calendar), contentDescription = "", tint = Color.Gray, modifier = Modifier.clickable { datePicker.show() })
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Team",
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
                    TextField(value = team_name, onValueChange = { team = 0 }, isError = team==-1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                            .menuAnchor()
                            .clip(RoundedCornerShape(10.dp)),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        placeholder = {
                            Text(
                                text = "Select team",
                                fontSize = 15.sp,
                                color = Color.Gray,

                                )
                        }, trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        readOnly = true
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                        modifier = Modifier
                            .background(colorResource(id = R.color.gray)),
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
                                       team = selectionOption.id
                                       expanded = false
                                   }
                               )
                           }
                       }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Task description",
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
                    minLines = 4,
                    placeholder = {
                        Text(text = "Enter the tasks's description",
                            fontSize = 15.sp,
                            color = Color.Gray,

                            )}
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = status=="ToDo", onClick = { status="ToDo" }
                    , colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(id = R.color.green),
                        unselectedColor = colorResource(id = R.color.green)
                    )
                    )
                    Text(text = "ToDo", fontSize = 15.sp, modifier = Modifier.padding(start = 10.dp), color = Color.White)
                    RadioButton(selected = status=="In Progress", onClick = { status="In Progress" }
                        , colors = RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.green),
                            unselectedColor = colorResource(id = R.color.green)
                        )
                    )
                    Text(text = "In Progress", fontSize = 15.sp, modifier = Modifier.padding(end = 10.dp), color = Color.White)
                }
                Spacer(modifier = Modifier.height(20.dp))
                if(status=="In Progress") {
                    Slider(
                        value = progress.toFloat()/100,
                        onValueChange = { progress = it.toInt()*100 },
                        colors = SliderDefaults.colors(
                            thumbColor =  colorResource(R.color.green),
                            activeTrackColor = colorResource(R.color.green),
                            inactiveTrackColor = colorResource(R.color.card_bg),

                        ),
                        steps = 50,
                        valueRange = 0f..100f,
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp), horizontalArrangement = Arrangement.SpaceAround) {
                    if(currentItem!=null){
                        Button(onClick = {
                            /*TODO("delete task")*/
                            viewModel.deleteTask(currentItem!!.id)
                            currentItem=null
                            showAdd=false

                        },
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .padding(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.pink)
                            ),
                            enabled = title!="" && deadline!="" && desc!="" && team!=-1
                        ) {
                            Text(
                                text = "Delete",
                                fontSize = 15.sp,
                                color = colorResource(id = R.color.gray),
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }
                    Button(onClick = {
                        /*TODO("add task or edit task")*/
                        if(currentItem==null){
                            viewModel.addTasks(TaskResponse(
                                0,project.id,team,desc,status,title,progress,deadline,"",""
                            ))
                        }else{
                            viewModel.updateTask(
                                TaskResponse(currentItem!!.id,currentItem!!.project_id,team,desc,status,title,progress, deadline,"","")
                            )
                        }

                        currentItem=null
                        showAdd=false

                    },
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .padding(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.green)
                        ),
                        enabled = title!="" && deadline!="" && desc!="" && team!=-1
                    ) {
                        Text(
                            text = if(currentItem!=null) "Edit" else "Add",
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.gray),
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        MaterialDialog(
            dialogState = datePicker,
            backgroundColor = colorResource(id = R.color.gray)
        ) {
            var selectedDate by remember {
                mutableStateOf(LocalDate.now())
            }
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
                selectedDate=it
            }
            Text(
                text = "Save",
                fontSize = 16.sp,
                color = colorResource(id = R.color.green),
                modifier = Modifier
                    .clickable {
                        deadline =
                            "${selectedDate.dayOfMonth}/${selectedDate.monthValue}/${selectedDate.year}"
                        datePicker.hide()

                    }
                    //.align(Alignment.End)
                    .padding(16.dp))
        }
    }
}