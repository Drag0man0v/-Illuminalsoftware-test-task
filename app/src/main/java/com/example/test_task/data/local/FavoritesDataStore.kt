package com.example.test_task.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "favorites")

class FavoritesDataStore(val context: Context) {

    companion object {
        private val FAVORITES_KEY = stringSetPreferencesKey("favorite_coins")
    }

    // Flow that automatically updates when favorites list changes
    // UI subscribes to this Flow and recomposes on every change
    val favoritesFlow: Flow<Set<String>> = context.dataStore.data
        .map { preferences ->
            preferences[FAVORITES_KEY] ?: emptySet()
        }

    // Adds or removes a coin from favorites
    suspend fun toggleFavorite(coinId: String) {
        context.dataStore.edit { preferences ->
            val current = preferences[FAVORITES_KEY] ?: emptySet()
            preferences[FAVORITES_KEY] = if (coinId in current) {
                current - coinId
            } else {
                current + coinId
            }
        }
    }
}