package hoods.com.noteapplication.data.repository

import hoods.com.noteapplication.data.local.NoteDao
import hoods.com.noteapplication.data.local.converters.toNote
import hoods.com.noteapplication.data.local.converters.toNoteEntity
import hoods.com.noteapplication.domain.Note
import hoods.com.noteapplication.domain.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> =
        noteDao.getAllNotes().map {entityList->
            entityList.map{ it.toNote() }
        }

    override fun getNoteById(noteId: Long): Flow<Note> =
        noteDao.getNoteById(noteId).map { it.toNote() }


    override suspend fun insertReplaceNote(note: Note) =
        noteDao.insertReplaceNote(note.toNoteEntity())


    override suspend fun deleteNoteById(noteId: Long) =
        noteDao.deleteNote(noteId)


    override fun getBookmarkedNotes(): Flow<List<Note>> =
        noteDao.getBookmarkedNotes().map {entityList->
            entityList.map{ it.toNote() }
        }

}