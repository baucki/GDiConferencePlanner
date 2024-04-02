package com.plcoding.daggerhiltcourse.data.datasource.remote.repository

import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.SpeakerRepository
import com.plcoding.daggerhiltcourse.data.datasource.remote.MyApi
import com.plcoding.daggerhiltcourse.data.model.Speaker
import com.plcoding.daggerhiltcourse.data.model.SpeakerJSON
import javax.inject.Inject

class SpeakerRepositoryImpl @Inject constructor(
    private val api: MyApi
): SpeakerRepository {
    override suspend fun fetchSpeakerById(id: Long): SpeakerJSON {
        return api.fetchSpeakersById(id.toString())!!
    }

}