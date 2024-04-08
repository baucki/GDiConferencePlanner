package com.plcoding.daggerhiltcourse.data.datasource.remote.repository.feedback

import com.plcoding.daggerhiltcourse.data.model.remote.requests.Feedback

interface FeedbackRepository {
    suspend fun addFeedback(feedback: Feedback): Feedback?
}