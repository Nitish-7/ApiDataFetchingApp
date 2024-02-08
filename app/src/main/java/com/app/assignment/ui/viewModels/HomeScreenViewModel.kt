package com.app.assignment.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.assignment.data.api.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.app.assignment.utils.Constants
import com.app.assignment.utils.ParserAndHelper

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    private var job: Job? = null
    val apiResponse = repository.apiResponse
    val lastRefreshTime = repository.lastRefreshTime

    init {
        startPolling()
    }

    fun startPolling() {
        job?.cancel()
        job = viewModelScope.launch {
            while (true) {
                repository.getProducts()
                delay(Constants.REFRESH_TIME)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}