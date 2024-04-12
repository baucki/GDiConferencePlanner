package com.plcoding.daggerhiltcourse.ui.presentation.my_agenda

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.daggerhiltcourse.R
import com.plcoding.daggerhiltcourse.data.model.local.CourseWithSpeakers
import com.plcoding.daggerhiltcourse.util.handlers.DateFormatter
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
    if (viewModel.isLoggedIn.value) {
        if (courses.value.isNotEmpty()) {
            LazyColumn {
                items(courses.value.sortedBy { it.course.startTime }) { course ->
                    CourseItem(
                        modifier = Modifier.clickable {
                            viewModel.onEvent(
                                MyAgendaEvent.OnCourseClick(
                                    course.course
                                )
                            )
                        },
                        course
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_my_agenda_empty),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 64.dp),
                    text = "Moj planer je prazan",
                    textAlign = TextAlign.Center
                )
            }
        }

    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_my_agenda_no_access),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 64.dp),
                text = "Prijavite se za pristup planeru",
                textAlign = TextAlign.Center
            )
        }
    }

}
@Composable
fun CourseItem(
    modifier: Modifier = Modifier,
    course: CourseWithSpeakers,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 6.dp,
        color = Color.White,
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .width(screenWidth * 0.13f),
            ) {

                val date = DateFormatter.formatDate(course.course.startTime.split("T")[0])
                Text(text = date)
//                        Text(text = date.split(" ")[0])
//                        Text(text = date.split(" ")[1])
                Text(text = course.course.startTime.split("T")[1].substring(0, 5))

            }
            Column(modifier = Modifier.padding(all = 8.dp)) {
                Text(
                    text = course.course.title,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(text = course.course.location)
                course.speakers.forEach { speaker ->
                    Column {
                        Text(
                            text = speaker.name + ", " + speaker.title,
                            style = TextStyle(fontStyle = FontStyle.Italic)
                        )
                    }
                }
            }
        }
    }
}