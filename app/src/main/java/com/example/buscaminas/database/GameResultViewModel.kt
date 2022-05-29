package com.example.buscaminas.database

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class GameResultViewModel(private val repository: GameResultRepository) : ViewModel() {

    var allWords: LiveData<List<GameResult>> = repository.allGames.asLiveData()

    fun insert(gameResult: GameResult) = viewModelScope.launch {
        repository.insert(gameResult)
    }

    fun deleteEntry(id: Int) = viewModelScope.launch {
        repository.deleteEntry(id)
    }

}

class GameResultViewModelFactory(private val repository: GameResultRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameResultViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
