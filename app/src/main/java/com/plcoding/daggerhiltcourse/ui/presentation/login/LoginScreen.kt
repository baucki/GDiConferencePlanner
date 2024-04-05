package com.plcoding.daggerhiltcourse.ui.presentation.login

import androidx.compose.foundation.clickable
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
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }
    LoginComponent(viewModel)
}

@Composable
fun LoginComponent(
    viewModel: LoginViewModel
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = viewModel.usernameTextField.value,
                onValueChange = { viewModel.usernameTextField.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                label = { Text("Korisnicko ime") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = Color.Black,
                    focusedBorderColor = if (viewModel.isError.value) Color.Red else Color.Black,
                    focusedLabelColor = if (viewModel.isError.value) Color.Red else Color.Black,
                    unfocusedBorderColor = if (viewModel.isError.value) Color.Red else Color.Black,
                    unfocusedLabelColor = if (viewModel.isError.value) Color.Red else Color.Black,
                )
            )
            OutlinedTextField(
                value = viewModel.passwordTextField.value,
                onValueChange = { viewModel.passwordTextField.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                label = { Text("Sifra") },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = Color.Black,
                    focusedBorderColor = if (viewModel.isError.value) Color.Red else Color.Black,
                    focusedLabelColor = if (viewModel.isError.value) Color.Red else Color.Black,
                    unfocusedBorderColor = if (viewModel.isError.value) Color.Red else Color.Black,
                    unfocusedLabelColor = if (viewModel.isError.value) Color.Red else Color.Black,
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = viewModel.errorMessage.value,
                    color = Color.Red
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Nemate nalog, registrujte se ovde",
                    color = Color.Black,
                    modifier = Modifier.clickable { viewModel.onEvent(LoginEvent.OnRegisterTextClick) }
                )
            }

        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { viewModel.onEvent(LoginEvent.OnLoginClick) },
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "Prijavi se",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        }
    }
}