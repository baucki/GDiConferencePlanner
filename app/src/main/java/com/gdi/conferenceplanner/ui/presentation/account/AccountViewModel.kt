package com.gdi.conferenceplanner.ui.presentation.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdi.conferenceplanner.data.datasource.remote.repository.user.UserRepository
import com.gdi.conferenceplanner.data.model.remote.responses.User
import com.gdi.conferenceplanner.util.handlers.DataStoreHandler
import com.gdi.conferenceplanner.util.Routes
import com.gdi.conferenceplanner.util.UiEvent
import com.gdi.conferenceplanner.util.handlers.TokenHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    var isLoggedIn by mutableStateOf(false)
    var user by mutableStateOf<User?>(null)

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
//            DataStoreHandler.write("")
            val token = DataStoreHandler.read()
            isLoggedIn = if (token != "") {
                val jwtDecoded = TokenHandler.decodeToken(token)
                val username = JSONObject(jwtDecoded).getString("username")
                try {
                    user = userRepository.findUserByUsername(username)
                } catch (e: IOException) {
                    _errorMessage.value = "Nema interneta"
                } catch (e: Exception) {
                    _errorMessage.value = "Doslo je do greske"
                }
                true
            } else {
                user = null
                false
            }
        }
    }

    fun onEvent(event: AccountEvent) {
        when (event) {
            is  AccountEvent.OnLogoutClick -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.LOGIN))
                    delay(300)
                    isLoggedIn = false
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