package hoods.com.noteapplication.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hoods.com.noteapplication.domain.Note
import hoods.com.noteapplication.domain.fakeNotes
import hoods.com.noteapplication.ui.theme.NoteApplicationTheme
import hoods.com.noteapplication.util.Resource

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onBookmarkClicked: (note: Note) -> Unit,
    onDeleteNote: (id: Long) -> Unit,
    onNoteClicked: (id: Long) -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    HomeScreenContent(
        state = state,
        onBookmarkClicked = onBookmarkClicked,
        onDeleteNote = onDeleteNote,
        onNoteClicked = onNoteClicked
    )

}

@Composable
private fun HomeScreenContent(
    state: HomeState,
    modifier: Modifier = Modifier,
    onBookmarkClicked: (note: Note) -> Unit,
    onDeleteNote: (id: Long) -> Unit,
    onNoteClicked: (id: Long) -> Unit,
) {

    when (val resource = state.notes) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(modifier = Modifier.fillMaxWidth(0.4f))
            }
        }

        is Resource.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = resource.message ?: "Unknown error...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is Resource.Success -> {
            val notes = resource.data

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(4.dp),
                modifier = modifier,
            ) {
                itemsIndexed(notes) { index, item ->

                    NoteCard(
                        index = index,
                        note = item,
                        onBookmarkClicked = onBookmarkClicked,
                        onDeleteNote = onDeleteNote,
                        onNoteClicked = onNoteClicked,
                    )

                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenContentPreview() {
    NoteApplicationTheme {
        HomeScreenContent(
            state = HomeState(notes = Resource.Success(fakeNotes)),
            onBookmarkClicked = {},
            onDeleteNote = {},
            onNoteClicked = {}
        )
    }
}
@Preview(name = "ERROR", showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenContentPreview2() {
    NoteApplicationTheme {
        HomeScreenContent(
            state = HomeState(notes = Resource.Error("Some fake error message here")),
            onBookmarkClicked = {},
            onDeleteNote = {},
            onNoteClicked = {}
        )
    }
}