package com.plcoding.daggerhiltcourse.ui.presentation.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.daggerhiltcourse.ui.presentation.edit_account.EditAccountEvent
import com.plcoding.daggerhiltcourse.ui.presentation.edit_account.EditAccountViewModel
import com.plcoding.daggerhiltcourse.util.UiEvent

@Composable
fun RegistrationScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.Navigate -> onNavigate(event)
            }
        }
    }
    LazyColumn() {
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                RegistrationComponent(viewModel)
            }
        }
    }
}

@Composable
fun RegistrationComponent(
    viewModel: RegistrationViewModel
) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Registracija",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 32.dp)
            )
        }

        EditTextField("Ime", viewModel.name, viewModel.nameErrorMessage, viewModel)
        EditTextField("Prezime", viewModel.lastName, viewModel.lastNameErrorMessage, viewModel)
        EditTextField("Zanimanje", viewModel.profession, viewModel.professionErrorMessage, viewModel)
        EditTextField("Email", viewModel.email, viewModel.emailErrorMessage, viewModel)
        EditTextField("Telefon", viewModel.phone, viewModel.phoneErrorMessage, viewModel)
        EditTextField("Drzava", viewModel.country, viewModel.countryErrorMessage, viewModel)
        EditTextField("Grad", viewModel.city, viewModel.cityErrorMessage, viewModel)


        EditTextField("Korisnicko Ime", viewModel.username, viewModel.usernameErrorMessage, viewModel)
        EditPasswordTextField("Sifra", viewModel.password, viewModel.passwordErrorMessage, viewModel)
        EditPasswordTextField("Ponovljena Sifra", viewModel.repeatedPassword, viewModel.repeatedPasswordErrorMessage, viewModel)


        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.onEvent(RegistrationEvent.OnRegistrationSaveClick) },
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                text = "Sacuvaj promene",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
}


@Composable
fun EditTextField(
    label: String,
    value: MutableState<String>,
    errorMessage: MutableState<String>,
    viewModel: RegistrationViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        OutlinedTextField(
            value = value.value,
            onValueChange = { viewModel.onEvent(RegistrationEvent.OnTextFieldChanged(label, it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            label = { Text(label) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color.Black,
                focusedBorderColor = if (errorMessage.value == "") Color.Black else Color.Red,
                focusedLabelColor =  if (errorMessage.value == "") Color.Black else Color.Red,
                unfocusedBorderColor = if (errorMessage.value == "") Color.Black else Color.Red,
                unfocusedLabelColor = if (errorMessage.value == "") Color.Black else Color.Red,
            )
        )
        Text(text = errorMessage.value, color = Color.Red)
    }
}

@Composable
fun EditPasswordTextField(
    label: String,
    value: MutableState<String>,
    errorMessage: MutableState<String>,
    viewModel: RegistrationViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        OutlinedTextField(
            value = value.value,
            onValueChange = { viewModel.onEvent(RegistrationEvent.OnTextFieldChanged(label, it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            label = { Text(label) },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color.Black,
                focusedBorderColor = if (errorMessage.value == "") Color.Black else Color.Red,
                focusedLabelColor =  if (errorMessage.value == "") Color.Black else Color.Red,
                unfocusedBorderColor = if (errorMessage.value == "") Color.Black else Color.Red,
                unfocusedLabelColor = if (errorMessage.value == "") Color.Black else Color.Red,
            )
        )
        Text(text = errorMessage.value, color = Color.Red)
    }
}