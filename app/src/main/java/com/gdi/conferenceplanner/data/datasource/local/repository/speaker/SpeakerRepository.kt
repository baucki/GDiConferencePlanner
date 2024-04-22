package com.gdi.conferenceplanner.data.datasource.local.repository.speaker

import com.gdi.conferenceplanner.data.model.local.entities.Speaker

interface SpeakerRepository {
    suspend fun insertSpeaker(speaker: Speaker)
}