# Quiz001 — Authorized Quiz Test Assistant

Android app for connectivity testing against a quiz API that you own or are explicitly authorized to test.

## Current build (v0.2.0)

- HTTPS GET status/health endpoint tester.
- Optional Bearer token input, held in memory only for the current app session.
- HTTP status, response time, and a truncated response body shown as an on-screen log.
- Practice question/options text field.
- GitHub Actions builds a debug APK artifact on pushes to `main`.

## Important scope

This build does **not** connect to MiniPIX private quiz endpoints, submit quiz answers, probe for a hidden correct option, bypass integrity/session checks, capture another app's screen, or auto-tap answers. The practice-answer AI providers are not connected yet. Use only an API and credentials you are authorized to test. Do not put production API keys in a public repository or ship them inside an APK.

## Build

Open **Actions → Android Debug APK** in GitHub and download the `Quiz001-debug-apk` artifact from the latest successful run. The artifact is a ZIP containing the debug APK. This build has not been device-tested in a Virtual Master guest.
