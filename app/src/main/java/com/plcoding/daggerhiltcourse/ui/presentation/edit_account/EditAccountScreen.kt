package com.plcoding.daggerhiltcourse.ui.presentation.edit_account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import com.plcoding.daggerhiltcourse.ui.presentation.account.AccountViewModel
import com.plcoding.daggerhiltcourse.ui.presentation.saved_course.SavedCourseEvent
import com.plcoding.daggerhiltcourse.ui.presentation.saved_course.SavedCourseViewModel
import com.plcoding.daggerhiltcourse.util.UiEvent

@Composable
fun EditAccountScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: EditAccountViewModel = hiltViewModel()
) {
    val user = viewModel.user

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowChangePasswordDialog -> {
                    viewModel.isChangePasswordPopupVisible = event.state
                }
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
                color = Color.White,
            ) {
                PersonalInformationComponent(viewModel)
            }
        }
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
            ) {
                SignInInformation(viewModel)
                if (viewModel.isChangePasswordPopupVisible) {
                    ChangePasswordPopup(viewModel)
                }
            }
        }
    }
}
@Composable
fun PersonalInformationComponent(
    viewModel: EditAccountViewModel
) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
    ) {
        Text(
            text = "Podaci o korisniku",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (viewModel.user != null) {
            EditTextField("Ime", viewModel.name, viewModel.nameErrorMessage, viewModel)
            EditTextField("Prezime", viewModel.lastName, viewModel.lastNameErrorMessage, viewModel)
            EditTextField("Zanimanje", viewModel.profession, viewModel.professionErrorMessage, viewModel)
            EditTextField("Email", viewModel.email, viewModel.emailErrorMessage, viewModel)
            EditTextField("Telefon", viewModel.phone, viewModel.phoneErrorMessage, viewModel)
            EditTextField("Drzava", viewModel.country, viewModel.countryErrorMessage, viewModel)
            EditTextField("Grad", viewModel.city, viewModel.cityErrorMessage, viewModel)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onEvent(EditAccountEvent.OnChangePersonalInformationSaveClick) },
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
}

@Composable
fun SignInInformation(viewModel: EditAccountViewModel) {
    Column(
        modifier = Modifier
            .padding(all = 16.dp)
    ) {
        Text(
            text = "Podaci o prijavi",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Korisnicko Ime",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (viewModel.user != null) {
            EditTextField("Korisnicko Ime",viewModel.newUsername, viewModel.usernameErrorMessage, viewModel)
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { viewModel.onEvent(EditAccountEvent.OnChangeUsernameSaveClick) },
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
                    text = "Sacuvaj Promene",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Sifra",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            EditPasswordTextField("Stara Sifra", viewModel.oldPassword, viewModel.oldPasswordErrorMessage, viewModel)
            EditPasswordTextField("Nova Sifra", viewModel.newPassword, viewModel.newPasswordErrorMessage, viewModel)
            EditPasswordTextField("Ponovljena Sifra", viewModel.repeatedPassword, viewModel.repeatedPasswordErrorMessage,viewModel)
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onEvent(EditAccountEvent.OnChangePasswordSaveClick) },
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
}


@Composable
fun ChangePasswordPopup(viewModel: EditAccountViewModel) {
    AlertDialog(
        onDismissRequest = {
            viewModel.onEvent(EditAccountEvent.OnChangePasswordDismissClick)
        },
        title = { Text("Promena Sifre") },
        text = { Text("Jeste li sigurni da zelite da promenite sifru?") },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                onClick = {
                    viewModel.onEvent(EditAccountEvent.OnChangePasswordConfirmClick)
                }
            ) {
                Text("Potvrdi")
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                onClick = {
                    viewModel.onEvent(EditAccountEvent.OnChangePasswordDismissClick)
                }
            ) {
                Text("Ponisti")
            }
        }
    )
}

@Composable
fun EditTextField(
    label: String,
    value: MutableState<String>,
    errorMessage: MutableState<String>,
    viewModel: EditAccountViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        OutlinedTextField(
            value = value.value,
            onValueChange = { viewModel.onEvent(EditAccountEvent.OnTextFieldChanged(label, it)) },
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
    viewModel: EditAccountViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        OutlinedTextField(
            value = value.value,
            onValueChange = { viewModel.onEvent(EditAccountEvent.OnTextFieldChanged(label, it)) },
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

