package hoods.com.noteapplication.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import hoods.com.noteapplication.data.local.model.NoteEntity.Companion.TABLE_NAME
import java.util.Date

@Entity(tableName = TABLE_NAME)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val creationDate: Date,
    val isBookmarked: Boolean = false,
) {
    companion object {
        const val TABLE_NAME = "notes"
    }
}

