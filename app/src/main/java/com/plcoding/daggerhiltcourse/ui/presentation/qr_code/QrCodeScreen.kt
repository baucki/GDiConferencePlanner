package com.plcoding.daggerhiltcourse.ui.presentation.qr_code

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.plcoding.daggerhiltcourse.util.handlers.DataStoreHandler
import com.plcoding.daggerhiltcourse.util.handlers.TokenHandler
import org.json.JSONObject

@Composable
fun QrCodeScreen(viewModel: QrCodeViewModel) {

    var username by remember { mutableStateOf("test!") }
    var type by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        val token = DataStoreHandler.read()
        if (token != "") {
            val jwtDecoded = TokenHandler.decodeToken(token)
            type = JSONObject(jwtDecoded).getString("type")
            username = JSONObject(jwtDecoded).getString("username")
        }
    }

    when (type) {
        "USER" -> {
            QrCodeGeneratorScreen(username)
        }
        "ADMIN" -> {
            QrCodeAdminScreen(viewModel)
        }
        else -> {
            QrCodeNoAccessScreen()
        }
    }
}
