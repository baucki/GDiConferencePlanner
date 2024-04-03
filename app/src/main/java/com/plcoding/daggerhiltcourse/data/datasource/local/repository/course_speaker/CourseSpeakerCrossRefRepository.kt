package com.plcoding.daggerhiltcourse.data.datasource.local.repository.course_speaker

import com.plcoding.daggerhiltcourse.data.model.CourseSpeakerCrossRef

interface CourseSpeakerCrossRefRepository {

    suspend fun insertCrossRef(crossRef: CourseSpeakerCrossRef)

}