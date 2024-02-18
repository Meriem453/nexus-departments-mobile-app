package com.example.survisionapp.nexustest

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nexusapp.models.MemberResponse
import com.example.nexusapp.viewmodels.MembersListVM
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.delay


@Preview
@Composable
fun MembersListPagePrev() {
    MembersListPage()
}




@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun MembersListPage() {

    val membersViewModel = viewModel(modelClass = MembersListVM::class.java)
    val state by membersViewModel.state.collectAsState()

    var loading by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        delay(3000L) // 3 seconds
        loading = false // Hide the progress bar after 3 seconds
    }


    val context = LocalContext.current

    var members by remember { mutableStateOf(state) }
    var newMemberName by remember { mutableStateOf("") }
    var newMemberTeam by remember { mutableStateOf("") }
    val openAddMemberDialog = remember { mutableStateOf(false) }


    when {
        openAddMemberDialog.value -> {
            Dialog(onDismissRequest = {openAddMemberDialog.value = false}) {
                Card (
                    colors = CardDefaults.cardColors(
                        contentColor = Color.Black,
                        containerColor = Color(0xFF373030)
                    ),
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(13.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .border(
                            2.dp,
                            Brush.verticalGradient(listOf(Color(0xFF373030), Color(0xFF76E494))),
                            RoundedCornerShape(15.dp)

                        )

                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(15.dp)) {
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Add A New Member",
                            fontSize = 15.sp,
//                    fontFamily = FontFamily(Font(R.font.gotham_medium)) ,
                            fontWeight = FontWeight(800),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(18.dp))

                        Card(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 3.dp
                            )
                        ){
                            OutlinedTextField(
                                value = newMemberName,
                                onValueChange = {newMemberName = it },
                                placeholder = {
                                    Text(text = "enter the member’s name")
                                },
                                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.White,
                                    unfocusedBorderColor = Color.White,
                                    cursorColor = Color.LightGray)
                                ,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White)
                                ,
                                singleLine = true
                            )

                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Button(onClick = {
                            // adding member logic in the online DB
                            // ++ gotta save it in shared preferences
                            members = members + MemberResponse(newMemberName, 0, newMemberTeam)
                            newMemberName = ""
                            newMemberTeam = ""
                            Toast.makeText(context, "Member added", Toast.LENGTH_SHORT).show()
                            openAddMemberDialog.value = false


                        }) {
                            Text(text = "Add",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))


                }

            }
            Toast.makeText(context, "dialog opened", Toast.LENGTH_SHORT).show()

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
        if (loading && state.isEmpty()) {
            // Show CircularProgressIndicator while loading
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center)
            )
        } else if (state.isEmpty()) {
            // Data is not received and loading is false, display a "No members" message
            Text(
                text = "No members",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center, color = Color.White
            )
        } else{
            LazyColumn(modifier = Modifier.padding(15.dp)) {
                items(state) {item: MemberResponse ->
                    MemberDataView(name = item.name, points = item.points , team = item.team)
                    Spacer(modifier = Modifier.height(5.dp))
                }




            }
        }

        if (!loading && state.isEmpty()) {
            Text(
                text = "check your internet connection.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center, color = Color.White
            )
        }

    }

}