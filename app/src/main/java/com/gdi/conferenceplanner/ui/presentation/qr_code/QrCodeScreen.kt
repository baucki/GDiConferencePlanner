package com.gdi.conferenceplanner.ui.presentation.qr_code

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import com.gdi.conferenceplanner.util.handlers.DataStoreHandler
import com.gdi.conferenceplanner.util.handlers.TokenHandler
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
