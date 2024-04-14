package com.plcoding.daggerhiltcourse.ui.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.plcoding.daggerhiltcourse.util.Routes

@Composable
fun TopBar(
    navController: NavController,
    topBarState: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val title: String = when (backStackEntry?.destination?.route) {
        Routes.HOME -> "Konferencija Beograd 2024"
        Routes.MY_AGENDA -> "Konferencija Beograd 2024"
        Routes.CLIENTS -> "Klijenti"
        Routes.SPLASH_SCREEN -> ""
        Routes.LOGIN -> "Prijava"
        Routes.REGISTER -> "Registracija"
        Routes.ACCOUNT -> "Nalog"
        Routes.QR_CODE -> "QR Kod"
        else -> "Detalji"
    }
    AnimatedVisibility(
        visible = topBarState.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
    ) {
        TopAppBar(
            modifier = modifier,
            title = { Text(text = title) },
            navigationIcon = {
                if (backStackEntry?.destination?.route !in listOf(
                        Routes.HOME,
                        Routes.MY_AGENDA,
                        Routes.CLIENTS,
                        Routes.ACCOUNT,
                        Routes.SPLASH_SCREEN,
                        Routes.QR_CODE
                )) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            },
            backgroundColor = MaterialTheme.colors.background,
            elevation = 6.dp
        )
    }

}