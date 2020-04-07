package com.github.sunyawhite.notereader.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RoomNote::class), version = 1, exportSchema = false)
abstract class SQLNoteDatabase : RoomDatabase(){

    abstract fun noteDao() : INoteDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SQLNoteDatabase? = null

        fun getDatabase(context: Context): SQLNoteDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SQLNoteDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}