package com.example.survisionapp.nexustest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


@Preview
@Composable
fun ExplorePagePrev() {
    MemberDataView(name = "soufyan", Team ="ui ux" , Points = "58")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreItemCard(title:String,onClick:()->Unit){
    Card(
        onClick = onClick
        ,
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color(0xFF373030)
        ),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(13.dp),
        modifier = Modifier
            .border(
                2.dp,
                Brush.verticalGradient(listOf(Color(0xFF76E494), Color(0xFF2A2A2A))),
                RoundedCornerShape(15.dp)

            )
            .fillMaxWidth()

    ) {


        Box(
            modifier = Modifier
                .padding(15.dp)
            ,
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = title,
//                fontFamily = FontFamily(Font(R.font.gotham_medium)),
//                fontWeight = FontWeight(700),
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier= Modifier.fillMaxWidth()
            )


        }
    }
}
@Composable
fun ExploreHeader (){

    Box(
        modifier = Modifier
            .background(Color(0xFF2A2A2A))
            .fillMaxSize()
            ,
        contentAlignment = Alignment.TopCenter

    ){
        Text(
            text = "Explore",
//        fontFamily = FontFamily(Font(R.font.gotham_medium)),
            fontWeight = FontWeight(700),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier= Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .background(Color(0xFF2A2A2A))
        )
    }

}

@Composable
fun MembersListHeader (onBackClick:()->Unit={},onAddClick:()->Unit={}){

    Box(
        modifier = Modifier
            .background(Color(0xFF2A2A2A))
            .fillMaxSize()
        ,
        contentAlignment = Alignment.TopCenter

    ){
        Row (
            modifier = Modifier .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween){

            /*todo add back button*/

            Text(
                text = "Members List",
//        fontFamily = FontFamily(Font(R.font.gotham_medium)),
                fontWeight = FontWeight(700),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
            )
            /*todo add add button*/

        }


    }

}

@Composable
fun MemberDataView(name:String,Team:String,Points:String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        Text(
            modifier = Modifier.weight(2f),
            text = name,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color.White,)

        Text(
            modifier = Modifier.weight(1f),
            text = Team,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color.White,)

        Text(
            modifier = Modifier.weight(1f),
            text = Points,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color.White,)

    }
}

@Composable
fun MemberDataHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),

    ) {
        Text(
            modifier = Modifier.weight(2f),
            text = "Member Name",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF76E494),
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "Team",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF76E494))
        Text(
            modifier = Modifier.weight(1f),
            text = "Points",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF76E494))
    }
}

@Composable
fun AddMemberDialog(name :String,team:String,onDismiss:()->Unit,onAdd:()->Unit){
    Dialog(onDismissRequest = {onDismiss()}) {

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
                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "add a new member",
                    fontSize = 15.sp,
//                    fontFamily = FontFamily(Font(R.font.gotham_medium)) ,
//                    fontWeight = FontWeight(800),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))

//                Button (onClick = {onAdd()})
                }
                Spacer(modifier = Modifier.height(15.dp))


        }
        
    }
}