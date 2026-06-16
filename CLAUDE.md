# AllThingsAndroid — Project Context for Claude

## Stack at a Glance

- **Kotlin 2.3.20 / AGP 9.2.1 / JDK 21**
- **100% Jetpack Compose** (BOM 2026.05.00, Material 3)
- **DI:** Koin BOM 4.1.1 — every feature has its own Koin module
- **Network:** Ktor 3.5.0 + Kotlinx Serialization (no Retrofit)
- **DB:** Room 2.7.2 (with Paging 3 RemoteMediator in `:feature:feed`)
- **Firebase:** Auth (Google Sign-In via Credential Manager), Firestore, FCM, Analytics, Crashlytics
- **Media:** Media3 ExoPlayer 1.10.1 + PiP + background MediaSessionService
- **Maps:** Google Maps Compose 8.3.0 + custom JSON style
- **Testing:** JUnit 5, MockK, Turbine, Roborazzi, Robolectric, Kover coverage gate
- **CI:** GitHub Actions — build / lint / unit tests / Kover / dep-updates

## Module Map

```
build-logic/convention/     # Convention plugins (apply instead of raw plugin blocks)
app/                        # Shell: Application class, MainActivity, NavigationGraph, DI assembly
core/common/                # AppNavigator, ResourceWrapper, DomainException, Logger utils
core/designsystem/          # M3 tokens (ColorTokens → ColorSchemes, Typography, Shape, Spacing)
                            # Components: AppButton, AppCard, AppScaffold, AppStates, AppTextField
core/model/                 # DomainModel, DownloadData (shared across features)
core/domain/                # Base UseCase types (pure Kotlin)
core/data/                  # Shared data contracts
core/testing/               # BaseViewModelTest, MainDispatcherRule, InstantTaskExecutorExtension
core/ui/                    # Shared UI helpers
feature/auth/               # Firebase Auth + Google Sign-In; screen: AuthScreen
feature/clouddb/            # Firestore note CRUD; screen: CloudDbScreen
feature/feed/               # Paging3 + Coil3 + Room RemoteMediator; screen: FeedScreen
feature/maps/               # Maps Compose + POI bottom sheet; screen: MapsScreen
feature/notifications/      # FCM + WorkManager; FcmService, LocalNotificationWorker
feature/player/             # ExoPlayer + PiP + PlayerService; screen: PlayerScreen
feature/apishowcase/        # JSONPlaceholder demo
feature/calculator/         # Calculator + Espresso tests
feature/home/               # Home landing
feature/login/              # Login screen variants
feature/settings/           # Settings screen
feature/profile/            # User profile
feature/download/           # File download demo
feature/meditation/         # Stylized UI demo
feature/solid/              # SOLID patterns demo
feature/designprinciple/    # Design pattern demos
feature/olympics/           # Olympics screen
commonscreens/              # Legacy shared screens (buttons, lists, text fields) — being phased out
```

## Key Files

| File | Purpose |
|------|---------|
| `gradle/libs.versions.toml` | Single version catalog — all deps/plugins declared here |
| `app/src/main/.../core/di/AppModule.kt` | Root Koin module, assembles all feature modules |
| `app/src/main/.../core/di/ViewModelsModule.kt` | ViewModel Koin bindings |
| `app/src/main/.../core/navigation/NavigationGraph.kt` | Single NavHost wiring all feature destinations |
| `app/src/main/.../core/navigation/destinations/` | One file per feature with route object |
| `app/google-services.json` | **gitignored** — must be placed manually or injected by CI |
| `secrets.defaults.properties` | Maps API key placeholder (actual key in `local.properties`, gitignored) |
| `.github/workflows/ci.yml` | Full CI pipeline |

## Conventions

### Adding a new feature module

1. Create `feature/<name>/build.gradle.kts` applying:
   ```kotlin
   plugins {
       alias(libs.plugins.allthingsandroid.android.feature)
       alias(libs.plugins.allthingsandroid.android.compose)
       alias(libs.plugins.allthingsandroid.android.koin)
   }
   ```
2. Add module to `settings.gradle.kts`.
3. Create Koin module in `feature/<name>/di/<Name>Module.kt`.
4. Register in `AppModule.kt`.
5. Add destination object in `app/.../destinations/<Name>Feature.kt`.
6. Wire route in `NavigationGraph.kt`.

### Design system usage

Always use tokens from `core/designsystem` — never hardcode colors or sizes:
- Colors: `ColorTokens` → applied via `ColorSchemes` in `Theme.kt`
- Spacing: `Spacing.kt` (e.g. `MaterialTheme.spacing.medium`)
- Components: `AppButton`, `AppCard`, `AppTextField`, `AppScaffold`, `AppStates`

### Testing

- Extend `BaseViewModelTest` from `:core:testing` for ViewModel tests
- Use `MainDispatcherRule` for coroutine dispatch control
- Use `InstantTaskExecutorExtension` for LiveData (if any)
- Turbine for Flow assertions
- Kover gate runs in CI: `./gradlew :app:koverVerify`

### Secrets / Firebase

- `app/google-services.json` is gitignored; CI decodes from `GOOGLE_SERVICES_JSON` secret (base64)
- Maps key goes in `local.properties` as `MAPS_API_KEY=...`; CI injects via `secrets.MAPS_API_KEY`
- Google OAuth Web Client ID wired in `AuthScreen` (not in a file tracked by git)

## Branch

Active branch: `feature/android-revival` → PR target: `master`
