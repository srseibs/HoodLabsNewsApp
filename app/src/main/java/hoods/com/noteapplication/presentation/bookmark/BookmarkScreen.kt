package hoods.com.noteapplication.presentation.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import hoods.com.noteapplication.presentation.home.NoteCard
import hoods.com.noteapplication.ui.theme.NoteApplicationTheme
import hoods.com.noteapplication.util.Resource

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = hiltViewModel(),
    onNoteClicked: (id: Long) -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    BookmarkScreenContent(
        state = state,
        onBookmarkChange = { viewModel.onBookmarkChange(it) },
        onDeleteNote = { viewModel.onDeleteNote(it)},
        onNoteClicked = { onNoteClicked }
    )
}

@Composable
fun BookmarkScreenContent(
    state: BookmarkState,
    modifier: Modifier = Modifier,
    onBookmarkChange: (note: Note) -> Unit,
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

            LazyColumn(
                contentPadding = PaddingValues(4.dp),
                modifier = modifier,
            ) {
                itemsIndexed(notes) { index, item ->

                    NoteCard(
                        index = index,
                        note = item,
                        onBookmarkClicked = onBookmarkChange,
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
fun BookmarkScreenContentPreview() {
    NoteApplicationTheme {
        BookmarkScreenContent(
            state = BookmarkState(notes = Resource.Success(fakeNotes)),
            onBookmarkChange = {},
            onDeleteNote = {},
            onNoteClicked = {}
        )
    }
}
@Preview(name = "ERROR", showSystemUi = true, showBackground = true)
@Composable
fun BookmarkScreenContentPreview2() {
    NoteApplicationTheme {
        BookmarkScreenContent(
            state = BookmarkState(notes = Resource.Error("Some fake error message here")),
            onBookmarkChange = {},
            onDeleteNote = {},
            onNoteClicked = {}
        )
    }
}