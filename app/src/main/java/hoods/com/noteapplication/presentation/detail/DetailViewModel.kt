package hoods.com.noteapplication.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoods.com.noteapplication.domain.Note
import hoods.com.noteapplication.domain.NoteRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

const val NEW_NOTE_ID = -1L
const val NOTE_ID_HANDLE_KEY = "note_ID"

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private var noteId = NEW_NOTE_ID
    init {
        noteId = savedStateHandle[NOTE_ID_HANDLE_KEY] ?: NEW_NOTE_ID
        initialize()
    }

    var state by mutableStateOf(DetailState())
        private set

    val isFormNotBlank: Boolean
        get() = state.title.isNotEmpty() && state.content.isNotEmpty()

    private val noteFromState: Note
        get() = state.run {
            Note(
                id = id,
                title = title,
                content = content,
                creationDate = creationDate,
                isBookmarked = isBookmarked, // TODO: he skipped this ?!
            )
        }

    private fun initialize() {
        val creatingNewNote = (noteId == NEW_NOTE_ID)
        state = state.copy(creatingNewNote = creatingNewNote)
        if( !creatingNewNote) {
            getNoteById()
        }
    }

    private fun getNoteById() = viewModelScope.launch() {
        repository.getNoteById(noteId).collectLatest() {
            state = state.copy(
                id = it.id,
                title = it.title,
                content = it.content,
                isBookmarked = it.isBookmarked,
                creationDate = it.creationDate,
                creatingNewNote = false,
            )
        }
    }

    fun onTitleChange(title: String) {
        state = state.copy(title = title)
    }
    fun onContentChange(content: String) {
        state = state.copy(content = content)
    }
    fun onBookmarkedChanged(newValue: Boolean) {
        state = state.copy(isBookmarked = newValue)
    }

    fun addOrUpdateNote() = viewModelScope.launch {
        repository.insertReplaceNote(note = noteFromState)
    }
}



data class DetailState(
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val isBookmarked: Boolean = false,
    val creationDate: Date = Date(),
    val creatingNewNote: Boolean = false,
)