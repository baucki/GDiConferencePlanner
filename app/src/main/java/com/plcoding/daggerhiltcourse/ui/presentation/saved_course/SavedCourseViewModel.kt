package com.plcoding.daggerhiltcourse.ui.presentation.saved_course

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.LocalRepository
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.RemoteRepository
import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedCourseViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
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
    var showDialog by mutableStateOf(false)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val courseId = savedStateHandle.get<Int>("courseId")!!
        if (courseId != -1) {
            viewModelScope.launch {
                remoteRepository.fetchCourseById(courseId)?.let { course ->
                    title = course.title
                    description = course.description
                    location = course.description
                    startTime = course.description
                    endTime = course.description
                    instructor = course.instructor
                    imageUrl = course.imageUrl
                    this@SavedCourseViewModel.course = course
                }
            }
        }
    }

    fun onEvent(event: SavedCourseEvent) {
        when (event) {
            is SavedCourseEvent.OnDeleteCourseClick -> {
                    sendUiEvent(UiEvent.ShowDialog(true))
                    sendUiEvent(UiEvent.ShowDialog(true))
            }
            is SavedCourseEvent.OnDeleteConfirmClick -> {
                viewModelScope.launch {
                    localRepository.deleteCourse(course!!)
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
            is SavedCourseEvent.OnDeleteDismissClick -> {
                    sendUiEvent(UiEvent.ShowDialog(false))
                    sendUiEvent(UiEvent.ShowDialog(false))
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