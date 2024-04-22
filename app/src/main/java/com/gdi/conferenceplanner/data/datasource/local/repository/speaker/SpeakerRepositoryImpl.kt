package com.gdi.conferenceplanner.data.datasource.local.repository.speaker

import com.gdi.conferenceplanner.data.datasource.local.dao.SpeakerDao
import com.gdi.conferenceplanner.data.model.local.entities.Speaker

class SpeakerRepositoryImpl(
    private val speakerDao: SpeakerDao
): SpeakerRepository {
    override suspend fun insertSpeaker(speaker: Speaker) {
        speakerDao.insertSpeaker(speaker)
    }
}