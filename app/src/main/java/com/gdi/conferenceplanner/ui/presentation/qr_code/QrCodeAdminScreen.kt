package com.gdi.conferenceplanner.ui.presentation.qr_code

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.gdi.conferenceplanner.R

@Composable
fun QrCodeAdminScreen(viewModel: QrCodeViewModel) {
    val scannedResult = viewModel.scannedResult.value
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { viewModel.startScan(context) }) {
            Text("Skenirajte QR Kod")
        }

        scannedResult?.let { result ->
            viewModel.findUser(result)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (viewModel.user != null) {
                    Text(text = "Korisnik: ${result} je potvrdjen u sistemu")
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_check_24),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                        contentDescription = null,
//                        modifier = Modifier.size(120.dp)
                    )
                } else {
                    if (viewModel.errorMessage.value == null) {
                        Text(text = "Korisnik: ${result} nije potvrdjen u sistemu")
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                            contentDescription = null,
//                        modifier = Modifier.size(120.dp)
                        )
                    } else {
                        Text(text = viewModel.errorMessage.value!!)
                    }
                }
            }
        }
    }
}