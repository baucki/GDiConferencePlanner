package com.plcoding.daggerhiltcourse.ui.presentation.clients

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.plcoding.daggerhiltcourse.data.model.Client

@Composable
fun ClientItem(client: Client) {
    val painter: Painter = rememberAsyncImagePainter(
                model = client.imageUrl,
//        model = "https://api.kickfire.com/logo?website=android.com"
    )


    if (client.name == "Empty") {
        Surface(
            modifier = Modifier
                .size(88.dp),
            color = Color.White,
        ) {
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .size(72.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = 4.dp
            ) {
                Image(
                    painter = painter,
                    contentDescription = null, // Provide proper content description if needed
                    modifier = Modifier.fillMaxSize(), // Fill the entire Surface with the image
                    contentScale = ContentScale.FillBounds // Scale the image to fit the bounds
                )
            }
        }
    }
}