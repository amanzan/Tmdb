# TMDB Movie App

A simple Android app that displays a list of movies by genre using <a href="https://developer.themoviedb.org/" target="_blank">The Movie Database (TMDB) API</a>. Built using **Kotlin**, **Jetpack Compose**, **Hilt (DI)**, **Coroutines**, and **Clean Architecture**.

## How to Build the App

### Prerequisites
- Android Studio Flamingo or later
- Kotlin 1.6+
- An API key from <a href="https://www.themoviedb.org/" target="_blank">TMDB</a>

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/amanzan/Tmdb.git
   ```
2. Open the project in **Android Studio**.
3. Add your **TMDB API key** in `NetworkModule`:
   ```kotlin
   private const val API_KEY = "YOUR_API_KEY_HERE"
   ```
4. Sync Gradle and run the project on an emulator or a real device.

## General Architecture
The app follows **Clean Architecture** principles:

- **Presentation Layer**: Handles UI with Jetpack Compose and ViewModel
- **Domain Layer**: Contains use cases that manage business logic and the repository interface. This layer is pure Kotlin and does not depend on the Android framework or any external libraries
- **Data Layer**: Handles API interactions, data models, and repository implementation

Project structure:
```
com.bragi.tmdb
├── data
│   ├── remote
│   │   ├── model
│   │   │   ├── GenreDto.kt
│   │   │   ├── GenreResponseDto.kt
│   │   │   ├── MovieDetailsResponseDto.kt
│   │   │   ├── MovieDto.kt
│   │   │   └── MoviesResponseDto.kt
│   │   └── service
│   │       └── TmdbApiService.kt
│   ├── repository
│   │   └── MovieRepositoryImpl.kt
│   ├── NetworkModule.kt
│   └── RepositoryModule.kt
├── di
│   ├── DataModule.kt
│   └── NetworkModule.kt
├── domain
│   ├── model
│   │   ├── Genre.kt
│   │   └── Movie.kt
│   ├── repository
│   │   └── MovieRepository.kt
│   └── usecase
│       ├── GetGenresUseCase.kt
│       └── GetMoviesUseCase.kt
├── presentation
│   ├── filters
│   │   ├── FiltersScreen.kt
│   │   └── FiltersViewModel.kt
│   ├── movies
│   │   ├── MovieItem.kt
│   │   ├── MoviesScreen.kt
│   │   └── MoviesViewModel.kt
│   ├── navigation
│   │   └── AppNavHost.kt
│   └── SharedFilterViewModel.kt
├── theme
│   ├── Color.kt
│   ├── Theme.kt
│   └── Type.kt
├── MainActivity.kt
└── TmdbApp.kt
```

## Breakdown of Libraries Used

| Library              | Purpose |
|----------------------|---------|
| Jetpack Compose      | UI framework |
| Hilt                 | Dependency Injection |
| Retrofit             | API communication |
| OkHttp               | Networking and retry mechanism |
| Kotlin Serialization | JSON serialization |
| Coil                 | Image loading |
| Navigation Compose   | In-app navigation |
| Kotlin Coroutines    | Asynchronous operations |

## Features
- Fetches and displays movies from TMDB API
- Supports genre-based filtering
- Implements retry mechanism for API calls
- Shows error messages when no internet connection is detected
