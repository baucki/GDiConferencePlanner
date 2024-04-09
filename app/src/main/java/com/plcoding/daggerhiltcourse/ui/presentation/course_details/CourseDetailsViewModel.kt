package com.plcoding.daggerhiltcourse.ui.presentation.course_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.course_speaker.CourseSpeakerCrossRefRepository
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.course.LocalRepository
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.speaker.SpeakerRepository
import com.plcoding.daggerhiltcourse.data.model.local.entities.Course
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.course.RemoteRepository
import com.plcoding.daggerhiltcourse.data.model.remote.responses.CourseJSON
import com.plcoding.daggerhiltcourse.data.model.local.entities.CourseSpeakerCrossRef
import com.plcoding.daggerhiltcourse.data.model.remote.responses.CourseWithSpeakersJSON
import com.plcoding.daggerhiltcourse.data.model.Notification
import com.plcoding.daggerhiltcourse.data.model.local.entities.Speaker
import com.plcoding.daggerhiltcourse.data.model.remote.responses.SpeakerJSON
import com.plcoding.daggerhiltcourse.util.handlers.DataStoreHandler
import com.plcoding.daggerhiltcourse.util.Routes
import com.plcoding.daggerhiltcourse.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CourseDetailsViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val speakerRepository: SpeakerRepository,
    private val crossRefRepository: CourseSpeakerCrossRefRepository,
    private val remoteRepository: RemoteRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var courseJSON by mutableStateOf<CourseWithSpeakersJSON?>(null)
        private set
    var course by mutableStateOf<Course?>(null)
    var speaker by mutableStateOf<Speaker?>(null)
    var courseSpeakerCrossRef by mutableStateOf<CourseSpeakerCrossRef?>(null)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val courseId = savedStateHandle.get<Int>("courseId")!!
        if (courseId != -1) {
            viewModelScope.launch {
                remoteRepository.fetchCourseById(courseId)?.let { course ->
                    this@CourseDetailsViewModel.courseJSON = course
                }
            }
        }
    }

    fun onEvent(event: CourseDetailsEvent) {
        when (event) {
            is CourseDetailsEvent.OnSaveClick -> {
                viewModelScope.launch {
                    val flow = DataStoreHandler.read()
                    flow.collect { userInfo ->
                        if (userInfo == "") {
                            sendUiEvent(UiEvent.PopBackStack)
                            sendUiEvent(UiEvent.Navigate(Routes.LOGIN))
                        } else {
                            if (courseJSON != null) {
                                course = createCourse(event.courseJSON)
                                localRepository.insertCourse(course!!)
                                event.speakersJSON.forEach {
                                    speaker = createSpeaker(it)
                                    speakerRepository.insertSpeaker(speaker!!)
                                    courseSpeakerCrossRef = CourseSpeakerCrossRef(course!!.courseId, speaker!!.speakerId)
                                    crossRefRepository.insertCrossRef(courseSpeakerCrossRef!!)
                                }
                                val now = LocalDateTime.now()
                                val messageNotification = Notification(
                                    time = now,
                                    message = "now"
                                )
                                messageNotification.let(event.scheduler::schedule)

                                sendUiEvent(UiEvent.Navigate(Routes.COURSE_NOTIFICATIONS + "?courseId=${course!!.courseId}"))
                            }
                        }
                    }
                }
            }
            is CourseDetailsEvent.OnSpeakerClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.SPEAKER_DETAILS + "?speakerId=${event.speakerId}"))
            }
        }
    }
    private fun createCourse(courseJSON: CourseJSON): Course {
        return Course(
            courseJSON.title,
            courseJSON.description,
            courseJSON.location,
            courseJSON.startTime,
            courseJSON.endTime,
            courseJSON.id
        )
    }
    private fun createSpeaker(speakerJSON: SpeakerJSON): Speaker {
        return Speaker(
            speakerJSON.imageUrl,
            speakerJSON.name,
            speakerJSON.title,
            speakerJSON.biography,
            speakerJSON.id
        )
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}