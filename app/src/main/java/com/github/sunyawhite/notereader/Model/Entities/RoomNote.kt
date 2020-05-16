package com.github.sunyawhite.notereader.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

// Сущность заметки. Используется для работы с базой данных SQLLite через ORM Room
@Entity(tableName = "Notes")
data class RoomNote(@PrimaryKey val id : Long, val date : String, val text : String, val imagePath : String) {

    // Converter
    companion object{
        internal fun toNoteRoom(note : Note) =
            RoomNote(note.Id, note.Date.toString(), note.Text, note.DrawableRes)
    }
}