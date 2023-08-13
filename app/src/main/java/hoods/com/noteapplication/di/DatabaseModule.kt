package hoods.com.noteapplication.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hoods.com.noteapplication.data.local.NoteDao
import hoods.com.noteapplication.data.local.NotesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNoteDao(database: NotesDatabase): NoteDao =
        database.dao

    @Provides
    @Singleton
    fun provideNoteDatabase(
        @ApplicationContext context: Context,
    ) : NotesDatabase = Room.databaseBuilder(
        context = context,
        NotesDatabase::class.java,
        "notes.db"
    ).build()

}