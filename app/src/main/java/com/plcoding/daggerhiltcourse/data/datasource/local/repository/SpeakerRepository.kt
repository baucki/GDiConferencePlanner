package com.plcoding.daggerhiltcourse.data.datasource.local.repository

import com.plcoding.daggerhiltcourse.data.model.Speaker

interface SpeakerRepository {
    suspend fun insertSpeaker(speaker: Speaker)
}