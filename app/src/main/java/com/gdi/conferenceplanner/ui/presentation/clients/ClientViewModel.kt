package com.gdi.conferenceplanner.ui.presentation.clients

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdi.conferenceplanner.data.datasource.remote.repository.client.ClientRepository

import com.gdi.conferenceplanner.data.model.remote.responses.Client
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val clientRepository: Lazy<ClientRepository>
): ViewModel() {
    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clients: StateFlow<List<Client>> = _clients

    var isLoggedIn by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        viewModelScope.launch {
            try {
                isLoading = true
                _clients.value = clientRepository.get().fetchAllClients()
                isLoading = false
            } catch (e: IOException) {
                isLoading = false
                _errorMessage.value = "Nema interneta"
            } catch (e: Exception) {
                isLoading = false
                _errorMessage.value = "Doslo je do greske"
            }
        }
    }

}