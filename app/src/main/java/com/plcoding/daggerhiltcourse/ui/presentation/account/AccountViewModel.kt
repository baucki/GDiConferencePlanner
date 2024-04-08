package com.plcoding.daggerhiltcourse.ui.presentation.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.user.UserRepository
import com.plcoding.daggerhiltcourse.data.model.remote.responses.User
import com.plcoding.daggerhiltcourse.util.handlers.DataStoreHandler
import com.plcoding.daggerhiltcourse.util.Routes
import com.plcoding.daggerhiltcourse.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    var isLoggedIn by mutableStateOf(false)
    var user by mutableStateOf<User?>(null)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            val flow = DataStoreHandler.read()
            flow.collect { username ->
                isLoggedIn = if (username != "") {
                    user = userRepository.findUserByUsername(username)
                    true
                } else {
                    user = null
                    false
                }
            }
        }
    }

    fun onEvent(event: AccountEvent) {
        when (event) {
            is  AccountEvent.OnLogoutClick -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.LOGIN))
                    // pravim mali delay pre nego sto resetujem vrednosti u
                    // datastore da ne bi doslo do update slike pre redirekcije na Login
                    delay(250)
                    DataStoreHandler.write("")
                }
            }
            is AccountEvent.OnLoginClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.LOGIN))
            }
            is AccountEvent.OnRegisterClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.REGISTER))
            }
            is AccountEvent.OnEditAccountClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.EDIT_ACCOUNT + "?username=${user!!.username}"))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}