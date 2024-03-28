package com.plcoding.daggerhiltcourse.ui.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.daggerhiltcourse.ui.presentation.clients.ClientsScreen
import com.plcoding.daggerhiltcourse.ui.presentation.course_details.CourseDetailsScreen
import com.plcoding.daggerhiltcourse.ui.presentation.course_notifications.CourseNotificationsScreen
import com.plcoding.daggerhiltcourse.ui.presentation.home.HomeScreen
import com.plcoding.daggerhiltcourse.ui.presentation.my_agenda.MyAgendaScreen
import com.plcoding.daggerhiltcourse.ui.presentation.saved_course.SavedCourseScreen
import com.plcoding.daggerhiltcourse.ui.theme.DaggerHiltCourseTheme
import com.plcoding.daggerhiltcourse.util.AlarmScheduler
import com.plcoding.daggerhiltcourse.util.AndroidAlarmScheduler
import com.plcoding.daggerhiltcourse.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scheduler = AndroidAlarmScheduler(this)
        setContent {
            App(scheduler)
        }
    }
}

@Composable
fun App(scheduler: AlarmScheduler) {
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    val topBarState = rememberSaveable { (mutableStateOf(false)) }
    DaggerHiltCourseTheme {
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                TopBar(
                    navController = navController,
                    topBarState = topBarState
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    items = bottomNavItems,
                    navController = navController,
                    onItemClick = { navController.navigate(it.route) },
                    bottomBarState = bottomBarState
                )
            },
            content = {
                Box(Modifier.padding(bottom = 64.dp)) {
                    NavHost(navController = navController, startDestination = Routes.SPLASH_SCREEN) {
                        composable(Routes.SPLASH_SCREEN) {
                            LaunchedEffect(Unit) {
                                topBarState.value = false
                                bottomBarState.value = false
                            }
                            SplashScreen(navController = navController)
                        }

                        composable(Routes.HOME){
                            HomeScreen(
                                onNavigate = {
                                    navController.navigate(it.route)
                                },
                                topBarState,
                                bottomBarState
                            )
                        }

                        composable(
                            route = Routes.COURSE_DETAILS + "?courseId={courseId}",
                            arguments = listOf(
                                navArgument(name = "courseId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            LaunchedEffect(Unit) {
                                bottomBarState.value = false
                            }
                            CourseDetailsScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }

                        composable(
                            route = Routes.COURSE_NOTIFICATIONS + "?courseId={courseId}",
                            arguments = listOf(
                                navArgument(name = "courseId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            LaunchedEffect(Unit) {
                                bottomBarState.value = false
                                topBarState.value = true
                            }
                            CourseNotificationsScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                scheduler
                            )
                        }

                        composable(Routes.MY_AGENDA) {
                            LaunchedEffect(Unit) {
                                bottomBarState.value = true
                                topBarState.value = true
                            }
                            MyAgendaScreen(
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }

                        composable(
                            route = Routes.SAVED_COURSE + "?courseId={courseId}",
                            arguments = listOf(
                                navArgument(name = "courseId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            LaunchedEffect(Unit) {
                                bottomBarState.value = false
                            }
                            SavedCourseScreen(onPopBackStack = {
                                navController.popBackStack()
                            })
                        }

                        composable(Routes.CLIENTS) {
                            LaunchedEffect(Unit) {
                                bottomBarState.value = true
                                topBarState.value = true
                            }
                            ClientsScreen()
                        }
                    }
                }
            }
        )
    }
}