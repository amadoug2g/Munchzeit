# MunchZeit

[![Android CI](https://github.com/amadoug2g/MunchZeit/actions/workflows/android.yml/badge.svg)](https://github.com/amadoug2g/MunchZeit/actions/workflows/android.yml)

A modern Android app to discover and explore recipes

## Features
- Browse recipes from an API
- View detailed recipe pages
- Filter by tags
- Smooth navigation (list → detail)
- Built with clean architecture

## Tech Stack
- **Language:** Kotlin
- **Architecture:** Clean Architecture (domain + app)
- **Async:** Coroutines & Flow
- **Networking:** Retrofit
- **Images:** Coil
- **DI:** Custon ServiceLocator
- **Testing:** JUnit 5, kotlinx-coroutines-test

## Project Structure
- `:core` → pure Kotlin module (models, use cases, contracts)
- `:app` → Android app module (data layer, UI, DI, presentation)

## Getting Started
Clone the repository and open in **Android Studio**:

```bash
git clone https://github.com/amadoug2g/MunchZeit.git

