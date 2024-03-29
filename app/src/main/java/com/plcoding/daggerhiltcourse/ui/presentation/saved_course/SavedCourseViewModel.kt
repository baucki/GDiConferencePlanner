package com.plcoding.daggerhiltcourse.ui.presentation.saved_course

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.LocalRepository
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.FeedbackRepository
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.RemoteRepository
import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.data.model.Feedback
import com.plcoding.daggerhiltcourse.util.DateFormatter
import com.plcoding.daggerhiltcourse.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class SavedCourseViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
    private val feedbackRepository: FeedbackRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var course by mutableStateOf<Course?>(null)
        private set

    var isFinished by mutableStateOf(false)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    init {
        val courseId = savedStateHandle.get<Int>("courseId")!!
        if (courseId != -1) {
            viewModelScope.launch {
                remoteRepository.fetchCourseById(courseId)?.let { course ->
                    this@SavedCourseViewModel.course = course
                    isFinished = LocalDateTime.now() > DateFormatter.dateToLocalTime(course.startTime)
                }
            }
        }
    }
    fun onEvent(event: SavedCourseEvent) {
        when (event) {
            is SavedCourseEvent.OnDeleteCourseClick -> {
                sendUiEvent(UiEvent.ShowDeleteDialog(true))
                sendUiEvent(UiEvent.ShowDeleteDialog(true))
            }
            is SavedCourseEvent.OnDeleteConfirmClick -> {
                viewModelScope.launch {
                    localRepository.deleteCourse(course!!)
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
            is SavedCourseEvent.OnDeleteDismissClick -> {
                sendUiEvent(UiEvent.ShowDeleteDialog(false))
                sendUiEvent(UiEvent.ShowDeleteDialog(false))
            }
            is SavedCourseEvent.OnFeedbackDismissClick -> {
                sendUiEvent(UiEvent.ShowFeedbackDialog(false))
                sendUiEvent(UiEvent.ShowFeedbackDialog(false))
            }
            is SavedCourseEvent.OnFeedbackRatingClick -> {
                sendUiEvent(UiEvent.UpdateFeedbackRating(event.rating))
                sendUiEvent(UiEvent.UpdateFeedbackRating(event.rating))
                sendUiEvent(UiEvent.UpdateFeedbackRating(event.rating))
            }
            is SavedCourseEvent.OnSendFeedbackClick -> {
                viewModelScope.launch {
                    if (event.rating != 0) {
                        var ratingString = ""
                        if (event.rating==1) ratingString = "bad"
                        if (event.rating==2) ratingString = "neutral"
                        if (event.rating==3) ratingString = "good"
                        feedbackRepository.addFeedback(Feedback(ratingString, event.message, course!!))
                    }
                }
            }
            else -> Unit
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}