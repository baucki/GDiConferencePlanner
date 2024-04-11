package com.plcoding.daggerhiltcourse.ui.presentation.qr_code

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.integration.android.IntentIntegrator
import com.plcoding.daggerhiltcourse.ui.presentation.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QrCodeViewModel @Inject constructor(): ViewModel() {
    private val _scannedResult = MutableLiveData<String?>()
    val scannedResult: LiveData<String?> = _scannedResult

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
