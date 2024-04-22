package com.gdi.conferenceplanner.ui.presentation.my_agenda

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdi.conferenceplanner.data.datasource.local.repository.course.LocalRepository
import com.gdi.conferenceplanner.util.handlers.DataStoreHandler
import com.gdi.conferenceplanner.util.Routes
import com.gdi.conferenceplanner.util.UiEvent
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
    var isLoggedIn = mutableStateOf(false)
    var isDataFetched = mutableStateOf(false)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    init {
        viewModelScope.launch {
            val userInfo = DataStoreHandler.read()
            if (userInfo != "") {
                isDataFetched.value = true
                isLoggedIn.value = true
            } else {
                isLoggedIn.value = false
            }
        }
    }
    fun onEvent(event: MyAgendaEvent) {
        when (event) {
            is MyAgendaEvent.OnCourseClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.SAVED_COURSE + "?courseId=${event.course.courseId}") )
            }
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}