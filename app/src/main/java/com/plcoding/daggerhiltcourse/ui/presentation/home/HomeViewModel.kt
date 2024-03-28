package com.plcoding.daggerhiltcourse.ui.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.local.repository.LocalRepository
import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.RemoteRepository
import com.plcoding.daggerhiltcourse.util.DateFormatter
import com.plcoding.daggerhiltcourse.util.Routes
import com.plcoding.daggerhiltcourse.util.UiEvent
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteRepository: Lazy<RemoteRepository>,
    private val localRepository: LocalRepository,
    savedStateHandle: SavedStateHandle
    ): ViewModel() {

    var course by mutableStateOf<Course?>(null)

    private var _tabsContent = MutableStateFlow<Map<String, List<Course>>>(emptyMap())
    var tabsContent: StateFlow<Map<String, List<Course>>> = _tabsContent

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: HomeEvent) {
        when (event){
            is HomeEvent.OnCourseClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.COURSE_DETAILS + "?courseId=${event.course.id}"))
            }
            else -> Unit
        }
    }
    private fun sortTabsData() {
        viewModelScope.launch {
            courses.collect { coursesList ->
                val tempTabsContent = mutableMapOf<String, List<Course>>()
                for (course in coursesList) {
                    val date = DateFormatter.formatDate(course.startTime.split("T")[0])
                    if (!tempTabsContent.containsKey(date)) {
                        tempTabsContent[date] = mutableListOf(course)
                    } else {
                        val currentList = tempTabsContent[date]!!.toMutableList()
                        currentList.add(course)
                        tempTabsContent[date] = currentList.toList()
                    }
                }
                _tabsContent.value = tempTabsContent.toMap()
            }
        }
    }
    suspend fun fetchAllCourses() {
        _courses.value = remoteRepository.get().fetchAllCourses()
        sortTabsData()
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}