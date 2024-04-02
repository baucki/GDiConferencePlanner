package com.plcoding.daggerhiltcourse.data.datasource.local.repository

import com.plcoding.daggerhiltcourse.data.datasource.local.dao.SpeakerDao
import com.plcoding.daggerhiltcourse.data.model.Speaker

class SpeakerRepositoryImpl(
    private val speakerDao: SpeakerDao
): SpeakerRepository {
    override suspend fun insertSpeaker(speaker: Speaker) {
        speakerDao.insertSpeaker(speaker)
    }
}