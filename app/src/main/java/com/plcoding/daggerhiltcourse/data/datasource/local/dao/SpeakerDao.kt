package com.plcoding.daggerhiltcourse.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.plcoding.daggerhiltcourse.data.model.local.entities.Speaker

@Dao
interface SpeakerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeaker(speaker: Speaker)

}