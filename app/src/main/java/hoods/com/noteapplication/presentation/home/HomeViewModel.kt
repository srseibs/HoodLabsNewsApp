package hoods.com.noteapplication.presentation.home

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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()


    init {
        getAllNotes()
    }

    fun deleteNote(noteId: Long) = viewModelScope.launch {
        noteRepository.deleteNoteById(noteId)
    }

    fun toggleBookmarked(note: Note) = viewModelScope.launch {
        noteRepository.insertReplaceNote(note.copy(isBookmarked = !note.isBookmarked))
    }

    private fun getAllNotes() {
        _state.value = HomeState(notes = Resource.Loading)
        noteRepository.getAllNotes().onEach {
            _state.value = HomeState(notes = Resource.Success(it))
        }.catch {
            _state.value = HomeState(notes = Resource.Error(it.message))
        }.launchIn(viewModelScope)
    }
}


data class HomeState(
    val notes: Resource<List<Note>> = Resource.Loading
)