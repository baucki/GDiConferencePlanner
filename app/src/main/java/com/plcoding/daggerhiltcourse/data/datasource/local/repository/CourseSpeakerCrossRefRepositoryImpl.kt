package com.plcoding.daggerhiltcourse.data.datasource.local.repository

import com.plcoding.daggerhiltcourse.data.datasource.local.dao.CourseSpeakerCrossRefDao
import com.plcoding.daggerhiltcourse.data.model.CourseSpeakerCrossRef

class CourseSpeakerCrossRefRepositoryImpl(
    private val crossRefDao: CourseSpeakerCrossRefDao
): CourseSpeakerCrossRefRepository {
    override suspend fun insertCrossRef(crossRef: CourseSpeakerCrossRef) {
        crossRefDao.insertCrossRef(crossRef)
    }
}