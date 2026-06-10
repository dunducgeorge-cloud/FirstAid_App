package com.proiect.firstaidapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class ProfilMedicalEntity(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val nume: String,
    val grupa: String,
    val alergii: String
)

