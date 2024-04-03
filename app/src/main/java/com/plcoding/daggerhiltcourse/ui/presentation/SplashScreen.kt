package com.plcoding.daggerhiltcourse.ui.presentation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.daggerhiltcourse.R
import com.plcoding.daggerhiltcourse.ui.presentation.home.HomeViewModel
import com.plcoding.daggerhiltcourse.util.DataStoreHandler
import com.plcoding.daggerhiltcourse.util.Routes
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember {
        Animatable(2.5f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 3f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        navController.popBackStack()
        navController.navigate(Routes.HOME)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo_black),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}
