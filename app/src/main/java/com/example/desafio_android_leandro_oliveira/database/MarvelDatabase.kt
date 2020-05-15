package com.example.desafio_android_leandro_oliveira.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MarvelTable::class], version = 1, exportSchema = false)
abstract class MarvelDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "MarvelDb"

        fun get(context: Context): MarvelDatabase {
            return Room.databaseBuilder(context.applicationContext, MarvelDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun marvelDao(): MarvelTableDao
}