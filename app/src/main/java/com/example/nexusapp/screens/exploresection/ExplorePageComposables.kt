package com.example.survisionapp.nexustest

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.nexusapp.R


@Preview
@Composable
fun ExplorePagePrev() {
AddMemberDialog( onDismiss = { /*TODO*/ }) {
}
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
val context = LocalContext.current
    Box(
        modifier = Modifier
            .background(Color(0xFF2A2A2A))
            .fillMaxSize()
        ,
        contentAlignment = Alignment.TopCenter

    ){
        Row (
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){

            Icon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription ="back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick() },
                tint = Color.White,
                )

            Text(
                text = "Members List",
//        fontFamily = FontFamily(Font(R.font.gotham_medium)),
                fontWeight = FontWeight(700),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
            )

            Icon(
                painter = painterResource(id = R.drawable.add),
                contentDescription ="back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
//                        Toast.makeText(context, "add clicked", Toast.LENGTH_SHORT).show()
                        onAddClick() },
                tint = Color.White,)


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
            fontSize = 17.sp,
            textAlign = TextAlign.Start,
            color = Color.White,)

        Text(
            modifier = Modifier.weight(1f),
            text = Team,
            fontSize = 17.sp,
            textAlign = TextAlign.Center,
            color = Color.White,)

        Text(
            modifier = Modifier.weight(1f),
            text = Points,
            fontSize = 17.sp,
            textAlign = TextAlign.End,
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
            fontSize = 17.sp,
            fontWeight = FontWeight(800),

            textAlign = TextAlign.Center,
            color = Color(0xFF76E494),
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "Team",
            fontSize = 17.sp,
            fontWeight = FontWeight(800),

            textAlign = TextAlign.Center,
            color = Color(0xFF76E494))
        Text(
            modifier = Modifier.weight(1f),
            text = "Points",
            fontWeight = FontWeight(800),

            fontSize = 17.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF76E494))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemberDialog(vararg textFieldText: () -> Unit, onDismiss:()->Unit, onAdd:()->Unit){
    Dialog(onDismissRequest = {onDismiss()}) {
        var textfieldstate by remember { mutableStateOf("") }


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
                        value = textfieldstate,
                        onValueChange = {textfieldstate = it },
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
                Button(onClick = { onAdd()  }) {
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

}

