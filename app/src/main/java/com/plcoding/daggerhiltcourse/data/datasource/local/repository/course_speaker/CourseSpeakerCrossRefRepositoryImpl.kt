package com.plcoding.daggerhiltcourse.data.datasource.local.repository.course_speaker

import com.plcoding.daggerhiltcourse.data.datasource.local.dao.CourseSpeakerCrossRefDao
import com.plcoding.daggerhiltcourse.data.model.local.entities.CourseSpeakerCrossRef

class CourseSpeakerCrossRefRepositoryImpl(
    private val crossRefDao: CourseSpeakerCrossRefDao
): CourseSpeakerCrossRefRepository {
    override suspend fun insertCrossRef(crossRef: CourseSpeakerCrossRef) {
        crossRefDao.insertCrossRef(crossRef)
    }
}