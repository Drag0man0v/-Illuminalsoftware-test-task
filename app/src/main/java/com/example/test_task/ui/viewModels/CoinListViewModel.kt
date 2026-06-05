package com.example.test_task.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_task.data.api.CoinDto
import com.example.test_task.data.repository.CryptoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Screen state — sealed class to cover all possible UI states
sealed class CoinListUiState {
    object Loading : CoinListUiState()
    data class Success(val coins: List<CoinDto>) : CoinListUiState()
    data class Error(val message: String) : CoinListUiState()
}

class CoinListViewModel : ViewModel() {
    private val repository = CryptoRepository()

    private val _uiState = MutableStateFlow<CoinListUiState>(CoinListUiState.Loading)
    val uiState: StateFlow<CoinListUiState> = _uiState.asStateFlow()

    // Stores all coins for local search
    private var allCoins: List<CoinDto> = emptyList()

    // Search query state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadCoins()
    }

    // Loads coins from repository and updates UI state
    fun loadCoins() {
        viewModelScope.launch {
            _uiState.value = CoinListUiState.Loading
            repository.getCoins()
                .onSuccess { coins ->
                    allCoins = coins
                    _uiState.value = CoinListUiState.Success(coins)
                }
                .onFailure { error ->
                    _uiState.value = CoinListUiState.Error(
                        error.message ?: "Невідома помилка"
                    )
                }
        }
    }

    // Updates search query and filters coins list locally based on name or symbol
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        val filtered = if (query.isBlank()) {
            allCoins
        } else {
            allCoins.filter { coin ->
                coin.name.contains(query, ignoreCase = true) ||
                        coin.symbol.contains(query, ignoreCase = true)
            }
        }
        _uiState.value = CoinListUiState.Success(filtered)
    }
}