package com.plcoding.daggerhiltcourse.ui.presentation.course_notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.LocalRepository
import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.data.model.Notification
import com.plcoding.daggerhiltcourse.ui.presentation.saved_course.SavedCourseEvent
import com.plcoding.daggerhiltcourse.util.DateFormatter
import com.plcoding.daggerhiltcourse.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseNotificationViewModel @Inject constructor(
    localRepository: LocalRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var course by mutableStateOf<Course?>(null)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var location by mutableStateOf("")
        private set
    var startTime by mutableStateOf("")
        private set
    var endTime by mutableStateOf("")
        private set
    var instructor by mutableStateOf("")
        private set
    var imageUrl by mutableStateOf("")
        private set

    var isNotifiableInSevenDays by mutableStateOf(false)
    var isNotifiableInTwoDays by mutableStateOf(false)


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val courseId = savedStateHandle.get<Int>("courseId")!!
        if (courseId != -1) {
            viewModelScope.launch {
                localRepository.getCourseById(courseId)?.let { course ->
                    title = course.title
                    description = course.description
                    location = course.description
                    startTime = course.description
                    endTime = course.description
                    instructor = course.instructor
                    imageUrl = course.imageUrl
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
                        message = "Konferencija pocinje za 7 dana"
                    )
                    item.let(event.scheduler::schedule)
                }
                if (isNotifiableInTwoDays) {
                    val date = DateFormatter.dateToLocalTime(course!!.startTime).minusDays(2)
                    val item = Notification(
                        time = date,
                        message = "Konferencija pocinje za 2 dana"
                    )
                    item.let(event.scheduler::schedule)
                }
                sendUiEvent(UiEvent.PopBackStack)
            }
            is CourseNotificationsEvent.OnSevenDaysNotificationClick -> {
                sendUiEvent(UiEvent.UpdateSevenDaysNotificationCheckbox(event.isNotify))
            }
            is CourseNotificationsEvent.OnTwoDaysNotificationClick -> {
                sendUiEvent(UiEvent.UpdateTwoDaysNotificationCheckbox(event.isNotify))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}