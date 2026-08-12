# Agent contract

Official Android skills live in [`.agents/skills/android`](.agents/skills/android). Open the matching `SKILL.md` before implementing that area. Optional global copy: `scripts/install-android-skills.sh`.

## Stack (do not replace)

- DI: Koin — one `Module` per feature, inject dispatchers, `viewModel { }`.
- Network: Ktor (`HttpClient` + `MockEngine` in tests + WebSockets). No Hilt. No Retrofit/OkHttp.
- UI: Jetpack Compose + Material 3. Architecture: UI → ViewModel → UseCase → Repository.

## Git

- Never commit or push to `master` / `main`.
- Branch: `tipo/escopo-curto`. Conventional Commits.
- Never add `Co-authored-by`, agent `Signed-off-by`, or “via Cursor” / “AI-assisted”.
- Commit only when the user asks.
- When a work slice is finished, stop and leave the working tree for the user to review. Do not commit first.

## Quality bar

- No raw `android.util.Log` — use the injected logger.
- Every new screen ships with: Screen + ViewModel + immutable state + fake + unit test + Compose UI test.
- Domain / ViewModel / use case unit coverage target: 90%.
- Flavor `demo` must run without API keys. Secrets stay in `local.properties`.

## Skill map

| Task | Skill |
| --- | --- |
| Tests / JaCoCo / Compose UI | `.agents/skills/android/testing/testing-setup` |
| Edge-to-edge | `.agents/skills/android/system/edge-to-edge` |
| Theme / typography | `.agents/skills/android/jetpack-compose/theming/styles` |
| Adaptive layout | `.agents/skills/android/jetpack-compose/adaptive` |
| Navigation | `.agents/skills/android/navigation/navigation-3` |
| Intents | `.agents/skills/android/security/android-intent-security` |
| Camera / feedback photo | `.agents/skills/android/camera/camerax` |
| Audio / voice | `.agents/skills/android/media` |
| R8 / release | `.agents/skills/android/performance/r8-analyzer` |
