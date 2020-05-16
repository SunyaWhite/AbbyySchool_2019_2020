package com.github.sunyawhite.notereader.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import com.github.sunyawhite.notereader.R
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import io.realm.exceptions.RealmException
import io.realm.kotlin.deleteFromRealm
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*
import kotlin.random.Random

// Класс для работы с базой данных Realm
class RealmNoteRepository (val context : Context) : INoteRepository {

    // Database context
    private val realm : Realm
    // Database configuration provider class
    private val config : RealmConfiguration

    init {
        Realm.init(context)
        config = RealmConfig.provideDefaultConfiguration()
        realm = Realm.getInstance(config)
    }

    override suspend fun getNewId(): Long = withContext(Dispatchers.IO) {
        val note = realm.where<NoteRealm>().sort("Id", Sort.DESCENDING)
            .findFirst()
        note?.Id ?: 1
    }


    override suspend fun getAllNotes(): List<Note>? = withContext(Dispatchers.IO) {
        realm.where<NoteRealm>()
            .findAll()
            .map { noteRealm -> Note.toNote(noteRealm) } // конвертируем данные
    }

    override suspend fun getNoteById(id: Long): Note? = withContext(Dispatchers.IO) {
        Note.toNote(
            realm.where<NoteRealm>().equalTo("Id", id)
                .findFirst()
        )
    }

    override suspend fun addNewNote(note: Note): Boolean  = withContext(Dispatchers.IO){
        addNewNote(NoteRealm.toNoteRealm(note))
    }

    override suspend fun deleteNote(id: Long): Boolean = withContext(Dispatchers.IO) {
        try{
            realm.executeTransaction { realm ->
                // Получаем данные из базы
                var note = realm.where<NoteRealm>().equalTo("Id", id).findFirst()
                    ?: throw RealmException("Unable to find note with a such id")
                // производим удаление данных
                note.deleteFromRealm()
            }
            true
        }
        catch (ex : Exception){
            Log.e("REALM EXCEPTION : ", ex.message)
            false
        }
    }

    override suspend fun containsNoteById(id: Long): Boolean = withContext(Dispatchers.IO) {
        realm.where<NoteRealm>().equalTo("Id", id).count() > 0
    }

    override suspend fun updateNote(note: Note): Boolean {
        TODO("Not yet implemented")
    }

    // Метод для добавления данных в базу данных. Вызывается только внутри функция данного класса
    private fun addNewNote(note : NoteRealm) : Boolean{
        return try {
            realm.executeTransaction { realm ->
                realm.copyToRealm(note)
                    ?: throw RealmException("Unable to add note to the database. Note $note")
            }
            true
        }
        catch(ex : Exception) {
            Log.e("REALM EXCEPTION : ", ex.message)
            false
        }
    }

}