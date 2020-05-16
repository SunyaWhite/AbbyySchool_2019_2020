package com.github.sunyawhite.notereader.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Создаем контекст для SQLLite БД. Подключаемся к базе данных
@Database(entities = arrayOf(RoomNote::class), version = 2, exportSchema = false)
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
                    "node_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}