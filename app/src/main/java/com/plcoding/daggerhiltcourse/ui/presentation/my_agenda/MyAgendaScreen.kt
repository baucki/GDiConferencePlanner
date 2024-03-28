package com.plcoding.daggerhiltcourse.ui.presentation.my_agenda

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.daggerhiltcourse.util.DateFormatter
import com.plcoding.daggerhiltcourse.util.UiEvent

@Composable
fun MyAgendaScreen(
    viewModel: MyAgendaViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val courses = viewModel.courses.collectAsState(initial = emptyList())

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    LazyColumn {
        items(courses.value.sortedBy { it.startTime }) {course ->

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                .clickable { viewModel.onEvent(MyAgendaEvent.OnCourseClick(course)) }
                ,
                elevation = 6.dp,
                color = Color.White,
                shape = RoundedCornerShape(16.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .width(screenWidth * 0.13f),
                    ) {

                        val date = DateFormatter.formatDate(course.startTime.split("T")[0])
                        Text(text = date)
//                        Text(text = date.split(" ")[0])
//                        Text(text = date.split(" ")[1])
                        Text(text = course.startTime.split("T")[1].substring(0, 5))

                    }
                    Column(modifier = Modifier.padding(all = 8.dp)) {
                        Text(
                            text = course.title,
                            style = TextStyle(fontWeight = FontWeight.Bold)
                        )
                        Text(text = course.location)
                        Text(
                            text = course.instructor,
                            style = TextStyle(fontStyle = FontStyle.Italic)
                        )
                    }
                }
            }
        }
    }
}
