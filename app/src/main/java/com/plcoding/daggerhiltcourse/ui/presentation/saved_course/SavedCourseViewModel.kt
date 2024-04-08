package com.plcoding.daggerhiltcourse.ui.presentation.saved_course

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.course.LocalRepository
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.feedback.FeedbackRepository
import com.plcoding.daggerhiltcourse.data.model.remote.responses.CourseJSON
import com.plcoding.daggerhiltcourse.data.model.local.CourseWithSpeakers
import com.plcoding.daggerhiltcourse.data.model.remote.requests.Feedback
import com.plcoding.daggerhiltcourse.util.handlers.DateFormatter
import com.plcoding.daggerhiltcourse.util.Routes
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
    private val feedbackRepository: FeedbackRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var course by mutableStateOf<CourseWithSpeakers?>(null)
        private set

    var isFinished by mutableStateOf(false)
    var showDeleteDialog by mutableStateOf(false)
    var showFeedbackDialog by mutableStateOf(false)

    var feedbackText by mutableStateOf("")
    var selectedRating by mutableStateOf(0)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    init {
        val courseId = savedStateHandle.get<Int>("courseId")!!
        if (courseId != -1) {
            viewModelScope.launch {
                localRepository.getCourseById(courseId)?.let { course ->
                    this@SavedCourseViewModel.course = course
                    isFinished = LocalDateTime.now() > DateFormatter.dateToLocalTime(course.course.startTime)
                }
            }
        }
    }
    fun onEvent(event: SavedCourseEvent) {
        when (event) {
            is SavedCourseEvent.OnDeleteCourseClick -> {
                sendUiEvent(UiEvent.ShowDeleteDialog(true))
            }
            is SavedCourseEvent.OnDeleteConfirmClick -> {
                viewModelScope.launch {
                    localRepository.deleteCourseWithSpeakers(course!!.course)
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
            is SavedCourseEvent.OnDeleteDismissClick -> {
                sendUiEvent(UiEvent.ShowDeleteDialog(false))
            }
            is SavedCourseEvent.OnFeedbackDismissClick -> {
                sendUiEvent(UiEvent.ShowFeedbackDialog(false))
            }
            is SavedCourseEvent.OnFeedbackClick -> {
                sendUiEvent(UiEvent.ShowFeedbackDialog(true))
            }
            is SavedCourseEvent.OnFeedbackRatingClick -> {
                selectedRating = event.rating
            }
            is SavedCourseEvent.OnFeedbackTextChangeClick -> {
                feedbackText = event.value
            }
            is SavedCourseEvent.OnFeedbackSubmitClick -> {
                viewModelScope.launch {
                    if (event.rating != 0) {
                        var ratingString = ""
                        when (event.rating) {
                            1 -> {
                                ratingString = "bad"
                            }
                            2 -> {
                                ratingString = "neutral"
                            }
                            3 -> {
                                ratingString = "good"
                            }
                        }
                        val courseJSON = CourseJSON(
                            course!!.course.title,
                            course!!.course.description,
                            course!!.course.location,
                            course!!.course.startTime,
                            course!!.course.endTime,
                            course!!.course.courseId
                        )
                        feedbackRepository.addFeedback(Feedback(ratingString, event.message, courseJSON))
                        sendUiEvent(UiEvent.ShowFeedbackDialog(false))
                    }
                }
            }
            is SavedCourseEvent.OnSpeakerClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.SPEAKER_DETAILS + "?speakerId=${event.speakerId}"))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}