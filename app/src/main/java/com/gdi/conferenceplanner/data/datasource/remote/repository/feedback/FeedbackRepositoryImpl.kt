package com.gdi.conferenceplanner.data.datasource.remote.repository.feedback

import com.gdi.conferenceplanner.data.datasource.remote.MyApi
import com.gdi.conferenceplanner.data.model.remote.requests.Feedback
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(
    private val api: MyApi
): FeedbackRepository {
    override suspend fun addFeedback(feedback: Feedback): Feedback? {
        return api.addFeedback(feedback)
    }
}