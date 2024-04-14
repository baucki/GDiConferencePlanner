package com.plcoding.daggerhiltcourse.ui.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val selectedTabKey = tabsContent.keys.elementAtOrNull(viewModel.selectedTabIndex)
    val selectedTabCourses = selectedTabKey?.let { tabsContent[it] } ?: emptyList()
    viewModel.tabsCourses = selectedTabCourses

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
                selectedTabIndex = viewModel.selectedTabIndex,
                backgroundColor = MaterialTheme.colors.background
            ) {
                tabsContent.keys.forEachIndexed { index, key ->
                    Tab(
                        text = { Text(text = key) },
                        selected = viewModel.selectedTabIndex == index,
                        onClick =  { viewModel.selectedTabIndex = index }
                    )
                }
            }
            SearchComponent(viewModel)
            Divider(color = MaterialTheme.colors.primary, thickness = 2.dp)
            LazyColumn {
                if (viewModel.searchText != "") {
                    items(viewModel.filteredData) { course ->
                        CourseItemFiltered(
                            viewModel,
                            course = course,
                            modifier = Modifier.clickable { viewModel.onEvent(HomeEvent.OnCourseClick(course)) }
                        )
                        startTime = course.startTime
                    }
                } else {
                    items(selectedTabCourses.sortedBy { it.startTime }) { course ->
                        CourseItem(
                            viewModel,
                            course = course,
                            modifier = Modifier.clickable { viewModel.onEvent(HomeEvent.OnCourseClick(course)) }
                        )
                        startTime = course.startTime
                    }
                }
            }
        }
    }
}


@Composable
fun SearchComponent(viewModel: HomeViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp, start = 16.dp, top = 8.dp, bottom = 8.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colors.surface,
    ) {
        TextField(
            value = viewModel.searchText,
            onValueChange = { viewModel.searchText = it },
            placeholder = { Text("Pretraga", style = TextStyle(color = Color.Gray)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                cursorColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = MaterialTheme.colors.primary
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {  }),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) // Add search icon
            },
            trailingIcon = {
                if (viewModel.searchText.isNotEmpty()) {
                    IconButton(
                        onClick = { viewModel.searchText = "" },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Gray) // Add clear icon
                    }
                }
            }
        )
    }
}