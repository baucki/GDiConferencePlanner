package com.plcoding.daggerhiltcourse.ui.presentation.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.user.UserRepository
import com.plcoding.daggerhiltcourse.data.model.remote.responses.User
import com.plcoding.daggerhiltcourse.util.Routes
import com.plcoding.daggerhiltcourse.util.UiEvent
import com.plcoding.daggerhiltcourse.util.handlers.DataStoreHandler
import com.plcoding.daggerhiltcourse.util.handlers.ValidationHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository
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

    var username = mutableStateOf("")
    var usernameErrorMessage = mutableStateOf("")

    var password = mutableStateOf("")
    var passwordErrorMessage = mutableStateOf("")
    var repeatedPassword = mutableStateOf("")
    var repeatedPasswordErrorMessage = mutableStateOf("")

    var isChangePasswordPopupVisible by mutableStateOf(false)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnTextFieldChanged -> {
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
                        username.value = event.input
                    }
                    "Sifra" -> {
                        password.value = event.input
                    }
                    "Ponovljena Sifra" -> {
                        repeatedPassword.value = event.input
                    }
                }
            }
            is RegistrationEvent.OnRegistrationSaveClick -> {
                viewModelScope.launch {
                    if (!ValidationHandler.isUsernameValid(username.value)) {
                        usernameErrorMessage.value = "Korisnicko ime mora biti u opsegu (4,20) " +
                                "karaktera i moze sadrzati mala slova (a-z)," +
                                " velika slova (A-Z), brojeve (0-9) kao i specijalne karaktere(.-_)"
                    } else if(password.value != repeatedPassword.value) {
                        repeatedPasswordErrorMessage.value = "Ponovljena sifra se ne poklapa sa sifrom"
                    } else if (!ValidationHandler.isPasswordValid(password.value)) {
                        repeatedPasswordErrorMessage.value = "Sifra mora biti u opsegu (8,20) " +
                                "karaktera i sadrzati bar jedno veliko slovo (A-Z), jedan broj (0-9) " +
                                "i jedan specijalan karakter(!@#\$%^&*()-+)"
                    } else if (!ValidationHandler.isInputLengthValid(name.value)) {
                        nameErrorMessage.value = "Ime ne moze biti ostavljeno kao prazno polje"
                    } else if (!ValidationHandler.isInputLengthValid(lastName.value)) {
                        lastNameErrorMessage.value = "Prezime ne moze biti ostavljeno kao prazno polje"
                    } else {
                        val user = User(
                            username.value,
                            password.value,
                            name.value,
                            lastName.value,
                            "",
                            email.value,
                            country.value,
                            city.value,
                            profession.value,
                            phone.value,
                            "USER"
                        )
                        val token = userRepository.addUser(user)
                        println(token.value)
                        if (token.value != "") {
                            DataStoreHandler.write(token.value)
                            sendUiEvent(UiEvent.PopBackStack)
                            sendUiEvent(UiEvent.PopBackStack)
                            sendUiEvent(UiEvent.Navigate(Routes.HOME))
                        } else {
                            repeatedPasswordErrorMessage.value = "Korisnicko ime je zauzeto, molimo vas pokusajte ponovo"
                        }
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