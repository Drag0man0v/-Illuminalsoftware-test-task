package com.example.test_task.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.test_task.data.api.CoinDto
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import com.example.test_task.ui.viewModels.CoinListUiState
import com.example.test_task.ui.viewModels.CoinListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    onCoinClick: (String) -> Unit, // Callback that returns selected coin ID/name
    viewModel: CoinListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        // Search input field for filtering coins list
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChanged(it) },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            placeholder = { Text("Search coin...") },
            singleLine = true
        )

        when (val state = uiState) {

            // Loading state while fetching data from API
            is CoinListUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            // Error state with retry option
            is CoinListUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.message, color = Color.Red)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadCoins() }) {
                            Text("Try again")
                        }
                    }
                }
            }

            // Success state showing list of coins
            is CoinListUiState.Success -> {
                PullToRefreshBox(
                    isRefreshing = false,
                    onRefresh = { viewModel.loadCoins() }
                ) {

                    // Show empty state if no coins match search
                    if (state.coins.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No results found")
                        }
                    } else {
                        LazyColumn {

                            // Display list of coins
                            items(state.coins) { coin ->
                                CoinListItem(
                                    coin = coin,
                                    onClick = { onCoinClick(coin.name) }
                                )
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CoinListItem(coin: CoinDto, onClick: () -> Unit) {

    // Single row item representing a coin in the list
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Coin image
        AsyncImage(
            model = coin.image,
            contentDescription = coin.name,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Coin name and symbol
        Column(modifier = Modifier.weight(1f)) {
            Text(text = coin.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = coin.symbol.uppercase(), color = Color.Gray, fontSize = 13.sp)
        }

        // Price and 24h change
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$${String.format("%.2f", coin.currentPrice)}",
                fontWeight = FontWeight.Bold
            )

            val changeColor =
                if (coin.priceChangePercentage24h >= 0)
                    Color(0xFF4CAF50)
                else
                    Color(0xFFE53935)

            Text(
                text = "${String.format("%.2f", coin.priceChangePercentage24h)}%",
                color = changeColor,
                fontSize = 13.sp
            )
        }
    }
}