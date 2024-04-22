package com.gdi.conferenceplanner.data.datasource.remote.repository.feedback

import com.gdi.conferenceplanner.data.model.remote.requests.Feedback

interface FeedbackRepository {
    suspend fun addFeedback(feedback: Feedback): Feedback?
}