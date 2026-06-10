package com.proiect.firstaidapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProfilMedical::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profilDao(): ProfilDao
}


