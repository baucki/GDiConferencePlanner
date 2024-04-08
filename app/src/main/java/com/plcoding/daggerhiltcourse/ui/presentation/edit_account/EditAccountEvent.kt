package com.plcoding.daggerhiltcourse.ui.presentation.edit_account

sealed class EditAccountEvent {
    data class OnTextFieldChanged(val textField: String, val input: String): EditAccountEvent()
    object OnChangePersonalInformationSaveClick: EditAccountEvent()
    object OnChangeUsernameSaveClick: EditAccountEvent()
    object OnChangePasswordSaveClick: EditAccountEvent()
    object OnChangePasswordConfirmClick: EditAccountEvent()
    object OnChangePasswordDismissClick: EditAccountEvent()
}