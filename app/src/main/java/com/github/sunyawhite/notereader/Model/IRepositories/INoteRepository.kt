package com.github.sunyawhite.notereader.Model

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

interface INoteRepository : LifecycleObserver {

    suspend fun getNewId() : Long

    suspend fun getAllNotes() : List<Note>?

    suspend fun getNoteById(id : Long) : Note?

    suspend fun addNewNote(note : Note) : Boolean

    suspend fun deleteNote(id : Long) : Boolean

    suspend fun containsNoteById(id : Long) : Boolean

    fun registerLifecycle(lifecycle: Lifecycle)

}