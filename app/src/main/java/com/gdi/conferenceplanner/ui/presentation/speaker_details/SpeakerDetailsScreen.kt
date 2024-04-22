package com.gdi.conferenceplanner.ui.presentation.speaker_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun SpeakerDetailsScreen(viewModel: SpeakerDetailsViewModel = hiltViewModel()) {
    val speaker = viewModel.speakerJSON
    if (speaker != null) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            elevation = 6.dp,
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colors.surface,
        ) {
            Column(
                modifier = Modifier.padding(all = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(speaker.imageUrl),
                        contentDescription = speaker.name,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = speaker.name + ", ${speaker.title}",
                        color = MaterialTheme.colors.primary,
                        style = TextStyle(
                            fontStyle = FontStyle.Italic,
                            fontSize = 18.sp
                        ),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 16.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = speaker.biography,
                    color = MaterialTheme.colors.primary,
                )
            }
        }
    }
}