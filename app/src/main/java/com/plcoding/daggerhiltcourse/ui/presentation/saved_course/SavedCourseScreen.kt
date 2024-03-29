package com.plcoding.daggerhiltcourse.ui.presentation.saved_course

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.plcoding.daggerhiltcourse.R
import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.util.UiEvent

@Composable
fun SavedCourseScreen(
    onPopBackStack: () -> Unit,
    viewModel: SavedCourseViewModel = hiltViewModel()
) {
    val course = viewModel.course

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }

    if (course != null) {
        CourseItem(course = course, viewModel = viewModel)
    }
}

@Composable
fun CourseItem(course: Course, viewModel: SavedCourseViewModel) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showFeedbackDialog by remember { mutableStateOf(false) }
    val isFinished = viewModel.isFinished

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowDeleteDialog -> {
                    showDeleteDialog = event.state
                }
                is UiEvent.ShowFeedbackDialog -> {
                    showFeedbackDialog = event.state
                }
                else -> Unit
            }
        }
    }

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
                text = course.title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                )
            )
            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = course.location + ", ")
                Text(text = "${course.startTime.split("T")[1].substring(0,5)} - ${course.endTime.split("T")[1].substring(0,5)}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 32.dp)
                    .height(screenHeight * 0.3f),
                text = course.description
            )
            Spacer(modifier = Modifier.height(2.dp))
            if (course.instructor != "") {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom

                ) {
                    AsyncImage(
                        model = course.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = course.instructor,
                        style = TextStyle(
                            fontStyle = FontStyle.Italic,
                            fontSize = 16.sp
                        )
                    )
                }
                if (isFinished) {
                    Button(
                        onClick = {
                            showFeedbackDialog=true
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
                            text = "Feedback",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            )
                        )
                    }
                } else {
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
                            text = "Delete",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            )
                        )
                    }
                }
            }
        }
        if (showDeleteDialog) {
            DeleteComponentButton(viewModel)
        }
        if (showFeedbackDialog) {
            FeedbackDialog(viewModel)
        }
    }
}

@Composable
fun DeleteComponentButton(viewModel: SavedCourseViewModel) {
    var isVisible by remember { mutableStateOf(true) }
    AlertDialog(
        onDismissRequest = { isVisible = false },
        title = { Text("Confirm Delete") },
        text = { Text("Are you sure you want to delete this course from the agenda?") },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                onClick = {
                    isVisible = false
                    viewModel.onEvent(SavedCourseEvent.OnDeleteConfirmClick)
                }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black, // Set button color to black
                    contentColor = Color.White // Set text color to white
                ),
                onClick = {
                    isVisible = false
                    viewModel.onEvent(SavedCourseEvent.OnDeleteDismissClick)
                }
            ) {
                Text("No")
            }
        }
    )
}
@Composable
fun FeedbackDialog(viewModel: SavedCourseViewModel) {
    var feedbackText by remember { mutableStateOf("") }
    var selectedRating by remember { mutableStateOf(0) }
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.UpdateFeedbackRating -> {
                    selectedRating = event.rating
                }
            }
        }
    }

    AlertDialog(
        onDismissRequest = {
            isVisible = false
            viewModel.onEvent(SavedCourseEvent.OnFeedbackDismissClick)
        },
        title = { Text(text = "Send Feedback") },
        text = {
            Column {
                Text("We'd love to hear your feedback!")
                TextField(
                    value = feedbackText,
                    onValueChange = { feedbackText = it },
                    label = { Text("Feedback") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("How was your experience?")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                        SadSmileyButton(
                            isSelected = selectedRating == 1,
                            onClick = { viewModel.onEvent(SavedCourseEvent.OnFeedbackRatingClick(1)) }
                        )
                        NeutralSmileyButton(
                            isSelected = selectedRating == 2,
                            onClick = { viewModel.onEvent(SavedCourseEvent.OnFeedbackRatingClick(2)) }
                        )
                        HappySmileyButton(
                            isSelected = selectedRating == 3,
                            onClick = { viewModel.onEvent(SavedCourseEvent.OnFeedbackRatingClick(3)) }
                        )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.onEvent(SavedCourseEvent.OnSendFeedbackClick(selectedRating, feedbackText))
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    isVisible = false
                    viewModel.onEvent(SavedCourseEvent.OnFeedbackDismissClick)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                )
            )
            {
                Text("Cancel")
            }
        }
    )
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


