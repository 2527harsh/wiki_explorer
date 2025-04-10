package com.example.wikiexplorer.viewmodel

import androidx.lifecycle.*
import com.example.wikiexplorer.data.model.Picture
import com.example.wikiexplorer.data.repository.FeaturedRepository
import kotlinx.coroutines.launch
import java.util.*

class FeaturedViewModel : ViewModel() {

    private val repository = FeaturedRepository()

    private val _image = MutableLiveData<Picture>()
    val image: LiveData<Picture> = _image

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchFeaturedImage()
    }

    fun fetchFeaturedImage() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        _isLoading.postValue(true)

        viewModelScope.launch {
            try {
                val response = repository.getFeaturedImages(year, month, day)

                // ✅ Replace this block
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.tfa?.let { featuredImage: Picture ->
                        _image.postValue(featuredImage)
                    } ?: run {
                        _error.postValue("No image found")
                    }
                } else {
                    _error.postValue("API error ${response.code()} ${response.message()}")
                }

            } catch (e: Exception) {
                _error.postValue("Exception: ${e.localizedMessage}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

}
