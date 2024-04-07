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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.daggerhiltcourse.ui.presentation.account.AccountViewModel
import com.plcoding.daggerhiltcourse.util.UiEvent

@Composable
fun EditAccountScreen(
    onPopBackStack: () -> Unit,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val user = viewModel.user

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onPopBackStack()
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
            }
        }
    }
}
@Composable
fun PersonalInformationComponent(
    viewModel: AccountViewModel
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
            EditTextField(label = "Ime", value = viewModel.user!!.name)
            EditTextField(label = "Prezime", value = viewModel.user!!.lastName)
            EditTextField(label = "Email", value = viewModel.user!!.email)
            EditTextField(label = "Drzava", value = viewModel.user!!.country)
            EditTextField(label = "Grad", value = viewModel.user!!.city)
            EditTextField(label = "Telefon", value = viewModel.user!!.phone)
            EditTextField(label = "Zanimanje", value = viewModel.user!!.profession)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {  },
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
fun SignInInformation(viewModel: AccountViewModel) {
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
            EditTextField(label = "Staro Korisnicko Ime", value = viewModel.user!!.username)
            EditTextField(label = "Novo Korisnicko Ime", value = "")
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {  },
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
            EditTextField(label = "Stara Sifra", value = "")
            EditTextField(label = "Nova Sifra", value = "")
            EditTextField(label = "Ponovljena Sifra", value = "")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {  },
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
fun EditTextField(
    label: String,
    value: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            label = { Text(label) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                focusedLabelColor =  Color.Black,
                unfocusedBorderColor = Color.Black,
                unfocusedLabelColor = Color.Black,
            )
        )
    }
}
