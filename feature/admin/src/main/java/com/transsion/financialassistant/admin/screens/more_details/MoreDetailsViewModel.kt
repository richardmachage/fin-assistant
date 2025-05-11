package com.transsion.financialassistant.admin.screens.more_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MoreDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var _state = MutableStateFlow(
        MoreDetailsScreenState(
            title = savedStateHandle.get<String>("title") ?: "",
            description = savedStateHandle.get<String>("description") ?: "",
            isSolved = savedStateHandle.get<Boolean>("isSolved") ?: false,
            photoUrl = savedStateHandle.get<String>("screenShotUrl")
        )
    )

    val state = _state.asStateFlow()

    fun showToast(message: String) {
        _state.update { it.copy(toastMessage = message) }
    }

    fun resetToast() {
        _state.update { it.copy(toastMessage = null) }
    }

    fun onMarkSolved() {
        showToast("will be implemented soon")
    }

}