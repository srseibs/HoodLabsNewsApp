package hoods.com.noteapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hoods.com.noteapplication.data.local.converters.DateConverter
import hoods.com.noteapplication.data.local.model.NoteEntity

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class NotesDatabase: RoomDatabase() {
    abstract val dao: NoteDao
}