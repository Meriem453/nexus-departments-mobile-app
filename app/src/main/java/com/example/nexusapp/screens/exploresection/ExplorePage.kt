import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.destinations.CalendarDestination
import com.example.destinations.IssuesPageDestination
import com.example.destinations.MeetingsPageDestination
import com.example.destinations.ProjectsPageDestination
import com.example.survisionapp.nexustest.ExploreHeader
import com.example.survisionapp.nexustest.ExploreItemCard
import com.example.survisionapp.nexustest.MembersListPage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Preview
@Composable
fun Preview(){
    //ExplorePage()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExplorePage(navigator:DestinationsNavigator) {
    var currentScreen by remember { mutableStateOf<Screen?>(null) }

    Scaffold(Modifier.background(Color(0xFF2A2A2A))) {
        val exploreItemsList = listOf("Calendar", "Members List", "Meetings", "Tasks", "Projects", "Help and Problems")
        ExploreHeader()

        Column(
            Modifier.padding(start = 25.dp, end = 25.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            exploreItemsList.forEach { item ->
                ExploreItemCard(title = item)
                {
                    when (item) {
                        "Members List" -> {
                            currentScreen = Screen.MembersList
                            navigator.navigate(MeetingsPageDestination())
                        }
                        "Calendar"-> navigator.navigate(CalendarDestination)
                        "Meetings"->navigator.navigate(MeetingsPageDestination)
                        "Projects" -> navigator.navigate(ProjectsPageDestination)
                        "Help and Problems"-> navigator.navigate(IssuesPageDestination)

                        // the other items here
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
            }
        }

        currentScreen?.let { screen ->
            when (screen) {
                Screen.MembersList -> MembersListPage()
            }
        }
    }
}

enum class Screen {
    MembersList,
    // All explore screens here
}
