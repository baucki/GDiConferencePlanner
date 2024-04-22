package com.gdi.conferenceplanner.ui.presentation.registration

sealed class RegistrationEvent {
    data class OnTextFieldChanged(val textField: String, val input: String): RegistrationEvent()
    object OnRegistrationSaveClick: RegistrationEvent()
}