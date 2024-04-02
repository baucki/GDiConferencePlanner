package com.plcoding.daggerhiltcourse.data.datasource.remote.repository

import com.plcoding.daggerhiltcourse.data.model.SpeakerJSON

interface SpeakerRepository {

    suspend fun fetchSpeakerById(id: Long): SpeakerJSON?


}