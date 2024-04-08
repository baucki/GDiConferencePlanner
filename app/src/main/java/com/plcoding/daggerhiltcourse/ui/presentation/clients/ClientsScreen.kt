package com.plcoding.daggerhiltcourse.ui.presentation.clients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.daggerhiltcourse.data.model.remote.responses.Client

@Composable
fun ClientsScreen(viewModel: ClientViewModel = hiltViewModel()) {

    val clients by viewModel.clients.collectAsState(initial = emptyList())

    if (clients.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            DisplayLittleBoxes(clients)
        }
    }
}
@Composable
fun DisplayLittleBoxes(littleBoxes: List<Client>) {
    LazyColumn {
        items(littleBoxes.chunked(4)) { rowOfBoxes ->
            LittleBoxRow(rowOfBoxes)
        }
    }
}

@Composable
fun LittleBoxRow(littleBoxes: List<Client>) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        littleBoxes.forEach {
            ClientItem(client = it)
        }
        val remainingBoxes = 4 - littleBoxes.size
        repeat(remainingBoxes) {
            ClientItem(client = Client("Empty", ""))
        }
    }
}

