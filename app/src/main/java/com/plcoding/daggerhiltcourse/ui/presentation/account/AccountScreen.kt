package com.plcoding.daggerhiltcourse.ui.presentation.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.plcoding.daggerhiltcourse.R
import com.plcoding.daggerhiltcourse.util.UiEvent

@Composable
fun AccountScreen(
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

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
    ) {
        AccountComponent(viewModel)
    }
}
@Composable
fun AccountComponent(
    viewModel: AccountViewModel
) {

    Column(
        modifier = Modifier.padding(all = 16.dp)
    ) {
        if (viewModel.isLoggedIn) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (viewModel.user!!.imagePath != "") {
                    Image(
                        painter = rememberAsyncImagePainter(viewModel.user!!.imagePath),
                        contentDescription = "${viewModel.user!!.name} ${viewModel.user!!.lastName}",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(64.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
                        contentDescription = null)
                }
                Column(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${viewModel.user!!.name} ${viewModel.user!!.lastName}, ${viewModel.user!!.profession}",
                        style = TextStyle(fontStyle = FontStyle.Italic),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
    //            /* TODO - ADD THIS CODE IN CASE OF BREAKING ACCOUNT INTO 2 PAGES (ACCOUNT AND SETTINGS) */
    //            Row(
    //                verticalAlignment = Alignment.CenterVertically,
    //                horizontalArrangement = Arrangement.End,
    //                modifier = Modifier.fillMaxWidth()
    //            ) {
    //                IconButton(
    //                    onClick = { /* TODO - GO TO SETTINGS PAGE (SETTINGS ICON BUTTON)  */
    //                    }) {
    //                    Icon(
    //                        imageVector = Icons.Default.Settings,
    //                        contentDescription = "Settings"
    //                    )
    //                }
    //            }
            }
//            Text("${viewModel.user!!.name} ${viewModel.user!!.lastName}")
//            Text(text = "Profession: ${viewModel.user!!.profession}")


            Spacer(modifier = Modifier.height(24.dp))

            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 8.dp))

            ContactDetail(label = "Email:", value = viewModel.user!!.email)
            ContactDetail(label = "Country:", value = viewModel.user!!.country)
            ContactDetail(label = "City:", value = viewModel.user!!.city)
            ContactDetail(label = "Phone:", value = viewModel.user!!.phone)

        } else {
            Column(
                modifier = Modifier.padding(top = 128.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_no_accounts_24),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 64.dp),
                    text = "Prijavite se za pristup podesavanja naloga",
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxHeight()
        ) {
            if (viewModel.isLoggedIn) {
                EditAccountButton(viewModel)
                LogoutButton(viewModel)
            } else {
                RegisterButton(viewModel)
                LoginButton(viewModel)
            }
        }
    }
}
@Composable
fun ContactDetail(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}
@Composable
fun EditAccountButton(viewModel: AccountViewModel) {
    Button(
        onClick = { viewModel.onEvent(AccountEvent.OnEditAccountClick(viewModel.user!!)) },
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
            text = "Podesi Nalog",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            )
        )
    }
}
@Composable
fun LogoutButton(viewModel: AccountViewModel) {
    Button(
        onClick = { viewModel.onEvent(AccountEvent.OnLogoutClick) },
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
            text = "Odjavi se",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            )
        )
    }
}

@Composable
fun RegisterButton(viewModel: AccountViewModel) {
    Button(
        onClick = { viewModel.onEvent(AccountEvent.OnRegisterClick) },
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
            text = "Registruj se",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            )
        )
    }
}

@Composable
fun LoginButton(viewModel: AccountViewModel) {
    Button(
        onClick = { viewModel.onEvent(AccountEvent.OnLoginClick) },
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
            text = "Prijavi se",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            )
        )
    }
}
