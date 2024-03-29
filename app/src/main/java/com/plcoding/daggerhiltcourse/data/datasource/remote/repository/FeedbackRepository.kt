package com.plcoding.daggerhiltcourse.data.datasource.remote.repository

import com.plcoding.daggerhiltcourse.data.model.Feedback

interface FeedbackRepository {

    suspend fun addFeedback(feedback: Feedback): Feedback?

}