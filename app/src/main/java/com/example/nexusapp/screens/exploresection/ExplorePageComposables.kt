package com.example.survisionapp.nexustest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.nexusapp.R
import com.example.nexusapp.models.MemberResponse
import com.example.nexusapp.viewmodels.MembersListVM


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
                        onAddClick()
                    },
                tint = Color.White,)


        }


    }

}

@Composable
fun MemberDataView(member:MemberResponse, membersViewModel: MembersListVM){
    //name context menu
    var isNameContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var namePressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    val nameInteractionSource = remember {
        MutableInteractionSource()
    }
    val nameDropDownItems= listOf("Delete")

    val teamInteractionSource = remember {
        MutableInteractionSource()
    }
    val teamDropDownItems= listOf("UI/UX","Motion","Video Editing")

    var isTeamContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var teamPressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var teamItemHeight by remember {
        mutableStateOf(0.dp)
    }
    val teamDensity = LocalDensity.current

    val pointsInteractionSource = remember {
        MutableInteractionSource()
    }

    var isPointsContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pointsPressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var pointsItemHeight by remember {
        mutableStateOf(0.dp)
    }
    val pointsDensity = LocalDensity.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .onSizeChanged {
                teamItemHeight = with(teamDensity) { it.height.toDp() }
                pointsItemHeight = with(pointsDensity) { it.height.toDp() }
            },
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        Text(
            modifier = Modifier
                .weight(2f)
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            isNameContextMenuVisible = true
                            namePressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            nameInteractionSource.emit(press)
                            tryAwaitRelease()
                            nameInteractionSource.emit(PressInteraction.Release(press))
                        }
                    )
                },
            text = member.name,
            fontSize = 15.sp,
            textAlign = TextAlign.Start,
            color = Color.White,)

        Text(
            modifier = Modifier
                .weight(1f)
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            isTeamContextMenuVisible = true
                            teamPressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            teamInteractionSource.emit(press)
                            tryAwaitRelease()
                            teamInteractionSource.emit(PressInteraction.Release(press))
                        }
                    )
                },
            text = member.team,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = Color.White,)

        Text(
            modifier = Modifier
                .weight(1f)
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            isPointsContextMenuVisible = true
                            pointsPressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            pointsInteractionSource.emit(press)
                            tryAwaitRelease()
                            pointsInteractionSource.emit(PressInteraction.Release(press))
                        }
                    )
                }
            ,
            text = member.points.toString(),
            fontSize = 15.sp,
            textAlign = TextAlign.End,
            color = Color.White,)

        DropdownMenu(
            expanded = isNameContextMenuVisible,
            onDismissRequest = {
                isNameContextMenuVisible = false
            },
            modifier = Modifier.background(
                colorResource(id = R.color.gray)
            )
            /*offset = namePressOffset.copy(
                y = namePressOffset.y - nameItemHeight
            )*/
        ) {
            nameDropDownItems.forEachIndexed{position,item->
                DropdownMenuItem(onClick = {
                    //TODO("delete member")
                    isNameContextMenuVisible = false
                }, text = { Text(text = item, color = colorResource(id = R.color.pink))})
            }
        }

        DropdownMenu(
            expanded = isTeamContextMenuVisible,
            onDismissRequest = {
                isTeamContextMenuVisible = false
            },
            modifier = Modifier.background(
                colorResource(id = R.color.gray)
            ),
            offset = teamPressOffset.copy(
                y = teamPressOffset.y - teamItemHeight
            )
        ) {
            Row (Modifier.padding(10.dp)){
                Text(text = "Edit team", color = colorResource(id = R.color.green), fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 40.dp))
                Image(painter = painterResource(id = R.drawable.polygon), contentDescription = "")
            }

            teamDropDownItems.forEachIndexed{position,item->
                DropdownMenuItem(onClick = {
                    //TODO("edit member's team")
                    isTeamContextMenuVisible = false
                }, text = { Text(text = item, color = Color.White)})
            }
        }

        DropdownMenu(
            expanded = isPointsContextMenuVisible,
            onDismissRequest = {
                isPointsContextMenuVisible = false
            },
            modifier = Modifier.background(
                colorResource(id = R.color.gray)
            ),
            offset = pointsPressOffset.copy(
                y = pointsPressOffset.y - pointsItemHeight
            )
        ) {
            var points by remember {
                mutableStateOf(member.points)
            }
            Row (Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Edit points", color = colorResource(id = R.color.green), fontWeight = FontWeight.Bold)
                Image(painter = painterResource(id = R.drawable.polygon), contentDescription = "")
            }
             TextField(
                 value = points.toString(),
                 onValueChange = {points=it.toInt()},
                 modifier = Modifier.width(150.dp).padding(10.dp),
                 colors = TextFieldDefaults.colors(
                     unfocusedContainerColor = Color.White,
                     focusedContainerColor = Color.White,
                     focusedIndicatorColor = colorResource(id = R.color.gray),
                     unfocusedIndicatorColor = Color.White,
                 ),
                 placeholder = {
                     Text(text = "Edit points", color = Color.Gray)
                 },
                 keyboardOptions = KeyboardOptions(
                     keyboardType = KeyboardType.Number
                 )
                 )
            Text(
                text = "Edit",
                fontSize = 15.sp,
                color = colorResource(id = R.color.green),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {
                            //TODO("edit member's points")
                            isPointsContextMenuVisible = false
                        }
                )


        }


    }
}

@Composable
fun MemberDataHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 30.dp),

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

