package com.gdi.conferenceplanner.ui.presentation.saved_course

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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.gdi.conferenceplanner.R
import com.gdi.conferenceplanner.data.model.local.CourseWithSpeakers
import com.gdi.conferenceplanner.util.UiEvent

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

    LazyColumn {
        item {
            Box(
                modifier = Modifier.fillParentMaxSize()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = 4.dp,
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
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
                                color = MaterialTheme.colors.primary,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp,
                                )
                            )
                            Row(
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text(
                                    text = course.course.location + ", ",
                                    color = MaterialTheme.colors.primary,
                                )
                                Text(
                                    text = "${
                                        course.course.startTime.split("T")[1].substring(0, 5)
                                    } - ${course.course.endTime.split("T")[1].substring(0, 5)}",
                                    color = MaterialTheme.colors.primary,
                                )
                            }
                            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 4.dp))
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 32.dp)
                                    .height(screenHeight * 0.3f),
                                text = course.course.description,
                                color = MaterialTheme.colors.primary,
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
                                                    .clip(CircleShape)
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
            }

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
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                ),
                onClick = {
                    viewModel.onEvent(SavedCourseEvent.OnDeleteConfirmClick)
                }
            ) {
                Text(
                    text = "Potvrdi",
                    color = MaterialTheme.colors.onPrimary,
                )
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                ),
                onClick = {
                    viewModel.onEvent(SavedCourseEvent.OnDeleteDismissClick)
                }
            ) {
                Text(
                    text = "Ponisti",
                    color = MaterialTheme.colors.onPrimary,
                )
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
        title = { Text(
            text = "Recenzija",
            color = MaterialTheme.colors.primary,
        ) },
        text = {
            Column {
                Text(text = "\n" +
                        "Voleli bismo da čujemo vašu povratnu informaciju!",
                    color = MaterialTheme.colors.primary,
                )
                TextField(
                    value = viewModel.feedbackText,
                    onValueChange = { viewModel.onEvent(SavedCourseEvent.OnFeedbackTextChangeClick(it)) },
                    label = { Text(text = "Recenzija", color = MaterialTheme.colors.primary) },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedLabelColor = MaterialTheme.colors.primary,
                        cursorColor = MaterialTheme.colors.primary,
                        focusedIndicatorColor = MaterialTheme.colors.primary,
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Kakvi su vasi utisci?",
                    color = MaterialTheme.colors.primary,
                )
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
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                )
            ) {
                Text(
                    text = "Posalji",
                    color = MaterialTheme.colors.onPrimary,
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.onEvent(SavedCourseEvent.OnFeedbackDismissClick)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                )
            )
            {
                Text(
                    text = "Ponisti",
                    color = MaterialTheme.colors.onPrimary,
                )
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
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
        ),
        shape = RoundedCornerShape(8.dp),

        ) {
        Text(
            text = "Izbrisi",
            color = MaterialTheme.colors.onPrimary,
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
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
        ),
        shape = RoundedCornerShape(8.dp),

        ) {
        Text(
            text = "Recenzija",
            color = MaterialTheme.colors.onPrimary,
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
            colorFilter = (if (isSelected) ColorFilter.tint(MaterialTheme.colors.primary) else ColorFilter.tint(Color.Gray)),
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
            colorFilter = (if (isSelected) ColorFilter.tint(MaterialTheme.colors.primary) else ColorFilter.tint(Color.Gray)),
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
            colorFilter = (if (isSelected) ColorFilter.tint(MaterialTheme.colors.primary) else ColorFilter.tint(Color.Gray)),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(all = 8.dp)
        )
    }
}


