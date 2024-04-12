package com.plcoding.daggerhiltcourse.ui.presentation.qr_code

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.plcoding.daggerhiltcourse.R

@Composable
fun QrCodeNoAccessScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_qr_code),
            contentDescription = "QR Code No Access",
            modifier = Modifier.size(120.dp)
        )
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = "Prijavite se sa genersianje qr koda"
        )
    }
}