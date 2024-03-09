package com.example.nexusapp

import ExplorePage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.NavGraphs
import com.example.nexusapp.screens.HomePage
import com.example.nexusapp.screens.SettingsPage
import com.example.nexusapp.ui.theme.NexusAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            rememberSystemUiController().setStatusBarColor(Color(0xFF2A2A2A))
            NexusAppTheme {
                Box (
                    Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.gray)))
                DestinationsNavHost(navGraph = NavGraphs.root)

            }
        }
    }
}
@Destination(start = true)
@Composable
fun Content(navigator: DestinationsNavigator){
    var active by remember {
        mutableStateOf(0)
    }
    Scaffold(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray))
        ,
        bottomBar = { BottomAppBar(containerColor = Color.Transparent){
           Row(
               Modifier
                   .fillMaxWidth()
                   .padding(start = 10.dp, end = 10.dp)
                   .clip(RoundedCornerShape(10.dp))
                   .background(colorResource(id = R.color.gray))
                   .border(
                       1.dp,
                       Brush.verticalGradient(
                           colors = listOf(
                               Color.White,
                               colorResource(id = R.color.gray),

                               )
                       ),
                       RoundedCornerShape(12.dp)
                   ),
               horizontalArrangement = Arrangement.SpaceEvenly,

               ){

           BarItem(
                painterResource(id = R.drawable.outlined_home),
                painterResource(id = R.drawable.filled_home),
                "Home",
                active==0
               , Modifier.weight(1f)
            ) { active = 0 }
            BarItem(
                painterResource(id = R.drawable.outlined_apps),
                painterResource(id = R.drawable.filled_apps),
                "Explore",
                active==1
                , Modifier.weight(1f)
            ) { active = 1 }
            BarItem(
                painterResource(id = R.drawable.outlined_settings),
                painterResource(id = R.drawable.filled_settings),
                "Settings",
                active==2
                , Modifier.weight(1f)
            ) { active = 2 }
        }}}
    ) {

        Box (
            Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.gray))){
        AnimatedContent(targetState = active, label = "",

        ) { page->
            when(page){
                0-> HomePage( it.calculateBottomPadding())
                1-> ExplorePage(navigator)
                2-> SettingsPage()
            }
        }}

    }
}
@Composable
fun BarItem(
    inactive: Painter, active: Painter, name: String,
    isActive:Boolean,
    modifier:Modifier,
    onCLick: ()->Unit
) {
Column (horizontalAlignment = Alignment.CenterHorizontally,
    modifier = if(isActive)
    {
        modifier
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0x4076E494),
                        colorResource(id = R.color.gray),
                    )
                )
            )
            .clickable { onCLick() }
    }else modifier
        .background(colorResource(id = R.color.gray))
        .clickable { onCLick() },

    ){
    Image(painter =
        if(isActive) active
        else inactive
        , contentDescription ="" ,
        Modifier.padding(top = 10.dp)
        )
    Text(text = name,
        fontSize = 12.sp,
        color = colorResource(id = R.color.green),
        modifier = Modifier.padding(bottom = 10.dp, top = 5.dp)
        )
}
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NexusAppTheme {
      //Content()
    }
}