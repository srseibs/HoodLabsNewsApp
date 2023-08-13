package hoods.com.noteapplication.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoods.com.noteapplication.domain.Note
import hoods.com.noteapplication.domain.NoteRepository
import hoods.com.noteapplication.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _state: MutableStateFlow<BookmarkState> = MutableStateFlow(BookmarkState())
    val state: StateFlow<BookmarkState> = _state.asStateFlow()

    private fun getBookmarkedNotes() {
        repository.getBookmarkedNotes().onEach {
            _state.value = BookmarkState(
                notes = Resource.Success(it)
            )
        }.catch {
            _state.value = BookmarkState(
                notes = Resource.Error(it.localizedMessage)
            )
        }
    }

    fun onBookmarkChange(note: Note) {
        viewModelScope.launch {
            repository.insertReplaceNote(
                note = note.copy(
                    isBookmarked = !note.isBookmarked
                )
            )
        }
    }

    fun onDeleteNote(noteId: Long) {
        viewModelScope.launch {
            repository.deleteNoteById(noteId)
        }
    }
}

data class BookmarkState(
    val notes: Resource<List<Note>> = Resource.Loading
)