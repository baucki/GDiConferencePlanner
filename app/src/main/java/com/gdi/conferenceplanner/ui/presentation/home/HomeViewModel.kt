package com.gdi.conferenceplanner.ui.presentation.home

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdi.conferenceplanner.data.datasource.remote.repository.course.RemoteRepository
import com.gdi.conferenceplanner.data.model.remote.responses.CourseWithSpeakersJSON
import com.gdi.conferenceplanner.util.handlers.DateFormatter
import com.gdi.conferenceplanner.util.Routes
import com.gdi.conferenceplanner.util.UiEvent
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteRepository: Lazy<RemoteRepository>
): ViewModel() {

    var course by mutableStateOf<CourseWithSpeakersJSON?>(null)

    private var _tabsContent = MutableStateFlow<Map<String, List<CourseWithSpeakersJSON>>>(emptyMap())
    var tabsContent: StateFlow<Map<String, List<CourseWithSpeakersJSON>>> = _tabsContent

    private val _courses = MutableStateFlow<List<CourseWithSpeakersJSON>>(emptyList())
    val courses: StateFlow<List<CourseWithSpeakersJSON>> = _courses

    var selectedTabIndex by mutableStateOf(0)
    var tabsCourses by mutableStateOf<List<CourseWithSpeakersJSON>>(emptyList())

    var searchText by mutableStateOf("")
    val filteredData by derivedStateOf { filterData(tabsCourses.sortedBy { it.startTime }, searchText) }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    var isLoading by mutableStateOf(false)

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
    private fun filterData(data: List<CourseWithSpeakersJSON>, query: String): List<CourseWithSpeakersJSON> {
        return if (query.isEmpty()) {
            data
        } else {
            data.filter {course ->
                course.title.contains(query, ignoreCase = true)
                    || course.startTime.contains(query, ignoreCase = true)
                    || course.location.contains(query, ignoreCase = true)
                    || course.speakers.any { speaker ->
                        speaker.name.contains(query, ignoreCase = true)
                        || speaker.title.contains(query, ignoreCase = true)
                }
            }
        }
    }
    private fun sortTabsData() {
        viewModelScope.launch {
            courses.collect { coursesList ->
                val tempTabsContent = mutableMapOf<String, List<CourseWithSpeakersJSON>>()
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
        viewModelScope.launch {
            try {
                isLoading = true
                _courses.value = remoteRepository.get().fetchAllCourses()
                sortTabsData()
                isLoading = false
            } catch (e: IOException) {
                isLoading = false
                _errorMessage.value = "Nema interneta"
            } catch (e: Exception) {
                isLoading = false
                _errorMessage.value = "Doslo je do greske"
            }
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}