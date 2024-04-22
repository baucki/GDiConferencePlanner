package com.gdi.conferenceplanner.ui.presentation.qr_code

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.integration.android.IntentIntegrator
import com.gdi.conferenceplanner.data.datasource.remote.repository.user.UserRepository
import com.gdi.conferenceplanner.data.model.remote.responses.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.EOFException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class QrCodeViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _scannedResult = MutableLiveData<String?>()
    val scannedResult: LiveData<String?> = _scannedResult

    var user by mutableStateOf<User?>(null)

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun findUser(username: String) {
        viewModelScope.launch {
            try {
                user = userRepository.findUserByUsername(username)
            }catch (e: EOFException) {
                user = null
                _errorMessage.value = null
            } catch (e: IOException) {
                user = null
                _errorMessage.value = "Nema interneta"
            } catch (e: Exception) {
                e.printStackTrace()
                user = null
                _errorMessage.value = "Doslo je do greske"
            }
        }
    }
    fun startScan(context: Context) {
        IntentIntegrator(context as Activity).initiateScan()
    }
    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null && result.contents != null) {
            _scannedResult.value = result.contents
        }
    }
}
