package hoods.com.noteapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hoods.com.noteapplication.presentation.detail.NEW_NOTE_ID
import hoods.com.noteapplication.presentation.navigation.NoteNavigation
import hoods.com.noteapplication.presentation.navigation.Screens
import hoods.com.noteapplication.ui.theme.NoteApplicationTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                ) {
                    val navController = rememberNavController()
                    NoteApplication(navController = navController)
                }
            }
        }
    }
}

@Composable
fun NoteApplication(navController: NavHostController) {
    var currentTab: TabScreen by remember { mutableStateOf(TabScreen.Home) }

    Scaffold(
        bottomBar = {
        BottomAppBar(actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {

                InputChipFor(
                    tabScreen = TabScreen.Home,
                    currentTab = currentTab,
                    onClick = {
                        currentTab = TabScreen.Home
                        navController.navigate(Screens.Home.route){
                            popUpTo(Screens.Home.route){ inclusive = true }
                        }
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                InputChipFor(
                    tabScreen = TabScreen.Bookmarks,
                    currentTab = currentTab,
                    onClick = {
                        currentTab = TabScreen.Bookmarks
                        navController.navigate(Screens.Bookmark.route)
                    }
                )

            }
        })
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        route = Screens.Detail.routeWithArgs(NEW_NOTE_ID))
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) {
        Box(modifier = Modifier.fillMaxWidth().padding(it)) {
            NoteNavigation(navHostController = navController)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputChipFor(
    tabScreen: TabScreen,
    currentTab: TabScreen,
    onClick: () -> Unit,
) = InputChip(
    label = { Text(text = tabScreen.title) },
    selected = currentTab == tabScreen,
    onClick = onClick,
    trailingIcon = {
        Icon(imageVector = tabScreen.icon, contentDescription = "Bottom bar icon")
    }
)


sealed class TabScreen(val title: String, val icon: ImageVector) {
    object Home : TabScreen(title = "Home", Icons.Default.Home)
    object Bookmarks : TabScreen(title = "Bookmarks", Icons.Default.Bookmarks)
}
