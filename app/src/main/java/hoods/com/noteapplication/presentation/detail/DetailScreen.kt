package hoods.com.noteapplication.presentation.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.outlined.NoteAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hoods.com.noteapplication.ui.theme.NoteApplicationTheme

@Composable
fun DetailScreen(
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
        onSaveClick = { viewModel.addOrUpdateNote() },
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
    onSaveClick: () -> Unit,
    onBackPress: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        ) {
        TopSection(
            title = title,
            onTitleChange = onTitleChange,
            isBookmarked = isBookmarked,
            onBookmarkChange = onBookmarkChange,
            onBackPress = onBackPress,
        )
        Spacer(modifier = Modifier.height(12.dp))
        AnimatedVisibility(visible = isFormNotBlank) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(onClick = onSaveClick) {
                    val icon = if (isCreatingNewNote)
                        Icons.Outlined.NoteAdd
                    else
                        Icons.Default.Check
                    Icon(imageVector = icon, contentDescription = "Save")
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        NotesTextField(
            value = content,
            onValueChange = onContentChange,
            label = "Enter note content...",
            modifier = Modifier.fillMaxSize()
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
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBackPress) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back arrow",
            )
        }
        NotesTextField(
            value = title,
            onValueChange = onTitleChange,
            modifier = Modifier.weight(1f),
            label = "Enter title here",
            labelAlign = TextAlign.Center,

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

@Composable
private fun NotesTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    labelAlign: TextAlign? = null,

    ) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words
        ),

        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = label,
                textAlign = labelAlign,
            )
        },
    )
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DetailScreenPreview() {

    var title by remember { mutableStateOf("") }
    var isBookmarked by remember { mutableStateOf(false) }

    NoteApplicationTheme {
        DetailScreenContent(
            isCreatingNewNote = true,
            title = title,
            onTitleChange = { title = it },
            content = "",
            onContentChange = {},
            isBookmarked = isBookmarked,
            onBookmarkChange = { isBookmarked = !isBookmarked },
            isFormNotBlank = false,
            onSaveClick = {},
            onBackPress = {},
        )
    }
}