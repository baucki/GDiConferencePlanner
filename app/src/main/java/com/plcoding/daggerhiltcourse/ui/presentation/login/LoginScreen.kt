package com.plcoding.daggerhiltcourse.ui.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.daggerhiltcourse.util.UiEvent

@Composable
fun LoginScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = viewModel.usernameTextField.value,
            onValueChange = { viewModel.usernameTextField.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            label = { Text("Username") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black
            )
        )
        OutlinedTextField(
            value = viewModel.passwordTextField.value,
            onValueChange = { viewModel.passwordTextField.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
            )
        )
        Button(
            onClick = { viewModel.onEvent(LoginEvent.OnLoginClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                text = "Login",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
}