package com.capstone.chilidoc.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.chilidoc.data.pref.UserModel
import com.capstone.chilidoc.data.repository.Repository
import com.capstone.chilidoc.data.response.ArticleResponse
import com.capstone.chilidoc.data.response.DataItem
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _articleResponse = MutableLiveData<List<DataItem>>()
    val articleResponse: LiveData<List<DataItem>> = _articleResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getArticles() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getArticles()
                _articleResponse.value = response.data
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ArticleResponse::class.java)
                _error.postValue(errorResponse.message)
            }
            _isLoading.value = false
        }
    }
}