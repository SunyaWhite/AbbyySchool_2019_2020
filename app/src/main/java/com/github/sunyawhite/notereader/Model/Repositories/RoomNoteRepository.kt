package com.github.sunyawhite.notereader.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

/*
    Паттерн Адаптер. Приспосабливаем INoteDAO под наши нужды
 */
class RoomNoteRepository (val context : Context) : INoteRepository {

    // DAO
    private val dao : INoteDAO
    // Database
    private val database : SQLNoteDatabase

    init{
        database = SQLNoteDatabase.getDatabase(this.context)
        dao = database.noteDao()
    }


    override suspend fun getNewId(): Long = withContext(Dispatchers.IO){
        val notes = dao.getAll()
        notes.maxBy { note -> note.id }.let { note -> if(note == null) 1 else note.id + 1}
    }

    override suspend fun getAllNotes(): List<Note>? = withContext(Dispatchers.IO){
        dao.getAll().map { note -> Note.toNote(note) }
    }

    override suspend fun getNoteById(id: Long): Note? = withContext(Dispatchers.IO){
        Note.toNote(dao.getById(id))
    }

    override suspend fun addNewNote(note: Note): Boolean = withContext(Dispatchers.IO){
        try {
            dao.addNote(RoomNote.toNoteRoom(note))
            true
        }
        catch(exc : Exception) {
            Log.e("RoomNoteRepository", exc.message)
            false
        }
    }
    override suspend fun deleteNote(id: Long): Boolean = withContext(Dispatchers.IO){
        try{
            dao.deleteNote(dao.getById(id))
            true
        }
        catch (exc: Exception){
            Log.e("RoomNoteRepository", exc.message)
            false
        }
    }

    override suspend fun containsNoteById(id: Long): Boolean = withContext(Dispatchers.IO){
        try{
            dao.getAll().any { note -> note.id == id }
        }
        catch (exc : Exception){
            Log.e("RoomNoteRepository", exc.message)
            false
        }
    }

    override fun registerLifecycle(lifecycle: Lifecycle) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}