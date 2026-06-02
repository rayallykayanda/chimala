package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM chimala_events ORDER BY date ASC, timestamp DESC")
    fun getAllEvents(): Flow<List<ChimalaEvent>>

    @Query("SELECT * FROM chimala_events WHERE type = :type ORDER BY timestamp DESC")
    fun getEventsByType(type: String): Flow<List<ChimalaEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: ChimalaEvent)

    @Delete
    suspend fun deleteEvent(event: ChimalaEvent)

    @Query("UPDATE chimala_events SET likes = likes + 1 WHERE id = :id")
    suspend fun incrementLikes(id: Int)
}
