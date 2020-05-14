package com.github.sunyawhite.notereader.Model

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

// Сущность заметки. Используется для работы с базой данных
@RealmClass
internal open class NoteRealm (
    @PrimaryKey
    var Id : Long = 0,
    var Date : Date = Date(),
    var Text : String = "",
    var DrawableRes : String = "") : RealmModel {

    override fun toString(): String {
        return "Note Id : $Id | Note Date : $Date"
    }

    companion object{
        internal fun toNoteRealm(note : Note) =
            NoteRealm(note.Id, note.Date, note.Text, note.DrawableRes)
    }

}