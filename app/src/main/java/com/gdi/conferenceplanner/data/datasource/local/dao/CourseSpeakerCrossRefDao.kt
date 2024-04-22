package com.gdi.conferenceplanner.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.gdi.conferenceplanner.data.model.local.entities.CourseSpeakerCrossRef

@Dao
interface CourseSpeakerCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(crossRef: CourseSpeakerCrossRef)

}