# AllThingsAndroid

Portfolio Android app: ten years of product work as **runnable mini-features**, not a widget gallery.

**Arthur Abreu — Senior Android Developer**  
Repo: [github.com/arthurabreu/AllThingsAndroid](https://github.com/arthurabreu/AllThingsAndroid)

## What a recruiter should open

| Home card | What it proves |
| --- | --- |
| Shop + cart | Offline cart, stock rules, checkout |
| Paged lists | Search, empty, error, retry |
| Maps | Pins / polyline (demo map; live uses Maps key) |
| Chat | Ktor WebSocket + offline queue |
| Voice AI | PCM stream + Python echo server / local fallback |
| Firebase suite | Auth, notes, Remote Config, FCM token, Storage, Crashlytics (demo fakes; live when configured) |
| Room + migrations | Schema v1→v3 in git |
| LeakCanary | Intentional leak behind a flag (debug) |
| Field feedback | CSAT + photo + offline queue (generic, no client IP) |
| Lab | Previous design-system / teaching screens |

## Stack

- Kotlin, Jetpack Compose, Material 3
- **Koin** (one module per feature cluster)
- **Ktor** (HTTP + WebSockets, `MockEngine` in tests)
- Room + exported schemas, WorkManager, LeakCanary (debug)
- Flavors: `demo` (no secrets) and `live` (`local.properties`)

Official Google Android skills for agents: [`.agents/skills/android`](.agents/skills/android). Agent contract: [AGENTS.md](AGENTS.md). Git rules: [CONTRIBUTING.md](CONTRIBUTING.md).

## Run

1. Android Studio + JDK 21 + SDK 35.
2. Open the project, sync, run **`demoDebug`**. No API keys required.
3. Optional voice server: see [`server/README.md`](server/README.md).
4. Optional live Maps: `MAPS_API_KEY=...` in `local.properties`, run `liveDebug`.

Calculator instrumented tests walkthrough: [YouTube](https://www.youtube.com/watch?v=ZUHIOGA8iao).

## Architecture

See [ARCHITECTURE.md](./ARCHITECTURE.md). Flow: UI → ViewModel → use case → repository → Ktor / Room.

```
:app
:core:{common,model,domain,network,database,ui,navigation}
:feature:{home,lab,persistence,lists,shop,maps,firebase,chat,voice,feedback}
:commonscreens   (Lab UI)
```

## Tests

```bash
./gradlew :core:domain:test :feature:shop:test :feature:home:test
./gradlew assembleDemoDebug
```

Domain / ViewModel / use case target: **90%**. Every new screen has a Compose UI test.

## Git

Never commit on `master`. Branch `tipo/escopo`. Conventional Commits. No `Co-authored-by`. After a slice of work, the author reviews the diff before any commit.
