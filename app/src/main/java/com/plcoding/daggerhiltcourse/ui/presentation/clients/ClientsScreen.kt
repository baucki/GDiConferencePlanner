package com.plcoding.daggerhiltcourse.ui.presentation.clients

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.daggerhiltcourse.R
import com.plcoding.daggerhiltcourse.data.model.remote.responses.Client
import com.plcoding.daggerhiltcourse.ui.presentation.home.HomeViewModel
import com.plcoding.daggerhiltcourse.ui.presentation.home.NoInternetScreen

@Composable
fun ClientsScreen(viewModel: ClientViewModel = hiltViewModel()) {

    val clients by viewModel.clients.collectAsState(initial = emptyList())

    if (clients.isEmpty()) {
        if (viewModel.errorMessage.value == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            NoInternetScreen(viewModel = viewModel)
        }
    } else {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            elevation = 6.dp,
            shape = RoundedCornerShape(16.dp)
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

@Composable
fun NoInternetScreen(viewModel: ClientViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_internet),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
            contentDescription = "QR Code No Access",
            modifier = Modifier.size(120.dp)
        )
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = viewModel.errorMessage.value!!
        )
    }
}