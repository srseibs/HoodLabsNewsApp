package hoods.com.noteapplication.domain

import java.util.Date


data class Note(
    val id: Long = 0,
    val title: String,
    val content: String,
    val creationDate: Date,
    val isBookmarked: Boolean = false,
)

val fakeNotes = listOf(
    Note(0,
        title = "First Note",
        content = "The content of this note. The content of this note. The content of this note.",
        creationDate = Date(),
        isBookmarked = false
    ),
    Note(1,
        title = "Second Note",
        content = "The content of this note",
        creationDate = Date(),
        isBookmarked = false
    ),
    Note(2,
        title = "Third Note",
        content = "The content of this note Bookmarked. Hello hello, can you hear me?",
        creationDate = Date(),
        isBookmarked = true
    ),
    Note(3,
        title = "Fourth Note",
        content = "The content of this note Bookmarked",
        creationDate = Date(),
        isBookmarked = true
    ),
)
