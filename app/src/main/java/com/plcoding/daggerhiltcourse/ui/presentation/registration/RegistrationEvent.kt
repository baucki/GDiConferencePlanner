package com.plcoding.daggerhiltcourse.ui.presentation.registration

sealed class RegistrationEvent {
    data class OnTextFieldChanged(val textField: String, val input: String): RegistrationEvent()
    object OnRegistrationSaveClick: RegistrationEvent()
}