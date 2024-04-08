package com.plcoding.daggerhiltcourse.data.datasource.remote.repository.speaker

import com.plcoding.daggerhiltcourse.data.model.remote.responses.SpeakerJSON

interface SpeakerRepository {
    suspend fun fetchSpeakerById(id: Long): SpeakerJSON?
}