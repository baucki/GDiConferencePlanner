package com.plcoding.daggerhiltcourse.ui.presentation.my_agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.LocalRepository
import com.plcoding.daggerhiltcourse.util.Routes
import com.plcoding.daggerhiltcourse.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAgendaViewModel @Inject constructor(
    private val localRepository: LocalRepository
): ViewModel() {
    val courses = localRepository.getCourses()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: MyAgendaEvent) {
        when (event) {
            is MyAgendaEvent.OnCourseClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.SAVED_COURSE + "?courseId=${event.course.id}") )
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}