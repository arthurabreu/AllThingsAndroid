# AllThingsAndroid

Android reference app built with Jetpack Compose, demonstrating modern Android architecture at scale. Multi-module, production-grade patterns with real Firebase integration, CI/CD, and coverage gates.

> Full architecture document: [ARCHITECTURE.md](./ARCHITECTURE.md)

---

## Table of Contents
- [Key Highlights](#key-highlights)
- [Module Structure](#module-structure)
- [Features Implemented](#features-implemented)
- [Core Technologies](#core-technologies)
- [CI/CD](#cicd)
- [Getting Started](#getting-started)
- [Architecture Overview](#architecture-overview)
- [General Screens](#general-screens)
- [Calculator Instrumented Tests Demo](#calculator-instrumented-tests-demo)
- [Acknowledgements](#acknowledgements)
- [Author](#author)

---

## Key Highlights

- **100% Jetpack Compose** (Material 3, token-based design system)
- **Multi-module** with `build-logic` convention plugins — zero boilerplate per module
- **Koin BOM 4.1** DI wired across all modules
- **Firebase Auth** (Google Sign-In via Credential Manager), **Firestore** CRUD, **FCM** push notifications
- **Media3 ExoPlayer** with Picture-in-Picture + background `MediaSessionService`
- **Paging 3 + Coil 3** paginated feed with Room cache (RemoteMediator)
- **Google Maps Compose** with custom JSON map style + POI bottom sheet
- **Kover** coverage gate enforced in CI
- **GitHub Actions** CI: build → lint → unit tests → coverage → dependency freshness

---

## Module Structure

```
AllThingsAndroid/
├── build-logic/
│   └── convention/          # Gradle convention plugins
│       ├── AndroidApplicationConventionPlugin
│       ├── AndroidLibraryConventionPlugin
│       ├── AndroidFeatureConventionPlugin
│       ├── AndroidComposeConventionPlugin
│       ├── AndroidKoinConventionPlugin
│       ├── AndroidKoverConventionPlugin
│       ├── AndroidTestConventionPlugin
│       └── JvmLibraryConventionPlugin
│
├── app/                     # Application shell — DI init, NavGraph, MainActivity
│
├── core/
│   ├── common/              # AppNavigator, ResourceWrapper, DomainException, utils
│   ├── data/                # Shared data layer contracts
│   ├── designsystem/        # M3 tokens (Color, Typography, Shape, Spacing) + AppButton/Card/Scaffold/TextField
│   ├── domain/              # Pure Kotlin use-case base types
│   ├── model/               # Shared domain models (DomainModel, DownloadData)
│   ├── testing/             # BaseViewModelTest, MainDispatcherRule, InstantTaskExecutorExtension
│   └── ui/                  # Shared UI utilities
│
├── feature/
│   ├── auth/                # Firebase Auth + Google Sign-In (Credential Manager)
│   ├── clouddb/             # Firestore CRUD (notes)
│   ├── feed/                # Paging 3 + Coil 3 + Room RemoteMediator
│   ├── maps/                # Google Maps Compose + custom style + POI sheet
│   ├── notifications/       # FCM push + WorkManager local notifications
│   ├── player/              # Media3 ExoPlayer, PiP, background MediaSessionService
│   ├── apishowcase/         # JSONPlaceholder REST demo
│   ├── calculator/          # Calculator with instrumented tests
│   ├── designprinciple/     # Design pattern demos
│   ├── download/            # File download demo
│   ├── home/                # Home landing
│   ├── login/               # Login examples
│   ├── meditation/          # Stylized UI demo
│   ├── olympics/            # Olympics screen
│   ├── profile/             # User profile
│   ├── settings/            # App settings
│   └── solid/               # SOLID principles demo
│
├── commonscreens/           # Legacy shared screens (buttons, lists, text fields)
└── gradle/
    └── libs.versions.toml   # Single version catalog for all modules
```

---

## Features Implemented

| Feature | Key Tech |
|---------|----------|
| **Auth** | Firebase Auth, Google Sign-In (Credential Manager) |
| **Cloud DB** | Firestore CRUD, real-time note sync |
| **Feed** | Paging 3, Room RemoteMediator, Coil 3, Ktor |
| **Maps** | Google Maps Compose, custom JSON style, POI bottom sheet |
| **Notifications** | FCM, WorkManager local notification scheduling |
| **Player** | Media3 ExoPlayer, HLS, Picture-in-Picture, MediaSessionService |
| **API Showcase** | JSONPlaceholder, Ktor, Kotlinx Serialization |
| **Calculator** | Espresso instrumented tests |
| **Design System** | M3 token-based colors/typography/spacing, shared components |
| **Home / Profile / Settings** | Navigation-destination pattern |
| **Login** | Multiple login screen patterns |
| **Download** | File download demo |

---

## Core Technologies

| Category | Library / Version |
|----------|-------------------|
| Language | Kotlin 2.3.20 |
| Build | AGP 9.2.1, Gradle convention plugins |
| UI | Jetpack Compose BOM 2026.05.00, Material 3 |
| Navigation | Navigation Compose 2.8.8 |
| DI | Koin BOM 4.1.1 |
| Network | Ktor 3.5.0 + Kotlinx Serialization |
| Database | Room 2.7.2 |
| Paging | Paging 3.3.6 |
| Images | Coil 3.2.0 (Ktor3 backend) |
| Maps | Maps Compose 8.3.0 |
| Firebase | BOM 34.11.0 (Auth, Firestore, FCM, Analytics, Crashlytics) |
| Media | Media3 1.10.1 (ExoPlayer, HLS, Session) |
| Auth Credentials | Credential Manager 1.5.0, GoogleID 1.1.1 |
| Background | WorkManager 2.10.0 |
| Storage | DataStore Preferences 1.1.3 |
| Testing | JUnit 5.13.3, MockK 1.14.4, Turbine, Roborazzi, Robolectric |
| Coverage | Kover 0.9.1 |

---

## CI/CD

GitHub Actions (`.github/workflows/ci.yml`) runs on push to `feature/android-revival` / `master` and on PRs to `master`.

| Job | What it does |
|-----|-------------|
| `build` | `assembleDebug` + `:app:lintDebug`, uploads APK artifact |
| `unit-tests-coverage` | `testDebugUnitTest` + Kover XML + `koverVerify` gate |
| `static-analysis` | `lintDebug` across all modules |
| `dependency-updates` | `dependencyUpdates` (ben-manes, runs Monday 06:00 UTC) |

Required GitHub secrets: `GOOGLE_SERVICES_JSON` (base64), `MAPS_API_KEY`, `GRADLE_ENCRYPTION_KEY`.

---

## Getting Started

### Prerequisites
- Android Studio Meerkat or newer
- JDK 21 (Temurin recommended)
- Google Maps API key (for `:feature:maps`)
- Firebase project with `google-services.json` placed in `app/`

### Clone & Run

```bash
git clone https://github.com/arthurabreu/AllThingsAndroid.git
cd AllThingsAndroid
```

1. Place `app/google-services.json` from your Firebase project (gitignored).
2. Add `MAPS_API_KEY=<your_key>` to `local.properties`.
3. Open in Android Studio → Gradle sync → run `app`.

### Secrets

```
# local.properties  (gitignored)
MAPS_API_KEY=your_maps_key_here
```

`google-services.json` lives at `app/google-services.json` (gitignored). CI injects it from the `GOOGLE_SERVICES_JSON` secret (base64-encoded).

---

## Architecture Overview

Layered clean architecture with full module separation:

```
UI (Compose) → ViewModel → UseCase → Repository Interface
                                          ↓
                                  Repository Impl
                              ┌────────┬──────┬────────┐
                           Network   Room  DataStore  Firebase
                           (Ktor)          (Prefs)
```

- **UI Layer:** Composable screens emit events, consume `StateFlow` UI state.
- **Presentation:** ViewModel + Coroutines + `StateFlow`. No Android deps in UseCases.
- **Domain:** Pure Kotlin. Use Cases, Repository interfaces, Domain models.
- **Data:** Repository impls, Ktor clients, Room DAOs, Firebase SDKs.
- **DI:** Koin modules per feature, assembled in `:app`.
- **Navigation:** Type-safe destination objects per feature (`AuthFeature`, `FeedFeature`, etc.) wired in `NavigationGraph.kt`.

See [ARCHITECTURE.md](./ARCHITECTURE.md) for the full Mermaid diagram and data-flow narrative.

---

## General Screens

<img width="392" height="876" alt="image" src="https://github.com/user-attachments/assets/2b87f5e6-32fa-4cfc-a70f-1e418319c2fe" />
<img width="394" height="874" alt="image" src="https://github.com/user-attachments/assets/f48dce40-4eaf-4e46-8f1a-fa16a46a4cac" />
<img width="392" height="875" alt="image" src="https://github.com/user-attachments/assets/012147dd-fe0d-476e-86b2-695296273339" />
<img width="397" height="873" alt="image" src="https://github.com/user-attachments/assets/0a5ecc7e-2d57-499c-bb26-e757dbbe9919" />
<img width="389" height="874" alt="image" src="https://github.com/user-attachments/assets/276fa846-a9e1-414-9b1f-30fb734984cd" />
<img width="391" height="872" alt="image" src="https://github.com/user-attachments/assets/2cefab8b-e5f1-4ed5-a333-b2aa4d8aae63" />
<img width="393" height="873" alt="image" src="https://github.com/user-attachments/assets/3edb03e0-cd60-4ae9-96c7-7a0bacb5fd4f" />
<img width="393" height="872" alt="image" src="https://github.com/user-attachments/assets/20429f17-cb38-4ea7-a411-4c991fc4133b" />
<img width="391" height="874" alt="image" src="https://github.com/user-attachments/assets/4208c5ee-1b31-4a0c-8927-aa354dfc9dba" />
<img width="393" height="872" alt="image" src="https://github.com/user-attachments/assets/81ef1a29-d2ff-4062-ba80-6121a69d0275" />

---

## Calculator Instrumented Tests Demo
[![Calculator Instrumented Tests](https://img.youtube.com/vi/ZUHIOGA8iao/0.jpg)](https://www.youtube.com/watch?v=ZUHIOGA8iao)

---

## Acknowledgements

* [Android Developer Documentation](https://developer.android.com/)
* [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
* [JSONPlaceholder API](https://jsonplaceholder.typicode.com/) (demo data)
* Community libraries & articles supporting best practices

---

## Author

**Arthur Abreu / Senior Android Developer**  
**Project Link:** [AllThingsAndroid Repository](https://github.com/arthurabreu/AllThingsAndroid)

---
