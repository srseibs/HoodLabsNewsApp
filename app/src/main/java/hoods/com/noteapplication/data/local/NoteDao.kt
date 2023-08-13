package hoods.com.noteapplication.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import hoods.com.noteapplication.data.local.model.NoteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME} ORDER BY creationDate")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME} WHERE id = :id ORDER BY creationDate")
    fun getNoteById(id: Long): Flow<NoteEntity>

    @Upsert
    suspend fun insertReplaceNote(note: NoteEntity)

    @Query("DELETE FROM ${NoteEntity.TABLE_NAME} WHERE id = :id")
    suspend fun deleteNote(id: Long)

    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME} WHERE isBookmarked = 1 ORDER BY creationDate DESC")
    fun getBookmarkedNotes(): Flow<List<NoteEntity>>

}