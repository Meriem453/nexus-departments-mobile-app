package com.example.survisionapp.nexustest

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nexusapp.R
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.MemberResponse
import com.example.nexusapp.viewmodels.MembersListVM
import com.ramcosta.composedestinations.annotation.Destination


@Preview
@Composable
fun MembersListPagePrev() {
    MembersListPage()
}





@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun MembersListPage() {
    val membersViewModel = hiltViewModel<MembersListVM>()

    val context = LocalContext.current

    val openAddMemberDialog = remember { mutableStateOf(false) }


    when {
        openAddMemberDialog.value -> {
            Dialog(onDismissRequest = { openAddMemberDialog.value =false }) {

                var name by remember {
                    mutableStateOf("")
                }
                var team by remember {
                    mutableStateOf(0)
                }
                var email by remember {
                    mutableStateOf("")
                }
                var password by remember {
                    mutableStateOf("")
                }
                var expanded by remember {
                    mutableStateOf(false)
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
                        Text(text = "Add new member",
                            fontSize = 20.sp,
                            color = Color.White,

                            )
                        Image(painter = painterResource(id = R.drawable.polygon), contentDescription = "")

                    }

                    Text(text = "Member name",
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
                            Text(text = "Enter the member's name",
                                fontSize = 15.sp,
                                color = Color.Gray,

                                )}
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(text = "Team",
                        fontSize = 15.sp,
                        color = Color.White,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, bottom = 10.dp)

                    )
                    val options = listOf("UI/UX", "Motion", "Graphic")
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        TextField(value = team.toString(), onValueChange = {  },
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
                            },
                            trailingIcon = {
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
                            modifier = Modifier.background(colorResource(id = R.color.gray))
                        ) {

                            options.forEachIndexed() { position, selectionOption ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = selectionOption,
                                            color = Color.White,
                                            fontSize = 16.sp
                                        )
                                    },
                                    onClick = {
                                        team = position
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Email",
                        fontSize = 15.sp,
                        color = Color.White,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, bottom = 10.dp)

                    )
                    TextField(value = email, onValueChange ={email=it},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        placeholder = {
                            Text(text = "Enter email",
                                fontSize = 15.sp,
                                color = Color.Gray,

                                )},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Password",
                        fontSize = 15.sp,
                        color = Color.White,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, bottom = 10.dp)

                    )
                    TextField(value = password, onValueChange ={password=it},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        placeholder = {
                            Text(text = "Enter password",
                                fontSize = 15.sp,
                                color = Color.Gray,

                                )},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {
                        //members = members + MemberResponse(name, 0, team)
                        //TODO("add member")
                        name = ""
                        team= 0
                        email=""
                        password=""
                        Toast.makeText(context, "Member added", Toast.LENGTH_SHORT).show()
                        openAddMemberDialog.value = false


                    },
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .padding(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.green)
                        )
                    ) {
                        Text(
                            text = "Add member",
                            fontSize = 15.sp,
                            color = colorResource(id = R.color.gray),
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
        }
    }

    Scaffold {
        it
    }

    MembersListHeader({openAddMemberDialog.value = false}, {openAddMemberDialog.value = true})
    Column {

        Spacer(modifier = Modifier.height(90.dp))
        MemberDataHeader()
        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.height(5.dp))
        if(membersViewModel.membersList is Resource.Loading) {
            // Show CircularProgressIndicator while loading
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center),

            )
        }
        if (membersViewModel.membersList is Resource.Failed) {
            // Data is not received and loading is false, display a "No members" message
            /*Text(
                text = "No members",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center, color = Color.White
            )*/
            Text(
                text = "Check your internet connection.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center, color = Color.White
            )
        }
        if(membersViewModel.membersList is Resource.Success){
            LazyColumn(modifier = Modifier.padding(15.dp)) {
                items(membersViewModel.membersList.data!!) {item: MemberResponse ->
                    MemberDataView(item,membersViewModel)
                    Spacer(modifier = Modifier.height(5.dp))
                }




            }
        }


    }

}