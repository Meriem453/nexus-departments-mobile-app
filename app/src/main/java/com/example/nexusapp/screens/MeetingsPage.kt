package com.example.nexusapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nexusapp.R
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.MeetingResponse
import com.example.nexusapp.models.ProjectResponse
import com.example.nexusapp.screens.components.DeleteDialog
import com.example.nexusapp.screens.components.Header
import com.example.nexusapp.viewmodels.MeetingsVM
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Destination
@Composable
fun MeetingsPage() {
    val list= listOf(
        MeetingResponse(1,"New meeting","4/5/2024",2,"great one"),
        MeetingResponse(1,"New meeting","4/5/2024",2,"great one"),
        MeetingResponse(1,"New meeting","4/5/2024",2,"great one"),
        MeetingResponse(1,"New meeting","4/5/2024",2,"great one"),
        MeetingResponse(1,"New meeting","4/5/2024",2,"great one"),

    )
Column(modifier = Modifier
    .fillMaxSize()
    .background(colorResource(id = R.color.gray))
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
    Header(
        title = "Meetings",
        icon = painterResource(id = R.drawable.add),
        {
            //TODO("back")
             }
    ) {
        showAdd=true

    }

    LazyColumn(
        modifier= Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        itemsIndexed(list){position,item->
            var dismissState by remember {
                mutableStateOf(DismissState(DismissValue.Default))
            }
            if(dismissState.currentValue== DismissValue.DismissedToEnd){
                currentItem=item
                showAdd=true
            }
            if(dismissState.currentValue== DismissValue.DismissedToStart){
                currentItem=item
                showDelete=true
            }
            if (dismissState.currentValue!= DismissValue.Default)
                dismissState= DismissState(DismissValue.Default)


            SwipeToDismiss(state = dismissState,
                background = {

                    Row(horizontalArrangement = Arrangement.SpaceBetween,
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
                        Text(text = "Edit",
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
                        Text(text = "Delete",
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
                        Row (
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween){
                            Text(text = item.title,
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                //modifier =Modifier.padding(start = 16.dp)
                            )
                            //Image(painter = painterResource(id = R.drawable.power), contentDescription = "")
                            //Image(painter = painterResource(id = R.drawable.power), contentDescription = "")
                        }

                        Text(text = item.date,
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start=16.dp, bottom = 16.dp)
                        )
                    }
                })
        }
    }

    if(showAdd){
        Dialog(onDismissRequest = {
            showAdd=false
            currentItem=null
        }) {

            var title by remember {
                mutableStateOf(
                    currentItem?.title ?: ""
                )
            }
            var date by remember {
                mutableStateOf(currentItem?.date ?: "")
            }
            var desc by remember {
                mutableStateOf(currentItem?.description ?: "")
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
                TextField(value = title , onValueChange ={title=it},
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
                Text(text = "Meeting date",
                    fontSize = 15.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 10.dp)

                )
                TextField(value = date , onValueChange ={date=it},
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
                Text(text = "Meeting description",
                    fontSize = 15.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 10.dp)

                )
                TextField(value = desc , onValueChange ={desc=it},
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
                        val result = viewModel.addMeeting(
                            MeetingResponse(0, title, date, 1, desc)
                        )
                       Toast.makeText(c,result.message,Toast.LENGTH_LONG).show()
                    }else{
                        /*TODO("add or edit meeting")*/
                    }
                    showAdd=false
                    currentItem=null

                },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .padding(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.green)
                    )
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
    }
    if(showDelete){
        DeleteDialog(
            "Delete Meeting",
            "meeting",
            {showDelete=false},
            {
                Toast.makeText(c,"Meeting deleted", Toast.LENGTH_SHORT).show()
                //TODO("delete meeting")
                showDelete=false
            }
        )
    }
}}