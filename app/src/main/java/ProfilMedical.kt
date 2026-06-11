package com.proiect.firstaidapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProfilMedical(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nume: String,
    val grupa: String,
    val alergii: String,
    val ownerEmail: String
)


