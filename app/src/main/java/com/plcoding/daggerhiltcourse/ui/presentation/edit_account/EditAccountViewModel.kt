package com.plcoding.daggerhiltcourse.ui.presentation.edit_account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.user.UserRepository
import com.plcoding.daggerhiltcourse.data.model.remote.requests.ChangePasswordRequest
import com.plcoding.daggerhiltcourse.data.model.remote.requests.ChangePersonalInformationRequest
import com.plcoding.daggerhiltcourse.data.model.remote.requests.ChangeUsernameRequest
import com.plcoding.daggerhiltcourse.data.model.remote.responses.User
import com.plcoding.daggerhiltcourse.util.handlers.DataStoreHandler
import com.plcoding.daggerhiltcourse.util.Routes
import com.plcoding.daggerhiltcourse.util.UiEvent
import com.plcoding.daggerhiltcourse.util.handlers.HashHandler
import com.plcoding.daggerhiltcourse.util.handlers.ValidationHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAccountViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var user by mutableStateOf<User?>(null)

    var name = mutableStateOf("")
    var nameErrorMessage = mutableStateOf("")
    var lastName = mutableStateOf("")
    var lastNameErrorMessage = mutableStateOf("")
    var email = mutableStateOf("")
    var emailErrorMessage = mutableStateOf("")
    var country = mutableStateOf("")
    var countryErrorMessage = mutableStateOf("")
    var city = mutableStateOf("")
    var cityErrorMessage = mutableStateOf("")
    var phone = mutableStateOf("")
    var phoneErrorMessage = mutableStateOf("")
    var profession = mutableStateOf("")
    var professionErrorMessage = mutableStateOf("")

    private lateinit var oldUsername: String
    var newUsername = mutableStateOf("")
    var usernameErrorMessage = mutableStateOf("")

    var oldPassword = mutableStateOf("")
    var oldPasswordErrorMessage = mutableStateOf("")
    var newPassword = mutableStateOf("")
    var newPasswordErrorMessage = mutableStateOf("")
    var repeatedPassword = mutableStateOf("")
    var repeatedPasswordErrorMessage = mutableStateOf("")

    var isChangePasswordPopupVisible by mutableStateOf(false)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val username = savedStateHandle.get<String>("username")
        if (username != null) {
            viewModelScope.launch {
                userRepository.findUserByUsername(username)?.let { user ->
                    name.value = user.name
                    lastName.value = user.lastName
                    email.value = user.email
                    country.value = user.country
                    city.value = user.city
                    phone.value = user.phone
                    profession.value = user.profession

                    oldUsername = username
                    newUsername.value = username
                    this@EditAccountViewModel.user = user
                }
            }
        }
    }

    fun onEvent(event: EditAccountEvent) {
        when (event) {
            is EditAccountEvent.OnTextFieldChanged -> {
                when (event.textField) {
                    "Ime" -> {
                        name.value = event.input
                    }
                    "Prezime" -> {
                        lastName.value = event.input
                    }
                    "Email" -> {
                        email.value = event.input
                    }
                    "Drzava" -> {
                        country.value = event.input
                    }
                    "Grad" -> {
                        city.value = event.input
                    }
                    "Telefon" -> {
                        phone.value = event.input
                    }
                    "Zanimanje" -> {
                        profession.value = event.input
                    }

                    "Korisnicko Ime" -> {
                        newUsername.value = event.input
                    }

                    "Stara Sifra" -> {
                        oldPassword.value = event.input
                    }

                    "Nova Sifra" -> {
                        newPassword.value = event.input
                    }
                    "Ponovljena Sifra" -> {
                        repeatedPassword.value = event.input
                    }
                }
            }
            is EditAccountEvent.OnChangePersonalInformationSaveClick -> {
                viewModelScope.launch {
                    if (!ValidationHandler.isInputLengthValid(name.value)) {
                        nameErrorMessage.value = "Ime se ne moze ostaviti kao prazno polje"
                    } else if (!ValidationHandler.isInputLengthValid(lastName.value)) {
                        lastNameErrorMessage.value = "Prezime se ne moze ostaviti kao prazno polje"
                    } else {
                        if (
                            userRepository.changePersonalInformation(
                                ChangePersonalInformationRequest(
                                    oldUsername,
                                    name.value,
                                    lastName.value,
                                    profession.value,
                                    email.value,
                                    phone.value,
                                    country.value,
                                    city.value
                                )
                            )
                        ) {
                            sendUiEvent(UiEvent.PopBackStack)
                            sendUiEvent(UiEvent.PopBackStack)
                            sendUiEvent(UiEvent.Navigate(Routes.ACCOUNT))
                        }
                    }
                }
            }
            is EditAccountEvent.OnChangeUsernameSaveClick -> {
                viewModelScope.launch {
                    if (ValidationHandler.isUsernameValid(newUsername.value)) {
                        if (oldUsername != newUsername.value) {
                            if (
                                userRepository.changeUsername(
                                    ChangeUsernameRequest(
                                        oldUsername,
                                        newUsername.value
                                    )
                                )
                            ) {
                                DataStoreHandler.write(newUsername.value)
                                sendUiEvent(UiEvent.PopBackStack)
                                sendUiEvent(UiEvent.PopBackStack)
                                sendUiEvent(UiEvent.Navigate(Routes.ACCOUNT))
                            }
                        }
                    } else {
                        usernameErrorMessage.value = "Korisnicko ime mora biti u opsegu (4,20) " +
                                "karaktera i moze sadrzati mala slova (a-z)," +
                                " velika slova (A-Z), brojeve (0-9) kao i specijalne karaktere(.-_)"
                    }
                }
            }
            is EditAccountEvent.OnChangePasswordConfirmClick -> {
                viewModelScope.launch {
                    if (HashHandler.sha256(oldPassword.value) != user!!.password) {
                        oldPasswordErrorMessage.value = "Stara sifra se ne poklapa sa sifrom ulogovanog korisnika"
                    } else if (newPassword.value != repeatedPassword.value) {
                        repeatedPasswordErrorMessage.value = "Ponovljena sifra se ne poklapa sa novom sifrom"
                    } else if (!ValidationHandler.isPasswordValid(newPassword.value)) {
                        repeatedPasswordErrorMessage.value = "Sifra mora biti u opsegu (8,20) " +
                                "karaktera i sadrzati bar jedno veliko slovo (A-Z), jedan broj (0-9) " +
                                "i jedan specijalan karakter(!@#\$%^&*()-+)"
                    }else {
                        if (
                            userRepository.changePassword(
                                ChangePasswordRequest(
                                    oldUsername,
                                    oldPassword.value,
                                    newPassword.value
                                )
                            )
                        ) {
                            sendUiEvent(UiEvent.PopBackStack)
                            sendUiEvent(UiEvent.PopBackStack)
                            sendUiEvent(UiEvent.Navigate(Routes.ACCOUNT))
                        }
                    }
                    sendUiEvent(UiEvent.ShowChangePasswordDialog(false))
                }
            }
            is EditAccountEvent.OnChangePasswordDismissClick -> {
                sendUiEvent(UiEvent.ShowChangePasswordDialog(false))
            }
            is EditAccountEvent.OnChangePasswordSaveClick -> {
                sendUiEvent(UiEvent.ShowChangePasswordDialog(true))
            }
        }
    }
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}