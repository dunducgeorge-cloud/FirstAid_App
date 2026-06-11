package com.proiect.firstaidapp

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.OnConflictStrategy

@Dao
interface ProfilDao {

    @Query("SELECT * FROM ProfilMedical WHERE ownerEmail = :email")
    fun getProfilesForUser(email: String): List<ProfilMedical>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfil(profil: ProfilMedical)

    @Delete
    suspend fun deleteProfil(profil: ProfilMedical)
}
