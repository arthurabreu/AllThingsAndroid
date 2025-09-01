# Improvement Tasks Checklist

Note: Check off each item when completed. The list is ordered from foundational architecture to code-level quality, tooling, UX, and delivery practices.

1. [ ] Define and document the overall architecture for the multi-module project (app, commonscreens) including responsibilities, data flow, and boundaries.
2. [ ] Introduce a clear module boundary guideline: what belongs in app vs commonscreens vs potential new domain/data modules.
3. [ ] Consider splitting domain and data layers into dedicated Gradle modules (e.g., :domain, :data) to enforce separation of concerns.
4. [ ] Establish navigation conventions (route naming, argument passing, deep links) and document them; ensure all features use consistent fullRoute/route patterns.
5. [ ] Audit NavigationGraph for single source of truth of startDestination and remove hardcoded strings where possible via sealed routes.
6. [ ] Create a centralized NavigationIntent abstraction documentation and tests to ensure back stack behaviors (popUpTo/inclusive/launchSingleTop) are correct.
7. [ ] Review DI (Koin) configuration: ensure proper scopes per screen/feature, avoid leaking singletons, and standardize logger parameter injection.
8. [ ] Add Koin modules for repositories/use-cases (if missing) and define a top-level DI graph initialization checklist.
9. [ ] Create an app-wide Logger abstraction guidelines and ensure all viewmodels/services use consistent tags; replace raw strings with class simpleName where applicable.
10. [ ] Introduce error handling strategy across layers (sealed error types, mapping, presentation) and document retry/backoff patterns.
11. [ ] Define coroutine/dispatcher strategy (IO/Main/Default) and inject Dispatchers via DI for testability.
12. [ ] Implement a Result/Either wrapper or Kotlin’s Result usage for repository calls and expose UI state accordingly.
13. [ ] Standardize ViewModel UI state with immutable data classes and single event channels; avoid exposing mutable state directly.
14. [ ] Create a base ViewModel utilities file for common patterns (loading/error handling, one-shot events) to reduce duplication.
15. [ ] Add unit tests for ViewModels (Home, ApiShowcase, Olympics, etc.) covering success/failure/loading and navigation intents.
16. [ ] Add instrumentation tests for key Compose screens (Login, Calculator, Home), including semantics and accessibility checks.
17. [ ] Introduce UI testing id/semantics properties for interactive components to improve test reliability.
18. [ ] Apply consistent naming conventions for ViewModels (ProfileViewModel typo fix if exists) and packages (e.g., ProfileViewmodel -> ProfileViewModel).
19. [ ] Review and fix any direct imports of top-level symbols without package qualifiers (e.g., SolidScreen/SolidViewModel) by adding proper packages or removing wildcard imports.
20. [ ] Refactor DataRepositoryImpl to hide implementation details behind interfaces and ensure mapping null-safety is consistent and centralized in mapper.
21. [ ] Add comprehensive KDoc to repository, mapper, and preferences classes describing threading, contracts, and error cases.
22. [ ] Introduce database/DAO interfaces and fake/in-memory implementations for tests.
23. [ ] Ensure preferences flows are hot/cold appropriately; consider exposing StateFlow with initial values where suitable.
24. [ ] Add timeout/cancellation policies for long-running suspend functions; ensure cooperative cancellation is respected.
25. [ ] Replace magic numbers/strings with constants or type-safe structures (e.g., sealed classes for routes, enums for ids where applicable).
26. [ ] Create a feature template (screen + ViewModel + state + navigation entry + tests) to standardize adding new features.
27. [ ] Consolidate strings in resources; ensure commonscreens uses its own resource namespace correctly and avoid duplication.
28. [ ] Add accessibility labels/content descriptions across composables (icons, images, buttons) and validate with accessibility scanner/lint.
29. [ ] Ensure text fields have proper keyboard options and IME actions; validate error states and supporting text alignment.
30. [ ] Introduce theming guidelines: typography, color scheme, shapes; document dark mode support and contrast requirements.
31. [ ] Add performance checks for composables (avoid unnecessary recompositions; use remember/derivedStateOf; stable state holders).
32. [ ] Introduce static analysis tools: ktlint/Spotless, Detekt with a shared config, and add Gradle tasks to CI.
33. [ ] Enable Android Lint with custom baseline and fix high/critical issues; fail build on new critical findings.
34. [ ] Configure Gradle Version Catalogs (libs.versions.toml) to manage dependency versions consistently.
35. [ ] Migrate deprecated APIs/usages found in navigation/compose/material to current Material3 best practices.
36. [ ] Add BuildConfig/feature flags for experimental features (e.g., Olympics) and document their rollout.
37. [ ] Implement a logging policy: debug logs suppressed in release, structured logs for critical paths; integrate with Timber or a custom abstraction.
38. [ ] Add crash/error reporting hook (placeholder) behind a DI-provided interface to avoid tight coupling to vendors.
39. [ ] Create a sample data layer contract for offline-first patterns (cache + network sync), even if network is mocked for now.
40. [ ] Document API boundaries for commonscreens vs app-specific resources (no app-only dependencies in commonscreens).
41. [ ] Review and standardize package names and directory structure (features under core/navigation/destinations vs screens).
42. [ ] Add CI workflow (GitHub Actions/other) to run checks: build, unit tests, lint, detekt/ktlint, and instrumented tests (if feasible).
43. [ ] Introduce a release build checklist: proguard/r8 rules, shrinkResources, minifyEnabled, and verify app starts.
44. [ ] Add dependency injection tests to verify Koin modules load and resolve successfully.
45. [ ] Add test fixtures/builders for state/data models to simplify test setup.
46. [ ] Add README sections for local setup, module overview, and how to add new screens/features via the template.
47. [ ] Review NavigationEffects collect block for lifecycle awareness; consider using navigation events as StateFlow or SharedFlow with replay=0 and distinctUntilChanged.
48. [ ] Ensure NavigationEffects handles rapid successive events and activity finishing states robustly; add tests.
49. [ ] Replace bare LaunchedEffect keys with stable keys where necessary to avoid re-collection on recompositions.
50. [ ] Introduce a unified state/result logger using LogExecutioner consistently; add tests for logging behavior and error paths.
51. [ ] Validate thread confinement for database/preferences operations; ensure they run on IO dispatcher.
52. [ ] Add instrumentation for cold-start time and measure navigation performance between heavy screens.
53. [ ] Ensure consistent use of sealed route objects (HomeFeature, SettingsFeature, etc.) with arguments declared in one place.
54. [ ] Audit resource qualifiers (values, dimens, strings) and introduce common dimens and spacing system.
55. [ ] Add a dependency graph diagram and keep it updated when modules change.
56. [ ] Create a migration plan to fix any identified naming typos (e.g., ProfileViewmodel) including file/class rename and references.
57. [ ] Add a sample feature using Paging (if lists are large) and standardize list state/error/empty placeholders.
58. [ ] Ensure tests cover CalculatorScreen logic with edge cases (divide by zero, invalid inputs) and UI feedback.
59. [ ] Add a contract for analytics events (screen/view events) behind an interface and no-op implementation for tests.
60. [ ] Introduce feature toggles for experimental UI patterns in commonscreens to avoid API churn.
61. [ ] Add compose stability checks (use @Stable/@Immutable where appropriate) to optimize recomposition.
62. [ ] Ensure all public functions/classes have KDoc; add Dokka to generate developer docs.
63. [ ] Add a CHANGELOG.md and adopt Conventional Commits to track changes.
64. [ ] Review test naming and structure; adopt Given-When-Then or Arrange-Act-Assert pattern consistently.
65. [ ] Introduce local and instrumentation test coverage reports (JaCoCo) and set a baseline threshold.
66. [ ] Add safe argument parsing helpers for navigation (type-safe NavType wrappers) and validate for nullability/format.
67. [ ] Replace mutable shared flows/channels with explicit event types; avoid Channel unless bounded and consumed reliably.
68. [ ] Ensure cancelation and error propagation in ViewModels do not leak coroutines; use viewModelScope + supervisorJob where needed.
69. [ ] Add a sample offline testing scenario for DataRepositoryImpl using fake AppDatabaseImpl and PreferencesManager.
70. [ ] Review commonscreens module for any app R resource leakage; ensure it uses only its own R package for resources.
71. [ ] Verify all composables have previews with necessary default state; avoid heavy dependencies in previews.
72. [ ] Add build-time checks to disallow android.util.Log usage in favor of the Logger abstraction.
73. [ ] Introduce a Gradle task alias set (build, check, testDebugUnitTest, connectedAndroidTest) and document in README.
74. [ ] Add pre-commit hooks (optional) to run ktlint/detekt/spotless and unit tests locally.
75. [ ] Create a contribution guide (CONTRIBUTING.md) to define code review and branching policies.
