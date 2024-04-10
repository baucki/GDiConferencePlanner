package com.plcoding.daggerhiltcourse.ui.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.user.UserRepository
import com.plcoding.daggerhiltcourse.data.model.remote.requests.LoginRequest
import com.plcoding.daggerhiltcourse.data.model.remote.responses.Token
import com.plcoding.daggerhiltcourse.util.handlers.DataStoreHandler
import com.plcoding.daggerhiltcourse.util.Routes
import com.plcoding.daggerhiltcourse.util.UiEvent
import com.plcoding.daggerhiltcourse.util.handlers.TokenHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Base64
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    var usernameTextField = mutableStateOf("")
    var passwordTextField = mutableStateOf("")
    var errorMessage = mutableStateOf("")
    var isError = mutableStateOf(false)

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
            is LoginEvent.OnRegisterTextClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.REGISTER))
            }
            is LoginEvent.OnLoginClick -> {
                viewModelScope.launch {
                    val requestUser = LoginRequest(
                        usernameTextField.value,
                        passwordTextField.value,
                        )
                    val token: Token = userRepository.login(requestUser)
                    if (token.value != "") {
                        DataStoreHandler.write(token.value)
                        sendUiEvent(UiEvent.PopBackStack)
                        sendUiEvent(UiEvent.Navigate(Routes.HOME))
                    } else {
                        isError.value = true
                        errorMessage.value = "Pogresno korisnicko ime ili lozinka. Molimo pokusajte ponovo."
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