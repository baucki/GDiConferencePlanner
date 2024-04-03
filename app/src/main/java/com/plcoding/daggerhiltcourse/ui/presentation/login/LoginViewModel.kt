package com.plcoding.daggerhiltcourse.ui.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.user.UserRepository
import com.plcoding.daggerhiltcourse.data.model.User
import com.plcoding.daggerhiltcourse.util.DataStoreHandler
import com.plcoding.daggerhiltcourse.util.Routes
import com.plcoding.daggerhiltcourse.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    var usernameTextField = mutableStateOf("")
    var passwordTextField = mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnUsernameChanged -> {
                usernameTextField.value = event.input
            }
            is LoginEvent.OnPasswordChanged -> {
                passwordTextField.value = event.input
            }
            is LoginEvent.OnLoginClick -> {
                viewModelScope.launch {
                    val requestUser = User(usernameTextField.value, passwordTextField.value)
                    val user = userRepository.login(requestUser)
                    if (user == requestUser) {
                        DataStoreHandler.write("${user.username}-${user.password}")
                        sendUiEvent(UiEvent.Navigate(Routes.HOME))
                    } else {
                        // handle error
                    }
                }
            }
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}