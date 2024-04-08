package com.plcoding.daggerhiltcourse.ui.presentation.my_agenda

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.course.LocalRepository
import com.plcoding.daggerhiltcourse.util.handlers.DataStoreHandler
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
    var isLoggedIn = mutableStateOf(false)
    var isDataFetched = mutableStateOf(false)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    init {
        viewModelScope.launch {
            val flow = DataStoreHandler.read()
            flow.collect { userInfo ->
                if (userInfo != "") {
                    isDataFetched.value = true
                    isLoggedIn.value = true
                } else {
                    isLoggedIn.value = false
                }
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