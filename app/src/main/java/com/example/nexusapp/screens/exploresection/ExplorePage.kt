package com.example.survisionapp.nexustest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Scaffold

@Preview
@Composable
fun Preview(){
    ExplorePage()
}
@Composable
fun ExplorePage() {
    Scaffold(Modifier.background(Color(0xFF2A2A2A))) {
        val exploreItemsList =
            listOf("Calendar", "Members List", "Meetings", "Tasks", "Projects", "Help and Problems")
        ExploreHeader()

        Column(
            Modifier.padding(start = 25.dp, end = 25.dp)

                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            exploreItemsList.forEach {
                ExploreItemCard(title = it)
                {
                    when (it) {
                        "Calendar" -> {/* TODO: navigate to calendar page*/
                        }

                        "Members List" -> {/* TODO: navigate to member list page*/
                        }

                        "Meetings" -> {/* TODO: navigate to meetings page*/
                        }

                        "Tasks" -> { /* TODO: navigate to tasks page*/
                        }

                        "Projects" -> {/* TODO: navigate to projects page*/
                        }

                        "Help and Problems" -> {/* TODO: navigate to help page*/
                        }
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))


            }


        }


    }
}
