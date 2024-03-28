package com.plcoding.daggerhiltcourse.ui.presentation.clients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ClientsScreen() {

    val clients = mutableListOf<String>()
    for(i in 1 .. 30) {
        clients.add("Client $i")
    }

    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        DisplayLittleBoxes(clients)
    }

}

@Composable
fun DisplayLittleBoxes(littleBoxes: List<String>) {
    LazyColumn {
        items(littleBoxes.chunked(4)) { rowOfBoxes ->
            LittleBoxRow(rowOfBoxes)
        }
    }
}

@Composable
fun LittleBoxRow(littleBoxes: List<String>) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        littleBoxes.forEach {
            ClientItem(client = it)
        }
        val remainingBoxes = 4 - littleBoxes.size
        repeat(remainingBoxes) {
            ClientItem(client = "Empty")
        }
    }
}

