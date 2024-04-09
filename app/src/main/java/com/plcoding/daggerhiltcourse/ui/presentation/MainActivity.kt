package com.plcoding.daggerhiltcourse.ui.presentation

import android.content.Context
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
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.daggerhiltcourse.ui.presentation.account.AccountScreen
import com.plcoding.daggerhiltcourse.ui.presentation.clients.ClientsScreen
import com.plcoding.daggerhiltcourse.ui.presentation.course_details.CourseDetailsScreen
import com.plcoding.daggerhiltcourse.ui.presentation.course_notifications.CourseNotificationsScreen
import com.plcoding.daggerhiltcourse.ui.presentation.edit_account.EditAccountScreen
import com.plcoding.daggerhiltcourse.ui.presentation.home.HomeScreen
import com.plcoding.daggerhiltcourse.ui.presentation.login.LoginScreen
import com.plcoding.daggerhiltcourse.ui.presentation.my_agenda.MyAgendaScreen
import com.plcoding.daggerhiltcourse.ui.presentation.registration.RegistrationScreen
import com.plcoding.daggerhiltcourse.ui.presentation.saved_course.SavedCourseScreen
import com.plcoding.daggerhiltcourse.ui.presentation.speaker_details.SpeakerDetailsScreen
import com.plcoding.daggerhiltcourse.ui.theme.DaggerHiltCourseTheme
import com.plcoding.daggerhiltcourse.util.AlarmScheduler
import com.plcoding.daggerhiltcourse.util.AndroidAlarmScheduler
import com.plcoding.daggerhiltcourse.util.handlers.DataStoreHandler
import com.plcoding.daggerhiltcourse.util.Routes
import dagger.hilt.android.AndroidEntryPoint

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scheduler = AndroidAlarmScheduler(this)
        val myDataStore: DataStore<Preferences> = dataStore
        DataStoreHandler.dataStore = myDataStore
        setContent {
            App(scheduler)
        }
    }
}
@Composable
fun App(scheduler: AlarmScheduler) {
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    val topBarState = rememberSaveable { (mutableStateOf(false)) }
    val paddingState = rememberSaveable { (mutableStateOf(64)) }
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
                Box(Modifier.padding(bottom = paddingState.value.dp)) {
                    NavHost(navController = navController, startDestination = Routes.SPLASH_SCREEN) {
                        composable(Routes.SPLASH_SCREEN) {
                            LaunchedEffect(Unit) {
                                topBarState.value = false
                                bottomBarState.value = false
                                paddingState.value = 0
                            }
                            SplashScreen(navController = navController)
                        }
                        composable(Routes.LOGIN) {
                            LaunchedEffect(Unit) {
                                topBarState.value = true
                                bottomBarState.value = false
                                paddingState.value = 0
                            }
                            LoginScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                        composable(Routes.REGISTER) {
                            LaunchedEffect(Unit) {
                                topBarState.value = true
                                bottomBarState.value = false
                                paddingState.value = 0
                            }
                            RegistrationScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }

                        composable(Routes.HOME){
                            LaunchedEffect(Unit) {
                                bottomBarState.value = true
                                topBarState.value = true
                                paddingState.value = 64
                            }
                            HomeScreen(
                                onNavigate = {
                                    navController.navigate(it.route)
                                },
                                topBarState
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
                                topBarState.value = true
                                paddingState.value = 0
                            }
                            CourseDetailsScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                onNavigate = {
                                    navController.navigate(it.route)
                                },
                                scheduler
                            )
                        }
                        composable(
                            route = Routes.SPEAKER_DETAILS + "?speakerId={speakerId}",
                            arguments = listOf(
                                navArgument(name = "speakerId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            LaunchedEffect(Unit) {
                                topBarState.value = true
                                bottomBarState.value = false
                                paddingState.value = 0
                            }
                            SpeakerDetailsScreen()
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
                                paddingState.value = 0
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
                                paddingState.value = 64
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
                                topBarState.value = true
                                paddingState.value = 0
                            }
                            SavedCourseScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                        composable(Routes.CLIENTS) {
                            LaunchedEffect(Unit) {
                                bottomBarState.value = true
                                topBarState.value = true
                                paddingState.value = 64
                            }
                            ClientsScreen()
                        }
                        composable(Routes.ACCOUNT) {
                            LaunchedEffect(Unit) {
                                bottomBarState.value = true
                                topBarState.value = true
                                paddingState.value = 64
                            }
                            AccountScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                        composable(
                            route = Routes.EDIT_ACCOUNT +"?username={username}",
                            arguments = listOf(
                                navArgument(name = "username") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )
                        ) {
                            LaunchedEffect(Unit) {
                                bottomBarState.value = false
                                topBarState.value = true
                                paddingState.value = 0
                            }
                            EditAccountScreen(
                                onPopBackStack = {
                                    navController.popBackStack()
                                },
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}