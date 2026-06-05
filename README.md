# Crypto Tracker

A compact Android application for tracking real-time cryptocurrency prices using the CoinGecko Public API. 
Built natively with Kotlin and Jetpack Compose within a strict 3-hour time limit.


## Tech Stack & Architecture
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (Material 3)
- **Architecture Pattern:** MVVM 
- **Network Layer:** Retrofit 2 + OkHttp
- **Image Loading:** Coil
- **Local Persistence:** DataStore 
- **API:** CoinGecko Public API v3


## How to Run the App

1. **Clone the repository:**
   ```bash
   git clone <url>
   ```
   
2. **Open the project in Android Studio Hedgehog or newer.**

3. **update the *CoinGecko API Key* in the `RetrofitInstance.kt` file:
```kotlin
// Location: data/api/RetrofitInstance.kt
.addInterceptor { chain ->
    val request = chain.request().newBuilder()
        .addHeader("x-cg-demo-api-key", "YOUR_API_KEY_HERE")
        .build()
    chain.proceed(request)
}kotlin
```
4. **Sync Gradle (File -> Sync Project with Gradle Files).**

5. **Run the app on an emulator or a physical device (Minimum requirement: Android 8.0 / API 26).**


### What is Fully Implemented
- **Clean MVVM Architecture:** Total separation of concerns. The UI layer purely observes state, while the repository abstracts the network source.
- **Robust Network Layer:** Setup Retrofit with a custom safe-execution wrapper (`Result<T>`) to guarantee the app never crashes on network errors.
- **Main Screen (Coin List):** Displays the top 20 coins fetching directly from CoinGecko API, showing icons, names, symbols, prices, and color-coded 24h percentage changes.
- **Reactive Search & Filtering:** Implemented instantaneous client-side filtering by name or ticker using `StateFlow`.
- **Pull-to-Refresh:** Added Material 3 `PullToRefreshBox` to easily reload the network state with a swipe-down gesture.
- **Error & Empty State Handling:** Added user-friendly Error States with a "Retry" button and Empty States for search queries with no results.
- **Navigation Framework:** Set up standard Jetpack Navigation Compose carrying clean type-safe string arguments.

### What is Left for Future Implementation((( (Time Limitations)
- **Full Details Screen:**
  - *Status:* A simplified navigation placeholder was created. When a user clicks a coin, it smoothly navigates to an empty details screen showing the selected coin's name and a functional back button.
  - *Next Steps:* Implement the detailed UI layout (Market Cap cards, High/Low metrics) using the existing `CoinDto` model parameters.
- **Dependency Injection:**
  - Introduce Dagger/Hilt to properly inject the Repository and Retrofit instances into the ViewModels, removing boilerplate code.
