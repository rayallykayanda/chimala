package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "media_items")
data class MediaItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val artist: String,
    val type: String, // "SONG" or "VIDEO"
    val url: String = "",
    val category: String = "Bongo Flava",
    val duration: String = "3:30",
    val views: Int = 0,
    val likes: Int = 0,
    val lyrics: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
