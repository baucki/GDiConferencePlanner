package com.plcoding.daggerhiltcourse.data.datasource.local.repository.speaker

import com.plcoding.daggerhiltcourse.data.datasource.local.dao.SpeakerDao
import com.plcoding.daggerhiltcourse.data.model.local.entities.Speaker

class SpeakerRepositoryImpl(
    private val speakerDao: SpeakerDao
): SpeakerRepository {
    override suspend fun insertSpeaker(speaker: Speaker) {
        speakerDao.insertSpeaker(speaker)
    }
}