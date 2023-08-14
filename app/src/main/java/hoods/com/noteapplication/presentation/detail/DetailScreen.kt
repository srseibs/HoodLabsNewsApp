package hoods.com.noteapplication.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hoods.com.noteapplication.ui.theme.NoteApplicationTheme

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {

    val state = viewModel.state

    DetailScreenContent(
        isCreatingNewNote = state.creatingNewNote,
        title = state.title,
        onTitleChange = viewModel::onTitleChange,
        content = state.content,
        onContentChange = viewModel::onContentChange,
        isBookmarked = state.isBookmarked,
        onBookmarkChange = viewModel::onBookmarkedChanged,
        isFormNotBlank = viewModel.isFormNotBlank,
        onDetailRequest = { },
        onBackPress = navigateUp,
    )

}


@Composable
fun DetailScreenContent(
    isCreatingNewNote: Boolean,
    title: String,
    onTitleChange: (String) -> Unit,
    content: String,
    onContentChange: (String) -> Unit,
    isBookmarked: Boolean,
    onBookmarkChange: (newValue: Boolean) -> Unit,
    isFormNotBlank: Boolean,
    onDetailRequest: (id: Long) -> Unit,
    onBackPress: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),

        ) {
        TopSection(
            title = title,
            onTitleChange = onTitleChange,
            isBookmarked = isBookmarked,
            onBookmarkChange = onBookmarkChange,
            onBackPress = onBackPress,
        )
    }


}

@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    title: String,
    onTitleChange: (String) -> Unit,
    isBookmarked: Boolean,
    onBookmarkChange: (newValue: Boolean) -> Unit,
    onBackPress: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBackPress) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back arrow",
            )
        }
        TextField(
            modifier = Modifier.weight(1f),
            value = title,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words
            ),
            onValueChange = onTitleChange,

            placeholder = { Text("type title here...") },

        )
        IconButton(onClick = {
            onBookmarkChange(!isBookmarked)
        }) {
            Icon(
                imageVector =
                if (isBookmarked)
                    Icons.Filled.Bookmark
                else
                    Icons.Outlined.BookmarkAdd,
                contentDescription = "Bookmarked Icon"
            )
        }

    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DetailScreenPreview() {

    var title by remember { mutableStateOf("") }
    var isBookmarked by remember { mutableStateOf(false) }

    NoteApplicationTheme {
        DetailScreenContent(
            isCreatingNewNote = false,
            title = title,
            onTitleChange = { title = it },
            content = "A note under construction",
            onContentChange = {},
            isBookmarked = isBookmarked,
            onBookmarkChange = { isBookmarked = !isBookmarked },
            isFormNotBlank = true,
            onDetailRequest = {},
            onBackPress = {},
        )
    }
}