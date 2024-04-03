package com.plcoding.daggerhiltcourse.data.datasource.remote.repository.speaker

import com.plcoding.daggerhiltcourse.data.model.SpeakerJSON

interface SpeakerRepository {
    suspend fun fetchSpeakerById(id: Long): SpeakerJSON?
}