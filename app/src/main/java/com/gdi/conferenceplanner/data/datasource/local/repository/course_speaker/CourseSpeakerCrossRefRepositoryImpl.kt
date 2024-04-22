package com.gdi.conferenceplanner.data.datasource.local.repository.course_speaker

import com.gdi.conferenceplanner.data.datasource.local.dao.CourseSpeakerCrossRefDao
import com.gdi.conferenceplanner.data.model.local.entities.CourseSpeakerCrossRef

class CourseSpeakerCrossRefRepositoryImpl(
    private val crossRefDao: CourseSpeakerCrossRefDao
): CourseSpeakerCrossRefRepository {
    override suspend fun insertCrossRef(crossRef: CourseSpeakerCrossRef) {
        crossRefDao.insertCrossRef(crossRef)
    }
}