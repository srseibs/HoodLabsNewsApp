package hoods.com.noteapplication.domain

import java.util.Date

data class Note(
    val id: Long = 0,
    val title: String,
    val content: String,
    val creationDate: Date,
    val isBookmarked: Boolean = false,
)
