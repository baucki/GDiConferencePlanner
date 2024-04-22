package com.gdi.conferenceplanner.ui.presentation.speaker_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdi.conferenceplanner.data.datasource.remote.repository.speaker.SpeakerRepository
import com.gdi.conferenceplanner.data.model.remote.responses.SpeakerJSON
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeakerDetailsViewModel @Inject constructor(
    private val speakerRepository: SpeakerRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var speakerJSON by mutableStateOf<SpeakerJSON?>(null)

    init {
        val speakerId = savedStateHandle.get<Long>("speakerId")!!
        if (speakerId != -1L) {
            viewModelScope.launch {
                speakerRepository.fetchSpeakerById(speakerId)?.let { speaker ->
                    this@SpeakerDetailsViewModel.speakerJSON = speaker
                }
            }
        }
    }
}