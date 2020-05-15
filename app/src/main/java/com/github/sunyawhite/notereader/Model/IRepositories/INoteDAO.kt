package com.github.sunyawhite.notereader.Model

import androidx.room.*

@Dao
interface INoteDAO {

    @Query("SELECT * FROM Notes WHERE id = :id LIMIT 1")
    suspend fun getById(id : Long) : RoomNote

    @Query("SELECT * FROM Notes")
    suspend fun getAll() : List<RoomNote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note : RoomNote)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note : RoomNote)

    @Delete
    suspend fun deleteNote(note : RoomNote)
}