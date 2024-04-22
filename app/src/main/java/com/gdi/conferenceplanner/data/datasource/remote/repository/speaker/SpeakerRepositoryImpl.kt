package com.gdi.conferenceplanner.data.datasource.remote.repository.speaker

import com.gdi.conferenceplanner.data.datasource.remote.MyApi
import com.gdi.conferenceplanner.data.model.remote.responses.SpeakerJSON
import javax.inject.Inject

class SpeakerRepositoryImpl @Inject constructor(
    private val api: MyApi
): SpeakerRepository {
    override suspend fun fetchSpeakerById(id: Long): SpeakerJSON {
        return api.fetchSpeakersById(id.toString())!!
    }

}