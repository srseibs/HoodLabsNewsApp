package hoods.com.noteapplication.data.local.converters

import hoods.com.noteapplication.data.local.model.NoteEntity
import hoods.com.noteapplication.domain.Note

fun Note.toNoteEntity() : NoteEntity =
    NoteEntity(
        id = id,
        title = title,
        content = content,
        creationDate = creationDate,
        isBookmarked = isBookmarked
    )

fun NoteEntity.toNote() : Note =
    Note(
        id = id,
        title = title,
        content = content,
        creationDate = creationDate,
        isBookmarked = isBookmarked
    )