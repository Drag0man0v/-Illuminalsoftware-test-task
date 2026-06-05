package com.example.test_task

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.test_task.data.api.RetrofitInstance
import com.example.test_task.ui.theme.TesttaskTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Тимчасовий тест — видали після перевірки
        lifecycleScope.launch {
            try {
                val coins = RetrofitInstance.api.getCoins()
                coins.forEach { coin ->
                    Log.d("CryptoTest", "${coin.name}: ${coin.currentPrice}$")
                }
            } catch (e: Exception) {
                Log.e("CryptoTest", "Помилка: ${e.message}")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TesttaskTheme {
        Greeting("Android")
    }
}