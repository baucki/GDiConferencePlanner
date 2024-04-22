package com.gdi.conferenceplanner.data.datasource.remote.repository.speaker

import com.gdi.conferenceplanner.data.model.remote.responses.SpeakerJSON

interface SpeakerRepository {
    suspend fun fetchSpeakerById(id: Long): SpeakerJSON?
}