package com.example.test_task.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.test_task.ui.screens.CoinListScreen

object Routes {
    const val COIN_LIST = "coin_list"
    const val COIN_DETAIL = "coin_detail/{coinName}"

    fun coinDetail(coinName: String): String {
        return "coin_detail/$coinName"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.COIN_LIST
    ) {

        // Coin list screen
        composable(Routes.COIN_LIST) {
            CoinListScreen(
                onCoinClick = { coinName ->
                    navController.navigate(Routes.coinDetail(coinName))
                }
            )
        }

        // Coin detail screen
        composable(
            route = Routes.COIN_DETAIL,
            arguments = listOf(
                navArgument("coinName") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val coinName = backStackEntry.arguments
                ?.getString("coinName")
                ?: "Unknown coin"

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = "Coin detail: $coinName",
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { navController.popBackStack() }
                    ) {
                        Text("Back")
                    }
                }
            }
        }
    }
}