package com.plcoding.daggerhiltcourse.ui.presentation.course_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.data.model.CourseJSON
import com.plcoding.daggerhiltcourse.data.model.CourseWithSpeakersJSON
import com.plcoding.daggerhiltcourse.data.model.Speaker
import com.plcoding.daggerhiltcourse.util.UiEvent
@Composable
fun CourseDetailsScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: CourseDetailsViewModel = hiltViewModel()
) {
    val courseJSON = viewModel.courseJSON

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        if (courseJSON != null) {
            CourseItem(courseJSON = courseJSON, viewModel = viewModel)
        }
    }
}
@Composable
fun CourseItem(courseJSON: CourseWithSpeakersJSON, viewModel: CourseDetailsViewModel) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp,
        color = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = courseJSON.title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                )
            )
            Row(
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(text = courseJSON.location + ", ")
                Text(text = "${courseJSON.startTime.split("T")[1].substring(0,5)} - ${courseJSON.endTime.split("T")[1].substring(0,5)}")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 32.dp)
                    .height(screenHeight * 0.3f),
                text = courseJSON.description
            )
            Spacer(modifier = Modifier.height(2.dp))
            if (courseJSON.speakers.isNotEmpty()) {
                Column {
                    courseJSON.speakers.forEach { speaker ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier.padding(all = 4.dp)
                        ) {
                            AsyncImage(
                                model = speaker.imageUrl,
                                contentDescription = speaker.name,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clickable { viewModel.onEvent(CourseDetailsEvent.OnSpeakerClick(speaker.id)) }
                            )
                            Text(
                                text = speaker.name + ", ${speaker.title}",
                                style = TextStyle(
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 16.sp
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .clickable { viewModel.onEvent(CourseDetailsEvent.OnSpeakerClick(speaker.id)) }
                            )
                        }
                    }
                }
                Button(
                    onClick = {
                        val course = CourseJSON(
                            courseJSON.title,
                            courseJSON.description,
                            courseJSON.location,
                            courseJSON.startTime,
                            courseJSON.endTime,
                            courseJSON.id
                        )
                        viewModel.onEvent(CourseDetailsEvent.OnSaveClick(course, courseJSON.speakers))
                    },
                    modifier = Modifier
                        .height(64.dp)
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),

                    ) {
                    Text(
                        text = "Save",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
        }
    }
}