package com.example.buscaminas.database


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ResultDataViewModel(private val repository: ResultDataRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: List<ResultDataEntity> = repository.allWords

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(result: ResultDataEntity) = viewModelScope.launch {
        repository.insert(result)
    }
    /*fun getPlayerNameFromDatabase() = viewModelScope.launch {
        repository.getListDataFromDatabase()
    }*/
}

class ResultDataModelFactory(private val repository: ResultDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResultDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}