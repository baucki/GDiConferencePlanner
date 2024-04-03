package com.plcoding.daggerhiltcourse.ui.presentation.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.data.datasource.remote.repository.client.ClientRepository

import com.plcoding.daggerhiltcourse.data.model.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val clientRepository: ClientRepository
): ViewModel() {
    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clients: StateFlow<List<Client>> = _clients

    init {
        viewModelScope.launch {
            _clients.value = clientRepository.fetchAllClients()
        }
    }

}