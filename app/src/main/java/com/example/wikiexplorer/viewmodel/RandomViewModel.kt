package com.example.wikiexplorer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wikiexplorer.data.model.Page
import com.example.wikiexplorer.data.api.RetrofitInstance
import kotlinx.coroutines.launch

class RandomViewModel : ViewModel() {

    private val _article = MutableLiveData<Page?>()
    val article: LiveData<Page?> get() = _article

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        fetchRandomArticle()
    }

    private fun fetchRandomArticle() {
        _loading.postValue(true) // Show shimmer while loading

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.mediaWikiApi.getRandomArticle()
                if (response.isSuccessful) {
                    val pageMap = response.body()?.query?.pages
                    val firstPage = pageMap?.values?.firstOrNull()
                    _article.postValue(firstPage)
                    _error.postValue("") // Clear any previous error
                } else {
                    _error.postValue("API error: ${response.code()}")
                    _article.postValue(null)
                }
            } catch (e: Exception) {
                _error.postValue("Exception: ${e.localizedMessage}")
                _article.postValue(null)
            } finally {
                _loading.postValue(false) // Hide shimmer
            }
        }
    }

    fun refreshArticle() {
        fetchRandomArticle()
    }
}
