package com.example.nexusapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.destinations.CheckInPageDestination
import com.example.nexusapp.R
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.models.ParticipantResponse
import com.example.nexusapp.screens.components.Header
import com.example.nexusapp.viewmodels.ParticipantsVM
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@Destination
@Composable
fun AllParticipantsPage(event_id:Int,navigator: DestinationsNavigator){
    val viewModel = hiltViewModel<ParticipantsVM>()
    val coroutine = rememberCoroutineScope()
    LaunchedEffect(true){
        coroutine.launch {
            viewModel.getAllPariticipants(event_id)
        }
    }


    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(title = "All Participants", icon = painterResource(id = R.drawable.qr_code_scanner), onBackPressed = { /*TODO*/ }) {
        navigator.navigate(CheckInPageDestination)
}
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                //.padding(start = 16.dp, end = 16.dp),
            ,horizontalArrangement = Arrangement.SpaceEvenly

            ) {
            Text(
                text = "Name",
                fontSize = 17.sp,
                fontWeight = FontWeight(800),
                color = Color(0xFF76E494),
            )
            Text(
                text = "Team",
                fontSize = 17.sp,
                fontWeight = FontWeight(800),
                color = Color(0xFF76E494))
            Text(
                text = "Present",
                fontWeight = FontWeight(800),
                fontSize = 17.sp,
                color = Color(0xFF76E494))
        }
        Spacer(modifier = Modifier.height(15.dp))
        if(viewModel.participants is Resource.Loading) {

            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .wrapContentSize(align = Alignment.Center),
                trackColor = colorResource(id = R.color.green),
                color = colorResource(id = R.color.gray)
            )
        }
        if (viewModel.participants is Resource.Failed) {
            Text(
                text = viewModel.participants.message!!,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = Color.Gray,
                textAlign = TextAlign.Center,
            )
        }
        if(viewModel.participants is Resource.Success){
            LazyColumn(modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()) {
                viewModel.participants.data?.let {
                    itemsIndexed(viewModel.participants.data!!) { position, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 20.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly

                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = item.name,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                            )

                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = item.team,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                            )

                            Text(
                                modifier = Modifier.weight(1f),
                                text = item.came,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
        }


    }
}