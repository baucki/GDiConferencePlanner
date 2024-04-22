package com.gdi.conferenceplanner.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.gdi.conferenceplanner.data.model.local.entities.Speaker

@Dao
interface SpeakerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeaker(speaker: Speaker)

}