package com.plcoding.daggerhiltcourse.ui.presentation.account

import com.plcoding.daggerhiltcourse.data.model.remote.responses.User

sealed class AccountEvent {
    data class OnEditAccountClick(val user: User): AccountEvent()
    object OnLogoutClick: AccountEvent()
    object OnLoginClick: AccountEvent()
    object OnRegisterClick: AccountEvent()
}