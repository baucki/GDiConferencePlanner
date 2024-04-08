package com.plcoding.daggerhiltcourse.ui.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.Modifier
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.daggerhiltcourse.util.UiEvent

@Composable
fun HomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    topBarState: MutableState<Boolean>,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val courses by viewModel.courses.collectAsState(initial = emptyList())
    val tabsContent by viewModel.tabsContent.collectAsState(initial = emptyMap())
    var startTime = ""
    var selectedTabIndex by remember { mutableStateOf(0) }


    LaunchedEffect(true) {
        viewModel.fetchAllCourses()
    }

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    if (courses.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        topBarState.value = true
        Column {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                backgroundColor = Color.White
            ) {
                tabsContent.keys.forEachIndexed { index, key ->
                    Tab(
                        text = { Text(text = key) },
                        selected = selectedTabIndex == index,
                        onClick =  {selectedTabIndex = index}
                    )
                }
            }
            val selectedTabKey = tabsContent.keys.elementAtOrNull(selectedTabIndex)
            val selectedTabCourses = selectedTabKey?.let { tabsContent[it] } ?: emptyList()
            val isVisibleMap: MutableMap<Long, MutableState<Boolean>> = mutableMapOf()
            val isBreakMap: MutableMap<Long, MutableState<Boolean>> = mutableMapOf()
             LazyColumn {
                items(selectedTabCourses.sortedBy { it.startTime }) { course ->
                    if (course.speakers.isEmpty()) {
                        isBreakMap[course.id] = rememberSaveable { (mutableStateOf(true)) }
                    } else {
                        isBreakMap[course.id] = rememberSaveable { (mutableStateOf(false)) }
                    }
                    if (startTime != course.startTime) {
                        isVisibleMap[course.id] = rememberSaveable { (mutableStateOf(true)) }
                    } else {
                        isVisibleMap[course.id] = rememberSaveable { (mutableStateOf(false)) }
                    }
                    CourseItem(
                        course = course,
                        isVisibleMap[course.id]!!,
                        isBreakMap[course.id]!!,
                        modifier = Modifier.clickable { viewModel.onEvent(HomeEvent.OnCourseClick(course)) }
                    )
                    startTime = course.startTime
                }
            }
        }
    }
}
