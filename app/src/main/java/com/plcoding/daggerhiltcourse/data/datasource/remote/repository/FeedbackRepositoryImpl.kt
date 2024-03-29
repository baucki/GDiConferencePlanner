package com.plcoding.daggerhiltcourse.data.datasource.remote.repository

import com.plcoding.daggerhiltcourse.data.datasource.remote.MyApi
import com.plcoding.daggerhiltcourse.data.model.Feedback
import javax.inject.Inject

class FeedbackRepositoryImpl @Inject constructor(
    private val api: MyApi
): FeedbackRepository {
    override suspend fun addFeedback(feedback: Feedback): Feedback? {
        return api.addFeedback(feedback)
    }
}