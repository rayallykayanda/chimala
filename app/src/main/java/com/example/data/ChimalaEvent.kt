package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chimala_events")
data class ChimalaEvent(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val price: String = "Bure",
    val type: String = "EVENT", // "EVENT" or "NEWS"
    val organizer: String = "Chimala Community",
    val likes: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
