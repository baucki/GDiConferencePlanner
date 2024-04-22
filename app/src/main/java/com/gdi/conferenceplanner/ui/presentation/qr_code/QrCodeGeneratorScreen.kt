package com.gdi.conferenceplanner.ui.presentation.qr_code

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

@Composable
fun QrCodeGeneratorScreen(username: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val qrCodeBitmap = generateQRCodeBitmap(username)
        Image(
            bitmap = qrCodeBitmap,
            contentDescription = "QR Code",
            modifier = Modifier.size(200.dp),
            )
    }
}

@Composable
fun generateQRCodeBitmap(content: String): ImageBitmap {
    val barcodeEncoder = BarcodeEncoder()
    val bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 200, 200)
    return bitmap.asImageBitmap()
}
