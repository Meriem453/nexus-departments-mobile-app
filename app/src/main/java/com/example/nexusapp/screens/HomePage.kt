package com.example.nexusapp.screens

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nexusapp.R
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.viewmodels.HomePageVM



@Composable
fun HomePage(bottomPadding: Dp) {
    val viewModel= hiltViewModel<HomePageVM>()
    Scaffold(Modifier.background(colorResource(id = R.color.gray))) {
        it
        Column(
            Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.gray)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {
           if(viewModel.userInfo is Resource.Loading) {
               val infiniteTransition = rememberInfiniteTransition(label = "")
               val rotationValue by infiniteTransition.animateFloat(
                   initialValue = 0f,
                   targetValue = 360f,
                   animationSpec = InfiniteRepeatableSpec(
                       animation = tween(2000),
                       repeatMode = RepeatMode.Restart
                   ), label = ""
               )
               CircularProgressIndicator(
                   modifier = Modifier
                       .size(50.dp)
                       .rotate(rotationValue)

                   ,
                   color = colorResource(id = R.color.green)
               )
           }
           if(viewModel.userInfo is Resource.Failed) {
               Text(
                   text = "Check your connection",
                   fontSize = 16.sp,
                   color = Color.Gray
               )
           }
               if(viewModel.userInfo is Resource.Success){
        var scrollPosition by remember { mutableStateOf(0) }
        var scrollPercentage by remember { mutableStateOf(0f) }
        val scrollState = rememberScrollState()
            ProfileHeader(progress = scrollPercentage , viewModel = viewModel)
        Column(
            Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.gray))
                .verticalScroll(scrollState)

        ) {
            scrollPosition = scrollState.value
            scrollPercentage =if((scrollPosition.toFloat() / scrollState.maxValue).coerceIn(0f, 1f)<=0.25 )(scrollPosition.toFloat() / scrollState.maxValue).coerceIn(0f, 1f)*4 else 1F
            //Log.d("scroll",scrollPercentage.toString())

            Overview(viewModel=viewModel,bottomPadding)
        }
    }}}
}

@Composable
fun Overview(viewModel: HomePageVM, bottomPadding: Dp) {
    var progress by remember {
        mutableFloatStateOf(.15F)
    }
    progress=(viewModel.userInfo.data!!.project.progress.toFloat() / 100)
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
                   Text(text = viewModel.userInfo.data!!.event.name,
                       color = Color.White,
                       fontSize = 16.sp,
                       //fontFamily = FontFamily.Cursive,
                       fontWeight = FontWeight.Bold,
                       modifier = Modifier.padding(10.dp)
                       )
            Text(text = viewModel.userInfo.data!!.event.date,
                color = Color.White,
                fontSize = 15.sp,
                //fontFamily = FontFamily.Cursive,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    ,
                textAlign = TextAlign.End,

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
            NexusCard(viewModel.userInfo.data!!.members_number.toString(),"Member's number", painterResource(id = R.drawable.members), Modifier.weight(1f))
            NexusCard(viewModel.userInfo.data!!.done_projects.toString(),"Done projects", painterResource(id = R.drawable.power), Modifier.weight(1f))

        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            NexusCard(viewModel.userInfo.data!!.pending_projects.toString(),"Pending projects", painterResource(id = R.drawable.power), Modifier.weight(1f))
            NexusCard(viewModel.userInfo.data!!.task_count.toString(),"Task's number", painterResource(id = R.drawable.members), Modifier.weight(1f))


        }
        Text(text = "Projects",
            color = Color.White,
            fontSize = 20.sp,
            //fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        Text(text = viewModel.userInfo.data!!.project.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color.White,
            fontSize = 19.sp,
            textAlign = TextAlign.Center
            //fontFamily = FontFamily.Cursive,


        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = bottomPadding + 20.dp

            ), contentAlignment = Alignment.Center){
            CircularProgressIndicator(progress = progress, color = colorResource(id = R.color.green), modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .shadow(
                    5.dp, shape = CircleShape, spotColor = Color.White
                ), strokeWidth = 35.dp, trackColor = colorResource(
                id = R.color.card_bg
            ))     
            Text(text = "${(progress*100).toInt()}%", modifier = Modifier.fillMaxSize(), fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                          }
     

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
     }
}

@OptIn(ExperimentalMotionApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ProfileHeader(progress: Float, viewModel: HomePageVM) {
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
        GlideImage(model = viewModel.userInfo.data!!.userData.image, contentDescription ="" ,
            modifier = Modifier
                .clip(CircleShape)
                .layoutId("profile_pic"))
        /*Image(
            painter = painterResource(id = R.drawable.members),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .layoutId("profile_pic")
        )*/
        Text(
            text = viewModel.userInfo.data!!.userData.name,
            fontSize = 23.sp,
            modifier = Modifier.layoutId("username"),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.green)
        )
        Text(
            text = viewModel.userInfo.data!!.userData.role,
            fontSize = 20.sp,
            modifier = Modifier.layoutId("department"),
            color = colorResource(id = R.color.green)
        )
        Text(
            text = "Manager",
            fontSize = 20.sp,
            modifier = Modifier.layoutId("manager"),
            color = Color.White
        )
    }
}
