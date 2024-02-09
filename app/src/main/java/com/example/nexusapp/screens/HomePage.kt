package com.example.nexusapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nexusapp.R
import kotlinx.coroutines.launch

@Composable
fun HomePage (){
    Scaffold {
        it
        Column(Modifier.fillMaxSize()) {


        var scrollPosition by remember { mutableStateOf(0) }
        var scrollPercentage by remember { mutableStateOf(0f) }
        val scrollState = rememberScrollState()
            ProfileHeader(progress = scrollPercentage)
        Column(
            Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.gray))
                .verticalScroll(scrollState)

        ) {
            scrollPosition = scrollState.value
            scrollPercentage =if((scrollPosition.toFloat() / scrollState.maxValue).coerceIn(0f, 1f)<=0.5 )(scrollPosition.toFloat() / scrollState.maxValue).coerceIn(0f, 1f)*2 else 1F
            //Log.d("scroll",scrollPercentage.toString())

            Overview()
        }
    }}
}

@Composable
fun Overview() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.gray))) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(colorResource(id = R.color.card_bg))) {
                   Text(text = "Figma Workshop",
                       color = Color.White,
                       fontSize = 16.sp,
                       //fontFamily = FontFamily.Cursive,
                       fontWeight = FontWeight.Bold,
                       modifier = Modifier.padding(10.dp)
                       )
            Text(text = "Tomorrow",
                color = Color.White,
                fontSize = 15.sp,
                //fontFamily = FontFamily.Cursive,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
        Text(text = "Department Overview",
            color = Color.White,
            fontSize = 20.sp,
            //fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            NexusCard("30","Member", painterResource(id = R.drawable.members), Modifier.weight(1f))
            NexusCard("+9","Done projects", painterResource(id = R.drawable.power), Modifier.weight(1f))

        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            NexusCard("30","Member", painterResource(id = R.drawable.members), Modifier.weight(1f))
            NexusCard("+9","Done projects", painterResource(id = R.drawable.power), Modifier.weight(1f))

        }
        Text(text = "Projects",
            color = Color.White,
            fontSize = 20.sp,
            //fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Image(painter = painterResource(id = R.drawable.power), contentDescription = "", modifier = Modifier
            .fillMaxWidth()
            .height(500.dp))

    }
}

@Composable
fun NexusCard(bold: String, thin: String, painterResource: Painter,modifier: Modifier) {

     Column(modifier = modifier
         .padding(16.dp)
         .clip(RoundedCornerShape(20.dp))
         .background(colorResource(id = R.color.card_bg))


     ) {
      Row (
          Modifier
              .fillMaxWidth()
              .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween){
          Text(text = bold,
              color = Color.White,
              fontSize = 26.sp,
              //fontFamily = FontFamily.Cursive,
              fontWeight = FontWeight.Bold,
          )
          Image(painter = painterResource, contentDescription = "")
      }
         Text(text = thin,
             color = Color.White,
             fontSize = 15.sp,
             //fontFamily = FontFamily.Cursive,
             modifier = Modifier.padding(10.dp)

         )
     }}

@OptIn(ExperimentalMotionApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ProfileHeader(progress: Float) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }
    
    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp)
            .background(colorResource(id = R.color.gray))

    ) {
        /*GlideImage(model = "https://firebasestorage.googleapis.com/v0/b/residence-47d76.appspot.com/o/images%2F7s28g4ICpPWBmrjKBgwLVfzCSTz2.jpg?alt=media&", contentDescription ="" ,
            modifier = Modifier
                .clip(CircleShape)
                .layoutId("profile_pic"))*/
        Image(
            painter = painterResource(id = R.drawable.members),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .layoutId("profile_pic")
        )
        Text(
            text = "Meriem",
            fontSize = 23.sp,
            modifier = Modifier.layoutId("username"),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.green)
        )
        Text(
            text = "Development Department",
            fontSize = 20.sp,
            modifier = Modifier.layoutId("department"),
            color = colorResource(id = R.color.green)
        )
        Text(
            text = "Member",
            fontSize = 20.sp,
            modifier = Modifier.layoutId("manager"),
            color = Color.White
        )
    }
}
