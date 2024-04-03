package com.plcoding.daggerhiltcourse.ui.presentation.login

sealed class LoginEvent {
    data class OnUsernameChanged(val input: String): LoginEvent()
    data class OnPasswordChanged(val input: String): LoginEvent()
    object OnLoginClick: LoginEvent()
}