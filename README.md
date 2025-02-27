# TMDB Movie App

A simple Android app that displays a list of movies by genre using [The Movie Database (TMDB) API](https://developer.themoviedb.org/). Built using **Kotlin**, **Jetpack Compose**, **Hilt (DI)**, **Coroutines**, and **Clean Architecture**.

## How to Build the App

### Prerequisites
- Android Studio Flamingo or later
- Kotlin 1.6+
- An API key from [TMDB](https://www.themoviedb.org/)

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/tmdb-movie-app.git
   ```
2. Open the project in **Android Studio**.
3. Add your **TMDB API key** in `ApiClient.kt`:
   ```kotlin
   private const val AUTH_TOKEN = "Bearer YOUR_ACCESS_TOKEN"
   ```
4. Sync Gradle and run the project on an emulator or a real device.

## General Architecture
The app follows **Clean Architecture** principles:

- **Presentation Layer**: Handles UI with Jetpack Compose and ViewModel
- **Domain Layer**: Contains use cases that manage business logic and the repository interface. This layer is pure Kotlin and does not depend on the Android framework or any external libraries
- **Data Layer**: Handles API interactions, data models, and repository implementation

## Breakdown of Libraries Used

| Library | Purpose |
|---------|---------|
| Jetpack Compose | UI framework |
| Hilt | Dependency Injection |
| Retrofit | API communication |
| OkHttp | Networking and retry mechanism |
| Moshi | JSON serialization |
| Coil | Image loading |
| Navigation Compose | In-app navigation |
| Kotlin Coroutines | Asynchronous operations |

## Features
- Fetches and displays movies from TMDB API
- Supports genre-based filtering
- Implements retry mechanism for API calls
- Shows error messages when no internet connection is detected
