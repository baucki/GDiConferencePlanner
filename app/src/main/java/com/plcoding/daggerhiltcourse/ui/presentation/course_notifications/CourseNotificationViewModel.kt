package com.plcoding.daggerhiltcourse.ui.presentation.course_notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.course.RemoteRepository
import com.plcoding.daggerhiltcourse.data.model.CourseWithSpeakersJSON
import com.plcoding.daggerhiltcourse.data.model.Notification
import com.plcoding.daggerhiltcourse.util.DateFormatter
import com.plcoding.daggerhiltcourse.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseNotificationViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var course by mutableStateOf<CourseWithSpeakersJSON?>(null)
        private set

    var isNotifiableInSevenDays by mutableStateOf(false)
    var isNotifiableInTwoDays by mutableStateOf(false)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val courseId = savedStateHandle.get<Int>("courseId")!!
        if (courseId != -1) {
            viewModelScope.launch {
                remoteRepository.fetchCourseById(courseId)?.let { course ->
                    this@CourseNotificationViewModel.course = course
                }
            }
        }
    }
    fun onEvent(event: CourseNotificationsEvent) {
        when (event) {
            is CourseNotificationsEvent.OnConfirmClick -> {
                if (isNotifiableInSevenDays) {
                    val date = DateFormatter.dateToLocalTime(course!!.startTime).minusDays(7)
                    val item = Notification(
                        time = date,
                        message = "seven days"
                    )
                    item.let(event.scheduler::schedule)
                }
                if (isNotifiableInTwoDays) {
                    val date = DateFormatter.dateToLocalTime(course!!.startTime).minusDays(2)
                    val item = Notification(
                        time = date,
                        message = "two days"
                    )
                    item.let(event.scheduler::schedule)
                }
                sendUiEvent(UiEvent.PopBackStack)
            }
            is CourseNotificationsEvent.OnSevenDaysNotificationClick -> {
                isNotifiableInSevenDays = event.isNotify
            }
            is CourseNotificationsEvent.OnTwoDaysNotificationClick -> {
                isNotifiableInTwoDays = event.isNotify
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}