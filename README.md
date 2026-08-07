# AI Reply Assistant V1

Privacy-first Android keyboard that generates AI reply suggestions for text messages.

## Project Structure

```
ai-reply-assistant/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/com/aireplyassistant/
│   │   │   │   ├── AIReplyAssistantApp.kt          # Application class
│   │   │   │   ├── di/
│   │   │   │   │   └── AppModule.kt                # Hilt dependency injection
│   │   │   │   ├── presentation/
│   │   │   │   │   ├── MainActivity.kt             # Companion app UI
│   │   │   │   │   ├── keyboard/                   # Keyboard IME (Phase 2)
│   │   │   │   │   ├── accessibility/              # Accessibility Service (Phase 3)
│   │   │   │   │   └── ui/theme/
│   │   │   │   ├── domain/                         # Business logic (Phase 4+)
│   │   │   │   ├── data/                           # Repositories (Phase 4+)
│   │   │   │   └── core/                           # Utilities & constants
│   │   │   ├── AndroidManifest.xml
│   │   │   └── res/
│   │   │       ├── values/
│   │   │       │   ├── strings.xml
│   │   │       │   ├── colors.xml
│   │   │       │   └── themes.xml
│   │   │       └── xml/
│   │   │           ├── method.xml
│   │   │           ├── accessibility_config.xml
│   │   │           ├── backup_rules.xml
│   │   │           └── data_extraction_rules.xml
│   │   ├── test/                                   # Unit tests
│   │   └── androidTest/                            # Instrumented tests
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## Architecture

**Clean Architecture Layers:**

1. **Presentation Layer** (`presentation/`)
   - UI components (Jetpack Compose)
   - ViewModels
   - Screens and Activities

2. **Domain Layer** (`domain/`)
   - Business logic (use cases)
   - Entities (data models)
   - Repository interfaces

3. **Data Layer** (`data/`)
   - Repository implementations
   - Data sources (Remote, Local)
   - Mappers (Entity ↔ Data Model)

4. **Core Layer** (`core/`)
   - Utilities
   - Constants
   - Extensions

## Tech Stack

- **Kotlin** – Language
- **Jetpack Compose** – UI framework
- **MVVM** – Architecture pattern
- **Hilt** – Dependency injection
- **Retrofit** – HTTP client
- **Coroutines** – Async/concurrency
- **Material Design 3** – UI design system

## Development Phases

- [x] Phase 1: Project Setup
- [ ] Phase 2: Custom Keyboard
- [ ] Phase 3: Accessibility Service
- [ ] Phase 4: AI Router
- [ ] Phase 5: Local AI Integration
- [ ] Phase 6: Cloud AI Integration
- [ ] Phase 7: Settings
- [ ] Phase 8: Testing

## Key Principles

✅ **AI is opt-in** – Only activates when user taps AI button
✅ **Privacy first** – Never stores conversations
✅ **User control** – Choose Local vs Cloud AI
✅ **Manual typing works** – Keyboard is fully functional without AI
✅ **Modular architecture** – Clean separation of concerns

## Building

```bash
./gradlew build
```

## Running

```bash
./gradlew installDebug
```

## Notes

- Minimum SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)
- Java version: 17

---

*Phase 1 completed: Project setup with Gradle configuration, Hilt DI, Material 3 theme, and basic structure.*
