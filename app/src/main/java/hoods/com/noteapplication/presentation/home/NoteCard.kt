package hoods.com.noteapplication.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hoods.com.noteapplication.domain.Note
import hoods.com.noteapplication.domain.fakeNotes
import hoods.com.noteapplication.ui.theme.NoteApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCard(
    index: Int,
    note: Note,
    onBookmarkClicked: (note: Note) -> Unit,
    onDeleteNote: (id: Long) -> Unit,
    onNoteClicked: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val isEvenIndex = index % 2 == 0
    val cornerDp = 20.dp
    val shape =
        if (isEvenIndex)
            RoundedCornerShape(
                topStart = cornerDp,
                bottomEnd = cornerDp
            )
        else
            RoundedCornerShape(
                topEnd = cornerDp,
                bottomStart = cornerDp
            )

    val icon = if (note.isBookmarked)
        Icons.Default.BookmarkRemove
    else
        Icons.Outlined.BookmarkAdd

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = shape,
        onClick = { onNoteClicked(note.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(cornerDp/2),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ){
                IconButton(onClick = { onDeleteNote(note.id) }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete icon" )
                }
                IconButton(onClick = { onBookmarkClicked(note) }) {
                    Icon(imageVector = icon, contentDescription = "Delete icon" )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun NoteCardPreview() {
    NoteApplicationTheme {

        NoteCard(
            index = 1,
            note = fakeNotes[2],
            onNoteClicked = {},
            onDeleteNote = {},
            onBookmarkClicked = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoteCardPreview2() {
    NoteApplicationTheme {

        NoteCard(
            index = 0,
            note = fakeNotes[0],
            onNoteClicked = {},
            onDeleteNote = {},
            onBookmarkClicked = {},
        )
    }
}