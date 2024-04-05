package com.plcoding.daggerhiltcourse.ui.presentation.edit_account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.user.UserRepository
import com.plcoding.daggerhiltcourse.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAccountViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    var user by mutableStateOf<User?>(null)

    init {
        viewModelScope.launch {
            userRepository.findUserByUsername("test")?.let { user ->
                this@EditAccountViewModel.user = user
            }
        }
    }

}