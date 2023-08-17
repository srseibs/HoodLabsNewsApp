package hoods.com.noteapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import hoods.com.noteapplication.presentation.bookmark.BookmarkScreen
import hoods.com.noteapplication.presentation.detail.DetailScreen
import hoods.com.noteapplication.presentation.home.HomeScreen

sealed class Screens(val route: String) {
    object Home : Screens("home_screen")
    object Bookmark : Screens("detail_screen")

    companion object {
        const val ARG_KEY_NOTE_ID = "noteId"
    }
    object Detail : Screens("detail_screen/{$ARG_KEY_NOTE_ID}") {
        val navArgDeclaration = listOf(navArgument(ARG_KEY_NOTE_ID) { type = NavType.LongType })
        fun routeWithArgs(noteId: Long) = "detail_screen/$noteId"
    }
}

@Composable
fun NoteNavigation(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController, startDestination = Screens.Home.route
    ) {
        composable(Screens.Home.route) {
            HomeScreen(onNoteClicked = {
                navHostController.navigate(Screens.Detail.routeWithArgs(it))
            })
        }

        composable(Screens.Bookmark.route) {
            BookmarkScreen(onNoteClicked = { navHostController.popBackStack() })
        }

        composable(
            route = Screens.Detail.route,
            arguments = Screens.Detail.navArgDeclaration,
        ) {
            DetailScreen(navigateUp = { navHostController.popBackStack() })
        }

    }

}
