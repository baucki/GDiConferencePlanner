package com.plcoding.daggerhiltcourse.ui.presentation.course_notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.daggerhiltcourse.data.model.Notification
import com.plcoding.daggerhiltcourse.ui.presentation.login.LoginEvent
import com.plcoding.daggerhiltcourse.util.AlarmScheduler
import com.plcoding.daggerhiltcourse.util.AndroidAlarmScheduler
import com.plcoding.daggerhiltcourse.util.UiEvent

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
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 32.dp),
                    text = "Uspesno ste sacuvali predavanje u svoj planer",
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
                            checkedColor = Color.Black
                        )
                    )
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Napravite notifikaciju kao podsetnik 7 dana pred pocetak predavanja")
                }
                Row() {
                    Checkbox(
                        checked = viewModel.isNotifiableInTwoDays,
                        onCheckedChange = { isChecked ->
                            viewModel.onEvent(CourseNotificationsEvent.OnTwoDaysNotificationClick(isChecked))
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Black
                        )
                    )
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Napravite notifikaciju kao podsetnik 2 dana pred pocetak predavanja")
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
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = "Potvrdi",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
            }
        }
    }
}