package com.plcoding.daggerhiltcourse.ui.presentation.course_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.plcoding.daggerhiltcourse.data.model.remote.responses.CourseJSON
import com.plcoding.daggerhiltcourse.data.model.remote.responses.CourseWithSpeakersJSON
import com.plcoding.daggerhiltcourse.util.AlarmScheduler
import com.plcoding.daggerhiltcourse.util.UiEvent
@Composable
fun CourseDetailsScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    scheduler: AlarmScheduler,
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
            CourseItem(courseJSON = courseJSON, viewModel = viewModel, scheduler = scheduler)
        }
    }
}
@Composable
fun CourseItem(
    courseJSON: CourseWithSpeakersJSON,
    viewModel: CourseDetailsViewModel,
    scheduler: AlarmScheduler,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Box(
                modifier = Modifier.fillParentMaxSize()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    elevation = 4.dp,
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
//                                .fillMaxSize()
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp),
                                text = courseJSON.title,
                                color = MaterialTheme.colors.primary,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                )
                            )
                            Row(
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Text(
                                    text = courseJSON.location + ", ",
                                    color = MaterialTheme.colors.primary,
                                )
                                Text(
                                    text = "${courseJSON.startTime.split("T")[1].substring(0,5)} - ${courseJSON.endTime.split("T")[1].substring(0,5)}",
                                    color = MaterialTheme.colors.primary,
                                )
                            }
                            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 32.dp)
                                    .height(screenHeight * 0.3f),
                                text = courseJSON.description,
                                color = MaterialTheme.colors.primary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                        Column(
                            modifier = Modifier
                                .padding(all = 16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom,
                        ) {
                            if (courseJSON.speakers.isNotEmpty()) {
                                Column {
                                    courseJSON.speakers.forEach { speaker ->
                                        Row(
                                            modifier = Modifier.padding(vertical = 4.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.Bottom,
                                        ) {
                                            Image(
                                                painter = rememberAsyncImagePainter(speaker.imageUrl),
                                                contentDescription = speaker.name,
                                                contentScale = ContentScale.Fit,
                                                modifier = Modifier
                                                    .size(64.dp)
                                                    .clip(CircleShape)
                                                    .clickable {
                                                        viewModel.onEvent(
                                                            CourseDetailsEvent.OnSpeakerClick(
                                                                speaker.id
                                                            )
                                                        )
                                                    }
                                            )
                                            Text(
                                                text = speaker.name + ", ${speaker.title}",
                                                color = MaterialTheme.colors.primary,
                                                style = TextStyle(
                                                    fontStyle = FontStyle.Italic,
                                                ),
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier
                                                    .padding(start = 16.dp)
                                                    .align(Alignment.CenterVertically)
                                                    .clickable {
                                                        viewModel.onEvent(
                                                            CourseDetailsEvent.OnSpeakerClick(
                                                                speaker.id
                                                            )
                                                        )
                                                    }
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
                                        viewModel.onEvent(CourseDetailsEvent.OnSaveClick(course, courseJSON.speakers, scheduler))
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(64.dp)
                                        .padding(top = 8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = MaterialTheme.colors.primary,
                                        contentColor = MaterialTheme.colors.onPrimary
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                ) {
                                    Text(
                                        text = "Sacuvaj u Moj Planer",
                                        color = MaterialTheme.colors.onPrimary,
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}