package com.gdi.conferenceplanner.ui.presentation.account

import com.gdi.conferenceplanner.data.model.remote.responses.User

sealed class AccountEvent {
    data class OnEditAccountClick(val user: User): AccountEvent()
    object OnLogoutClick: AccountEvent()
    object OnLoginClick: AccountEvent()
    object OnRegisterClick: AccountEvent()
}