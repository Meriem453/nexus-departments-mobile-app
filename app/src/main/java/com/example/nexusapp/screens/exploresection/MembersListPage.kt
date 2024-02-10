package com.example.survisionapp.nexustest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import androidx.wear.compose.material.Scaffold


@Preview
@Composable
fun MembersListPagePrev() {
    MembersListPage()
}




@Composable
fun MembersListPage() {
    data class Member(val name:String, val team:String, val points:String)
    val membersList = listOf(
        Member("Alice dzb", "Team A", "points"),
        Member("Alice", "Team A", "50"),
        Member("Bob", "Team B", "30"),
        Member("Charlie", "Team A", "70"),
        Member("David", "Team C", "40"),
        Member("Eva", "Team B", "85"),
        Member("Frank", "Team C", "60"),
        Member("Grace", "Team A", "75"),
        Member("Henry", "Team B", "90"),
        Member("Ivy", "Team C", "55"),
        Member("Jack", "Team A", "80")
    )

    /*Scaffold {
        MembersListHeader({}, {})
        Column {
            Spacer(modifier = Modifier.height(70.dp))
            MemberDataHeader()
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.height(5.dp))
            membersList.forEach() {
                MemberDataView(name = it.name, Team = it.team, Points = it.points)
                Spacer(modifier = Modifier.height(5.dp))
            }


        }

    }*/
}

