package com.plcoding.daggerhiltcourse.ui.presentation.clients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ClientItem(client: String) {
    if (client.equals("Empty")) {
        Surface(
            modifier = Modifier
                .size(88.dp),
            color = Color.White,
            ) {
        }
    } else {
        Surface(
            modifier = Modifier
                .padding(all = 8.dp)
                .size(72.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.Yellow,
            elevation = 4.dp
        ) {
            Text(text = client)
        }
    }
}