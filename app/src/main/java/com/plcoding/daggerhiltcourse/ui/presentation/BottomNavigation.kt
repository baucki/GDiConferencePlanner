package com.plcoding.daggerhiltcourse.ui.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.plcoding.daggerhiltcourse.R
import com.plcoding.daggerhiltcourse.util.Routes


@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
    bottomBarState: MutableState<Boolean>
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = Color.DarkGray,
            elevation = 6.dp
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = if (selected) item.iconSelected else item.iconUnselected),
                                contentDescription = item.name,
                            )
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp,
                                color = if (selected) Color.White else Color.Gray
                            )
                        }
                    }
                )
            }
        }
    }
}

data class BottomNavItem(
    val name: String,
    val route: String,
    val iconSelected: Int,
    val iconUnselected: Int
)

val bottomNavItems = listOf(
    BottomNavItem(
        name = "Raspored",
        route = Routes.HOME,
        iconSelected = R.drawable.ic_schedule_selected,
        iconUnselected = R.drawable.ic_schedule_unselected,
    ),
    BottomNavItem(
        name = "Moj planer",
        route = Routes.MY_AGENDA,
        iconSelected = R.drawable.ic_baseline_event_24_selected,
        iconUnselected = R.drawable.ic_baseline_event_24_unselected,

    ),
    BottomNavItem(
        name = "Klijenti",
        route = Routes.CLIENTS,
        iconSelected = R.drawable.ic_clients_selected,
        iconUnselected = R.drawable.ic_clients_unselected,
    ),
    BottomNavItem(
        name = "Nalog",
        route = Routes.ACCOUNT,
        iconSelected = R.drawable.ic_account_selected,
        iconUnselected = R.drawable.ic_account_unselected,
    )
)
