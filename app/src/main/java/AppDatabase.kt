package com.proiect.firstaidapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProfilMedical::class, PostEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profilDao(): ProfilDao
    abstract fun postDao(): PostDao
}
