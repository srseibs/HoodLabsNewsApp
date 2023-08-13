package hoods.com.noteapplication.domain

import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    fun getNoteById(noteId: Long): Flow<Note>

    suspend fun insertReplaceNote(note: Note)

    suspend fun deleteNoteById(noteId: Long)

    fun getBookmarkedNotes(): Flow<List<Note>>

}