package com.plcoding.daggerhiltcourse.ui.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.plcoding.daggerhiltcourse.data.model.Course
import com.plcoding.daggerhiltcourse.data.model.CourseWithSpeakersJSON

@Composable
fun CourseItem(
    course: CourseWithSpeakersJSON,
    isVisible: MutableState<Boolean>,
    isBreak: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .width(screenWidth * 0.13f),
            ) {
                if (isVisible.value) {
                    Text(text = course.startTime.split("T")[1].substring(0, 5))
                } else {
                    Text(text = "")
                }
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(all = 8.dp)
            ) {
                Text(
                    text = course.title,
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Text(text = course.location)
                if (!isBreak.value) {
                    Column {
                        course.speakers.forEach { speaker ->
                            Text(
                                text = speaker.name + ", " + speaker.title ,
                                style = TextStyle(fontStyle = FontStyle.Italic)
                            )
                        }
                    }
                }
            }
        }
    }
}

