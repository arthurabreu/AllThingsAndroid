# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

- **Build the app**: `./gradlew build`
- **Build debug APK**: `./gradlew assembleDebug`
- **Install on emulator/device**: `./gradlew installDebug`
- **Run the app**: `./gradlew runDebug` (requires connected device/emulator)

## Testing

This project uses **JUnit 5** for unit tests and **Espresso + Compose UI tests** for instrumented tests.

### Unit Tests
- **Run all unit tests**: `./gradlew test`
- **Run tests for a specific module**: `./gradlew :app:test`
- **Run a specific test file**: `./gradlew test --tests "com.arthurabreu.allthingsandroid.ui.viewmodel.apishowcase.ApiShowcaseViewModelTest"`
- **Run a specific test method**: `./gradlew test --tests "com.arthurabreu.allthingsandroid.ui.viewmodel.apishowcase.ApiShowcaseViewModelTest.test*"`

Key test files are located in `app/src/test/java/`. Unit tests use:
- **JUnit 5** with Jupiter engine
- **MockK** for mocking
- **Kotlin Coroutines Test** for testing coroutines
- Custom test utilities: `MainDispatcherRule`, `InstantTaskExecutorExtension`, and `BaseViewModelTest` for common ViewModel test setup

### Instrumented Tests
- **Run all instrumented tests**: `./gradlew connectedAndroidTest`
- **Run instrumented tests for a specific module**: `./gradlew :app:connectedAndroidTest`
- **Run a specific instrumented test**: `./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.arthurabreu.allthingsandroid.ui.screen.profile.ProfileScreenTest`

Instrumented tests use:
- **Espresso** for view interaction testing
- **Compose UI Test** for Jetpack Compose testing
- **Koin Test Rule** (`BaseComposeKoinTest`) for dependency injection in tests

## Architecture Overview

The project follows **Clean Architecture** with clear separation of concerns across three layers:

### Layer Structure
```
UI Layer (ui/)
├── screen/        → Composable screens for each feature
├── viewmodel/     → ViewModels managing state and business logic
├── states/        → State data classes for UI
└── theme/         → Compose theme and styling

Domain Layer (domain/)
├── model/         → Domain models and data classes
├── repos/         → Repository interfaces (API, Data)
├── usecases/      → Business logic and use cases
└── exceptions/    → Domain-specific exceptions

Data Layer (data/)
├── remote/        → API integration (Ktor client)
├── db/            → Room database (DAO, entities, database)
├── mapper/        → DTOs ↔ Domain models mapping
├── config/        → Configuration (Resource wrapper, BaseUrlProvider)
└── error/         → Error handling
```

### Key Architecture Patterns

**State Management**
- ViewModels use `StateFlow` and `MutableStateFlow` for reactive state
- Example: `ApiShowcaseViewModel` exposes `apiData: StateFlow<Resource<DomainModel>>`
- Screens observe state and react to changes via `.collectAsState()`

**Navigation**
- Centralized navigation via `NavigationGraph.kt`
- Feature-based route definitions (sealed interfaces in `destinations/` folder)
- Navigation commands decoupled through `Channel<NavigationIntent>` in `MainViewModel`
- `AppNavigator` interface with `AppNavigatorImpl` implementation handles navigation

**Dependency Injection**
- **Koin** framework for DI
- Modules defined in `core/di/`:
  - `AppModule.kt` → Main app dependencies, ViewModels
  - `NetworkModule.kt` → Ktor client, API service
  - `PersistenceModule.kt` → Room database, DataStore
  - `ViewModelsModule.kt` → All ViewModel instances

**Async Operations**
- **Kotlin Coroutines** for background tasks
- **Flow** for continuous data streams (e.g., observing DB changes)
- ViewModels launch coroutines in `viewModelScope` for automatic cleanup

**API Integration**
- Uses **Ktor Client** (not Retrofit)
- API service: `ApiServiceKtorImpl` implements `ApiService` interface
- Serialization via **Kotlin Serialization** (JSON)
- Models: DTOs in `data/remote/dto/`, domain models in `domain/model/`
- Mapping: `ApiMapper` converts DTOs → domain models

**Persistence**
- **Room** database for local storage
- **DataStore** for preferences/simple key-value storage
- Repository pattern bridges domain and data layers

### Resource Wrapper
The project uses a custom `Resource<T>` wrapper for handling async states:
```kotlin
sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val exception: DomainException) : Resource<T>()
    class Loading<T> : Resource<T>()
}
```

## Module Structure

**app/** → Main Android application module
- Contains most features, screens, ViewModels, and business logic
- Orchestrates integration between domain and data layers

**commonscreens/** → Reusable UI library module
- Shared composable screens and UI components
- Common state models, utilities, and preview data
- Used by the app module to reduce duplication

## Key Directories & Responsibilities

- **core/** → Application infrastructure (navigation, DI, utilities)
- **ui/screen/** → Feature-specific Composable screens (one per screen)
- **ui/viewmodel/** → ViewModel per feature (handles state and logic)
- **ui/states/** → State data classes for UI layer
- **domain/usecases/** → Business logic orchestration (e.g., `DataUseCase`)
- **domain/repos/** → Repository interfaces defining data contracts
- **data/remote/** → Network layer (Ktor, API DTOs, service)
- **data/db/** → Local database layer (Room, DAOs, entities)
- **data/mapper/** → Conversion logic (DTO ↔ domain model)

## Important Notes

- **Clean Architecture Intent**: Domain and Data layers are independent; UI layer depends on both
- **Testing**: Features with clean architecture have comprehensive unit and instrumented tests; some experimental features may have less coverage
- **Modularization**: The `commonscreens` module demonstrates feature modularity; additional modules can follow this pattern
- **Logging**: Custom `ClassLogger` utility (`utils/logger/`) provides structured logging with execution context

## Common Development Patterns

### Creating a New Screen Feature
1. Add sealed interface in `core/navigation/destinations/`
2. Create `*Screen.kt` composable in `ui/screen/{feature}/`
3. Create `*ViewModel.kt` in `ui/viewmodel/{feature}/` (if needed)
4. Register in `NavigationGraph.kt` and Koin module
5. Add unit tests in `app/src/test/java/`
6. Add instrumented tests in `app/src/androidTest/java/` if UI-heavy

### Accessing ViewModels
- Use `viewModel<YourViewModel>()` in composables (Koin integration)
- Or `viewModel(factory = { YourViewModel(...) })` for custom factories

### Handling API Calls
1. Define DTOs in `data/remote/dto/`
2. Implement API method in `ApiServiceKtorImpl`
3. Create mapping in `ApiMapper`
4. Implement repository method in `ApiRepositoryImpl`
5. Call from ViewModel using the repository
6. Expose as `StateFlow` in ViewModel
