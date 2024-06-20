package com.capstone.chilidoc.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.chilidoc.data.repository.Repository
import com.capstone.chilidoc.data.response.DetailHistoryResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException

class ScanViewModel(private val repository: Repository) : ViewModel() {
    private val _saveResponse = MutableLiveData<DetailHistoryResponse>()
    val saveResponse: LiveData<DetailHistoryResponse> = _saveResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveResult(image: MultipartBody.Part) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.saveHistory(image)
                _saveResponse.postValue(response)

            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, DetailHistoryResponse::class.java)
                _error.value = errorResponse.success.toString()
            }
            _isLoading.value = false
        }
    }
}