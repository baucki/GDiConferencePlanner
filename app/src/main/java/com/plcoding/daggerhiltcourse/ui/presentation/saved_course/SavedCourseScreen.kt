package com.plcoding.daggerhiltcourse.ui.presentation.saved_course

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.plcoding.daggerhiltcourse.R
import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.data.model.CourseWithSpeakers
import com.plcoding.daggerhiltcourse.data.model.Feedback
import com.plcoding.daggerhiltcourse.ui.presentation.course_details.CourseDetailsEvent
import com.plcoding.daggerhiltcourse.util.UiEvent

@Composable
fun SavedCourseScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: SavedCourseViewModel = hiltViewModel()
) {
    val course = viewModel.course

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowDeleteDialog -> {
                    viewModel.showDeleteDialog = event.state
                }
                is UiEvent.ShowFeedbackDialog -> {
                    viewModel.showFeedbackDialog = event.state
                }
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
            }
        }
    }

    if (course != null) {
        CourseItem(course = course, viewModel = viewModel)
    }
}
@Composable
fun CourseItem(course: CourseWithSpeakers, viewModel: SavedCourseViewModel) {
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
                text = course.course.title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                )
            )
            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = course.course.location + ", ")
                Text(text = "${course.course.startTime.split("T")[1].substring(0,5)} - ${course.course.endTime.split("T")[1].substring(0,5)}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 32.dp)
                    .height(screenHeight * 0.3f),
                text = course.course.description
            )
            Spacer(modifier = Modifier.height(2.dp))

        }
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            if (course.speakers.isNotEmpty()) {
                course.speakers.forEach { speaker ->
                    Column {
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(speaker.imageUrl),
                                contentDescription = speaker.name,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clickable {
                                        viewModel.onEvent(
                                            SavedCourseEvent.OnSpeakerClick(
                                                speaker.speakerId
                                            )
                                        )
                                    }
                            )
                            Text(
                                text = speaker.name + ", " + speaker.title,
                                style = TextStyle(
                                    fontStyle = FontStyle.Italic,
                                ),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        viewModel.onEvent(
                                            SavedCourseEvent.OnSpeakerClick(
                                                speaker.speakerId
                                            )
                                        )
                                    }
                            )
                        }
                    }
                }
                if (viewModel.isFinished) {
                    FeedbackButton(viewModel)
                } else {
                    DeleteButton(viewModel)
                }
            }
        }
        if (viewModel.showDeleteDialog) {
            DeleteComponentButton(viewModel)
        }
        if (viewModel.showFeedbackDialog) {
            FeedbackDialog(viewModel)
        }
    }
}

@Composable
fun DeleteComponentButton(viewModel: SavedCourseViewModel) {
    AlertDialog(
        onDismissRequest = {
            viewModel.onEvent(SavedCourseEvent.OnDeleteDismissClick)
        },
        title = { Text("Brisanje Kursa") },
        text = { Text("Jeste li sigurni da zelite da izbrisete ovaj kurs iz planera?") },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                onClick = {
                    viewModel.onEvent(SavedCourseEvent.OnDeleteConfirmClick)
                }
            ) {
                Text("Potvrdi")
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                onClick = {
                    viewModel.onEvent(SavedCourseEvent.OnDeleteDismissClick)
                }
            ) {
                Text("Ponisti")
            }
        }
    )
}
@Composable
fun FeedbackDialog(viewModel: SavedCourseViewModel) {
    AlertDialog(
        onDismissRequest = {
            viewModel.onEvent(SavedCourseEvent.OnFeedbackDismissClick)
        },
        title = { Text(text = "Recenzija") },
        text = {
            Column {
                Text("\n" +
                        "Voleli bismo da čujemo vašu povratnu informaciju!")
                TextField(
                    value = viewModel.feedbackText,
                    onValueChange = { viewModel.onEvent(SavedCourseEvent.OnFeedbackTextChangeClick(it)) },
                    label = { Text("Recenzija") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedLabelColor = Color.Black,
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Kakvi su vasi utisci?")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                        SadSmileyButton(
                            isSelected = viewModel.selectedRating == 1,
                            onClick = { viewModel.onEvent(SavedCourseEvent.OnFeedbackRatingClick(1)) }
                        )
                        NeutralSmileyButton(
                            isSelected = viewModel.selectedRating == 2,
                            onClick = { viewModel.onEvent(SavedCourseEvent.OnFeedbackRatingClick(2)) }
                        )
                        HappySmileyButton(
                            isSelected = viewModel.selectedRating == 3,
                            onClick = { viewModel.onEvent(SavedCourseEvent.OnFeedbackRatingClick(3)) }
                        )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.onEvent(SavedCourseEvent.OnFeedbackSubmitClick(viewModel.selectedRating, viewModel.feedbackText))
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Posalji")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.onEvent(SavedCourseEvent.OnFeedbackDismissClick)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                )
            )
            {
                Text("Ponisti")
            }
        }
    )
}

@Composable
fun DeleteButton(
    viewModel: SavedCourseViewModel
) {
    Button(
        onClick = { viewModel.onEvent(SavedCourseEvent.OnDeleteCourseClick) },
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
            text = "Izbrisi",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            )
        )
    }
}

@Composable
fun FeedbackButton(
    viewModel: SavedCourseViewModel
) {
    Button(
        onClick = {
            viewModel.onEvent(SavedCourseEvent.OnFeedbackClick)
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
            text = "Recenzija",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
        )
    }
}

@Composable
fun SadSmileyButton(isSelected: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(id = if (isSelected) R.drawable.ic_smiley_sad_selected else R.drawable.ic_smiley_sad_unselected),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(all = 8.dp)
        )
    }
}

@Composable
fun NeutralSmileyButton(isSelected: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(id = if (isSelected) R.drawable.ic_smiley_neutral_selected else R.drawable.ic_smiley_neutral_unselected),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(all = 8.dp)
        )
    }
}

@Composable
fun HappySmileyButton(isSelected: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(id = if (isSelected) R.drawable.ic_smiley_happy_selected else R.drawable.ic_smiley_happy_unselected),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(all = 8.dp)
        )
    }
}


