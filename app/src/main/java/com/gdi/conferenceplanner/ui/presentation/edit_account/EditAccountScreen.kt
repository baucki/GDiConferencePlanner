package com.gdi.conferenceplanner.ui.presentation.edit_account

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gdi.conferenceplanner.R

import com.gdi.conferenceplanner.util.UiEvent
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

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

    LazyColumn {
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colors.surface,
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
                color = MaterialTheme.colors.surface,
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
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (viewModel.user != null) {
            ProfileImage(viewModel = viewModel)
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
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                ),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "Sacuvaj promene",
                    color = MaterialTheme.colors.onPrimary,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        }
    }
}
fun copyImageToInternalStorage(context: Context, uri: Uri): String? {
    try {
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.use { input ->
            val fileName = "image_${System.currentTimeMillis()}.jpg"
            val outputFile = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(outputFile)
            input.copyTo(outputStream)
            return outputFile.absolutePath
        }
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}
@Composable
fun ProfileImage(viewModel: EditAccountViewModel) {
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val path = copyImageToInternalStorage(context, uri)
                if (path != null)
                    viewModel.imagePath.value = path
            }
        }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        if (viewModel.user != null && viewModel.imagePath.value != "") {
            val file = File(viewModel.imagePath.value)
            if (file.exists()) {
                val painter: Painter =
                    rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = file).apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                    )
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                )
            }
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
        }
        Button(
            onClick = {
                launcher.launch("image/*")
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
            ),
            shape = RoundedCornerShape(8.dp),

            ) {
            Text(
                text = "Promeni Sliku",
                color = MaterialTheme.colors.onPrimary,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                )
            )
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
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Korisnicko Ime",
            color = MaterialTheme.colors.primary,
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
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                ),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "Sacuvaj Promene",
                    color = MaterialTheme.colors.onPrimary,
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
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                ),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = "Sacuvaj promene",
                    color = MaterialTheme.colors.onPrimary,
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
        title = { Text(text  = "Promena Sifre", color = MaterialTheme.colors.primary) },
        text = { Text(text = "Jeste li sigurni da zelite da promenite sifru?", color = MaterialTheme.colors.primary,) },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                ),
                onClick = {
                    viewModel.onEvent(EditAccountEvent.OnChangePasswordConfirmClick)
                }
            ) {
                Text(text = "Potvrdi", color = MaterialTheme.colors.onPrimary)
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                ),
                onClick = {
                    viewModel.onEvent(EditAccountEvent.OnChangePasswordDismissClick)
                }
            ) {
                Text(text = "Ponisti", color = MaterialTheme.colors.onPrimary)
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
                cursorColor = MaterialTheme.colors.primary,
                focusedBorderColor = if (errorMessage.value == "") MaterialTheme.colors.primary else Color(0xFFF44336),
                focusedLabelColor =  if (errorMessage.value == "") MaterialTheme.colors.primary else Color(0xFFF44336),
                unfocusedBorderColor = if (errorMessage.value == "") MaterialTheme.colors.primary else Color(0xFFF44336),
                unfocusedLabelColor = if (errorMessage.value == "") MaterialTheme.colors.primary else Color(0xFFF44336),
            )
        )
        Text(text = errorMessage.value, color = Color(0xFFF44336))
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
                cursorColor = MaterialTheme.colors.primary,
                focusedBorderColor = if (errorMessage.value == "") MaterialTheme.colors.primary else Color(0xFFF44336),
                focusedLabelColor =  if (errorMessage.value == "") MaterialTheme.colors.primary else Color(0xFFF44336),
                unfocusedBorderColor = if (errorMessage.value == "") MaterialTheme.colors.primary else Color(0xFFF44336),
                unfocusedLabelColor = if (errorMessage.value == "") MaterialTheme.colors.primary else Color(0xFFF44336),
            )
        )
        Text(text = errorMessage.value, color = Color(0xFFF44336))
    }
}

