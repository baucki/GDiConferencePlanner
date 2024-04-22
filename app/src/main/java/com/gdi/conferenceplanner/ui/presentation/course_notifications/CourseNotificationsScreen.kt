package com.gdi.conferenceplanner.ui.presentation.course_notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gdi.conferenceplanner.util.AlarmScheduler
import com.gdi.conferenceplanner.util.UiEvent

@Composable
fun CourseNotificationsScreen(
    onPopBackStack: () -> Unit,
    scheduler: AlarmScheduler,
    viewModel: CourseNotificationViewModel = hiltViewModel()
) {
    val course = viewModel.course

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                else -> Unit
            }
        }
    }

    if (course != null) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 4.dp,
            color = MaterialTheme.colors.surface,
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
                        text = course.location + ", ",
                        color = MaterialTheme.colors.primary,
                    )
                    Text(
                        text = "${course.startTime.split("T")[1].substring(0,5)} - ${course.endTime.split("T")[1].substring(0,5)}",
                        color = MaterialTheme.colors.primary,
                    )
                }
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 32.dp),
                    text = "Uspesno ste sacuvali predavanje u svoj planer",
                    color = MaterialTheme.colors.primary,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row() {
                    Checkbox(
                        checked = viewModel.isNotifiableInSevenDays,
                        onCheckedChange = { isChecked ->
                            viewModel.onEvent(CourseNotificationsEvent.OnSevenDaysNotificationClick(isChecked))
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colors.primary,
                        )
                    )
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Napravite notifikaciju kao podsetnik 7 dana pred pocetak predavanja",
                        color = MaterialTheme.colors.primary,
                    )

                }
                Row() {
                    Checkbox(
                        checked = viewModel.isNotifiableInTwoDays,
                        onCheckedChange = { isChecked ->
                            viewModel.onEvent(CourseNotificationsEvent.OnTwoDaysNotificationClick(isChecked))
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colors.primary,
                        )
                    )
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Napravite notifikaciju kao podsetnik 2 dana pred pocetak predavanja",
                        color = MaterialTheme.colors.primary,
                    )
                }
                Spacer(modifier = Modifier.height(64.dp))
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(all = 16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { viewModel.onEvent(CourseNotificationsEvent.OnConfirmClick(scheduler)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary,
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = "Potvrdi",
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