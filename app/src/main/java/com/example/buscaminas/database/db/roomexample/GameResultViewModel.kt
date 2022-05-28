 package com.example.buscaminas.database.db.roomexample

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class GameResultViewModel(private val repository: GameResultRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<GameResult>> = repository.allGames.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(gameResult: GameResult) = viewModelScope.launch {
        repository.insert(gameResult)
    }

}

class GameResultViewModelFactory(private val repository: GameResultRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameResultViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}