package com.example.nexusapp.screens

import androidx.compose.runtime.Composable
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import android.Manifest
import android.media.MediaPlayer
import android.widget.Toast
import androidx.camera.core.Preview
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nexusapp.QRCodeManager.BarcodeScanner
import com.example.nexusapp.R
import com.example.nexusapp.Repo.Resource
import com.example.nexusapp.viewmodels.CheckInVM
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Destination
@Composable
fun CheckInPage() {
    val viewmodel = hiltViewModel<CheckInVM>()
    var code by remember {
        mutableStateOf("Code")
    }
    var coroutine = rememberCoroutineScope()

    val mediaPlayer = MediaPlayer.create(
        LocalContext.current,
        R.raw.scanned
    )
    var delay by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCamPermission = granted
        }
    )
    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray))
        , verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            text = "Scan the QR code",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top=20.dp, bottom = 20.dp)


        )
        if (hasCamPermission) {
            AndroidView(modifier = Modifier.padding(30.dp),
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(
                            Size(
                                previewView.width,
                                previewView.height
                            )
                        )
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        BarcodeScanner { result ->
                            if(!delay){
                                delay=true
                                coroutine.launch {
                                    delay(2000L)
                                    code = result
                                    mediaPlayer.start()
                                    try {
                                        val id=code.toInt()
                                        viewmodel.checkIn(id)
                                    } catch (e:Exception){
                                        Toast.makeText(context,"Enter a valid QR Code",Toast.LENGTH_LONG).show()
                                    }
                                    delay=false
                                }
                            }

                        }
                    )
                    try {
                        cameraProviderFuture.get().bindToLifecycle(
                            lifecycleOwner,
                            selector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    previewView
                },

                )
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    colorResource(id = R.color.card_bg)
                )
                .border(
                    2.dp,
                    Brush.verticalGradient(listOf(Color(0xFF76E494), Color(0xFF2A2A2A))),
                    RoundedCornerShape(20.dp)

                )
                , verticalArrangement = Arrangement.Center
            ) {
                if(viewmodel.participant is Resource.Loading){
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
                            .size(30.dp)
                            .rotate(rotationValue)

                        ,
                        color = colorResource(id = R.color.green)
                    )
                }
                if(viewmodel.participant is Resource.Failed){
                    Text(
                        text = viewmodel.participant.message!!,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier =Modifier.padding(10.dp)
                    )
                }
                if(viewmodel.participant is Resource.Success){
                    Text(
                        text = "Participant name: ${viewmodel.participant.data!!.name}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier =Modifier.padding(10.dp)
                    )
                    Text(
                        text = "Team: ${viewmodel.participant.data!!.team}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier =Modifier.padding(10.dp)
                    )
                }

                /*Button(onClick = {
                    if(code!="Code")
                        resultNavigator.navigateBack(code)


                    else Toast.makeText(context,"You have to scan first",Toast.LENGTH_SHORT).show()
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, top = 10.dp, bottom = 10.dp)
                    , colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.buttons_green))


                ) {

                    Text(
                        text = "Add",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .fillMaxWidth()

                    )
                }*/
            }



        }
    }
}