package com.capstone.chilidoc.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.chilidoc.data.repository.Repository
import com.capstone.chilidoc.data.response.HistoryDataItem
import com.capstone.chilidoc.data.response.HistoryResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HistoryViewModel(private val repository: Repository) : ViewModel() {
    private val _historyResponse = MutableLiveData<List<HistoryDataItem>>()
    val historyResponse: LiveData<List<HistoryDataItem>> = _historyResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getHistory() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getHistory()
                _historyResponse.value = response.data
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, HistoryResponse::class.java)
                _error.postValue(errorResponse.success.toString())
            }
            _isLoading.value = false
        }
    }
}