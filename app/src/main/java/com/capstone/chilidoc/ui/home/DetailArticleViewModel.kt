package com.capstone.chilidoc.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.chilidoc.data.repository.Repository
import com.capstone.chilidoc.data.response.Data
import kotlinx.coroutines.launch

class DetailArticleViewModel(private val repository: Repository) : ViewModel() {
    private val _article = MutableLiveData<Data>()
    val article: LiveData<Data> = _article

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailArticle(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            val response = repository.getDetailArticle(id)
            _article.value = response.data
            _isLoading.value = false
        }
    }
}