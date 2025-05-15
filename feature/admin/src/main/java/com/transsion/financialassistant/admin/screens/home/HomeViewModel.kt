package com.transsion.financialassistant.admin.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import com.transsion.financialassistant.admin.model.FeedBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {
    private var _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private var listenerRegistration: ListenerRegistration? = null

    init {
        //getFeedbackFromFirestore()
        fbListener()
    }


    /*val listOfIssues = (0..5).map {
        FeedBack(
            title = "example $it",
            description = "Description for example $it",
            attachment = if (it % 2 == 0) "https://res.cloudinary.com/derwpisbt/image/upload/v1746556159/kyxi7v1lwkyawhsgpa1v.jpg" else null
        )
    }

    val feedBacks = mutableListOf<FeedBack>()
*/
    private fun isLoading(state: Boolean) {
        _state.update { it.copy(isLoading = state) }
    }

    private fun getFeedbackFromFirestore() {
        viewModelScope.launch {
            isLoading(true)
            firestore.collection("Feedback")
                .get()
                .addOnSuccessListener { result ->
                    val fetched = result.documents.mapNotNull {
                        it.toObject<FeedBack>()
                    }
                    _state.update { it.copy(feedbacks = fetched) }
                    isLoading(false)
                }
                .addOnFailureListener { exception ->
                    isLoading(false)
                    showToast(exception.message.toString())
                }
        }

    }


    private fun fbListener() {
        listenerRegistration = firestore.collection("Feedback")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    showToast(e.message.toString())
                    return@addSnapshotListener
                }
                snapshots?.let { snapshot ->
                    val list = mutableListOf<FeedBack>()
                    for (doc in snapshot) {
                        val item = doc.toObject<FeedBack>()
                        list.add(item)
                    }
                    _state.update { it.copy(feedbacks = list) }
                }

            }
    }

    fun showToast(message: String) {
        _state.update { it.copy(toastMessage = message) }
    }

    fun resetToast() {
        _state.update { it.copy(toastMessage = null) }
    }

    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }
}