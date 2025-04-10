package com.example.wikiexplorer.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.wikiexplorer.data.db.CategoryEntity
import com.example.wikiexplorer.data.repository.CategoryRepository
import kotlinx.coroutines.launch
class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CategoryRepository(application)

    private val _categories = MutableLiveData<List<CategoryEntity>>()
    val categories: LiveData<List<CategoryEntity>> = _categories

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var nextToken: String? = null
    private val allData = mutableListOf<CategoryEntity>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadCategories(loadMore: Boolean = false) {
        viewModelScope.launch {
            try {
                val (data, next) = repository.fetchCategoriesFromApi(nextToken)
                allData.addAll(data)
                _categories.value = allData
                nextToken = next
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }


    fun hasMore(): Boolean = nextToken != null
}
