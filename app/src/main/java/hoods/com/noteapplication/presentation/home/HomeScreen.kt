package hoods.com.noteapplication.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    modifier: Modifier = Modifier
) {
    val notes = viewModel.state.collectAsState().value.notes
    when (notes) {
        is Resource.Success -> {
            Column(modifier = Modifier.fillMaxSize()) {
                Text("Home Screen Content below")
                HomeScreenContent(
                    notes.data,
                    onBookmarkClicked = {},
                    onDeleteNote = {},
                    onNoteClicked = {})

            }
        }

        else -> {}
    }
}

@Composable
private fun HomeScreenContent(
    notes: List<Note>,
    modifier: Modifier = Modifier,
    onBookmarkClicked: (note: Note) -> Unit,
    onDeleteNote: (id: Long) -> Unit,
    onNoteClicked: (id: Long) -> Unit,
) {
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


@Preview
@Composable
fun HomeScreenContentPreview() {
    NoteApplicationTheme {
        HomeScreenContent(
            notes = fakeNotes,
            onBookmarkClicked = {} ,
            onDeleteNote =  {},
            onNoteClicked =  {}
        )
    }

}