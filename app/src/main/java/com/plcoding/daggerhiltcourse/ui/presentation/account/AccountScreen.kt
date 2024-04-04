package com.plcoding.daggerhiltcourse.ui.presentation.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
    var username = viewModel.username
    var email = viewModel.email
    var profileImageUrl = viewModel.profileImageUrl

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.PopBackStack -> onPopBackStack()
            }
        }
    }

    if (user != null) {
        email = "john.doe@example.com"
        profileImageUrl = "https://bootdey.com/img/Content/avatar/avatar7.png"
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = 6.dp,
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
    ) {
        AccountItem(username, email, profileImageUrl, viewModel)
    }
}
@Composable
fun AccountItem(
    userName: String,
    userEmail: String,
    profileImageUrl: String,
    viewModel: AccountViewModel
) {

    Column(
        modifier = Modifier.padding(all = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (profileImageUrl != "") {
                Image(
                    painter = rememberAsyncImagePainter(profileImageUrl),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(64.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_account_box_24),
                    contentDescription = null)
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
        Text(userName)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Email: $userEmail")
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
            text = "Edit Account",
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
            text = "Logout",
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
            text = "Register",
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
            text = "Login",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            )
        )
    }
}