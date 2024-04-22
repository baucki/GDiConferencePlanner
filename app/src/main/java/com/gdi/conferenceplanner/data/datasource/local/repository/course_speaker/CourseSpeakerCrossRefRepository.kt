package com.gdi.conferenceplanner.data.datasource.local.repository.course_speaker

import com.gdi.conferenceplanner.data.model.local.entities.CourseSpeakerCrossRef

interface CourseSpeakerCrossRefRepository {

    suspend fun insertCrossRef(crossRef: CourseSpeakerCrossRef)

}