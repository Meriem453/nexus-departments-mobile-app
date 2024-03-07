package com.example.nexusapp.screens

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nexusapp.R
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.TeamResponse
import com.example.nexusapp.screens.components.Header
import com.example.nexusapp.viewmodels.TeamsVM
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.ramcosta.composedestinations.annotation.Destination
@Preview
@Destination
@Composable
fun TeamsPage() {
    val teamVM = hiltViewModel<TeamsVM>()
    var showAdd by remember {
        mutableStateOf(false)
    }
    var currentItem by remember {
        mutableStateOf<TeamResponse?>(null)
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray)),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Header(title = "Teams",
            icon = painterResource(id = R.drawable.add),
            onBackPressed = { /*TODO("back")*/ }) {
            showAdd=true
        }
        Row (Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically){
            Image(painter = painterResource(id = R.drawable.liiiines), contentDescription = "")
            if(teamVM.teams is Resource.Loading) {
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
            if(teamVM.teams is Resource.Failed) {
                Text(
                    text = teamVM.teams.message!!,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
            if(teamVM.teams is Resource.Success){
                LazyColumn(modifier = Modifier.fillMaxSize()){
                    itemsIndexed(teamVM.teams.data!!){position,item->
                        Box (
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(item.color.toLong(16)))
                                .clickable {
                                    currentItem = item
                                    showAdd = true
                                }
                            ,
                            contentAlignment = Alignment.Center
                        ){
                          Text(
                              text = item.name,
                              color = Color.White,
                              fontSize = 16.sp,
                              modifier = Modifier.padding(15.dp)
                              )
                        }
                    }
                }
            }

        }
    }
    if(showAdd){
        var colorPicker by remember {
            mutableStateOf(false)
        }
        Dialog(onDismissRequest = {
            currentItem=null
            showAdd=false

        }) {

            var name by remember {
                mutableStateOf(
                    currentItem?.name ?: ""
                )
            }
            var color by remember {
                mutableStateOf(currentItem?.color?: "ff76E494")
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
                    Text(text = "${if(currentItem!=null) "Edit" else "Add new"} team",
                        fontSize = 20.sp,
                        color = Color.White,

                        )
                    Image(painter = painterResource(id = R.drawable.polygon), contentDescription = "")

                }

                Text(text = "Team name",
                    fontSize = 15.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 10.dp)

                )
                TextField(value = name , onValueChange ={if (it.length <= 20)name=it}, isError = name=="", maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    placeholder = {
                        Text(text = "Enter the team's name",
                            fontSize = 15.sp,
                            color = Color.Gray,

                            )}
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Team color",
                    fontSize = 15.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, bottom = 10.dp)

                )
                TextField(value = color , onValueChange ={color=it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    placeholder = {
                        Text(text = "Enter the team's color",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            modifier = Modifier.clickable { colorPicker=true }

                            )},
                    trailingIcon = {
                        Icon(painter = painterResource(id = R.drawable.color_lens), contentDescription = "", tint = Color.Gray,
                            modifier = Modifier.clickable { colorPicker=true }
                        )
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp), horizontalArrangement = Arrangement.SpaceAround) {
                    if(currentItem!=null){
                        Button(onClick = {
                            teamVM.deleteTeam(currentItem!!.id)
                            /*TODO("delete team")*/
                            currentItem=null
                            showAdd=false

                        },
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .padding(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.pink)
                            )
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
                        /*TODO("add team or edit team")*/
                        if(currentItem==null){
                            teamVM.addTeam(TeamResponse(
                                0,name,color
                            ))
                        }else{
                            currentItem!!.name=name
                            currentItem!!.color=color
                            teamVM.updateTeam(TeamResponse(
                                currentItem!!.id,name,color
                            ))
                        }
                        currentItem=null
                        showAdd=false

                    },
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .padding(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.green)
                        ), enabled = name!=""
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
            if (colorPicker){
                val controller = rememberColorPickerController()
                var picked by remember {
                    mutableStateOf(color)
                }
                Dialog(onDismissRequest = { colorPicker=false }) {
                    Column(Modifier.background(colorResource(id = R.color.gray))) {
                        Text(text = "Pick a color",
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.green),
                            modifier = Modifier
                                .padding(20.dp)
                        )
                        HsvColorPicker(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                                .height(300.dp),
                            controller = controller,
                            onColorChanged = {
                                picked=it.hexCode
                            }
                        )
                        Text(text = "Save",
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.green),
                            modifier = Modifier
                                .clickable {
                                    color = picked
                                    colorPicker = false
                                }
                                .align(Alignment.End)
                                .padding(20.dp)
                        )
                    }

                }
            }
        }

    }

}