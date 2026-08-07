# Project Structure Reference

Quick reference guide to the AI Reply Assistant project structure.

## Root Level Files

```
ai-reply-assistant/
├── build.gradle.kts              # Root gradle config (plugins, versions)
├── settings.gradle.kts           # Project modules and repositories
├── gradle.properties             # Global gradle settings
├── .gitignore                    # Git ignore rules
├── README.md                     # Project overview
├── DEVELOPMENT.md                # Development guidelines & best practices
├── PHASE_CHECKLIST.md            # Phase tracking and progress
├── PROJECT_STRUCTURE.md          # This file
└── app/                          # Main application module
```

## App Module

```
app/
├── build.gradle.kts              # App-level gradle configuration
├── proguard-rules.pro            # Code obfuscation rules
└── src/
    ├── main/
    │   ├── kotlin/com/aireplyassistant/
    │   │   ├── AIReplyAssistantApp.kt       # Application class (Hilt)
    │   │   ├── di/
    │   │   │   └── AppModule.kt             # Dependency injection
    │   │   ├── core/                        # Utilities & Constants
    │   │   │   ├── constants/
    │   │   │   ├── extensions/
    │   │   │   └── utils/
    │   │   ├── data/                        # Data Layer
    │   │   │   ├── api/                     # API clients
    │   │   │   ├── datasource/              # Data sources (local/remote)
    │   │   │   ├── mapper/                  # Model mappers
    │   │   │   └── repository/              # Repository implementations
    │   │   ├── domain/                      # Domain Layer
    │   │   │   ├── model/                   # Entities
    │   │   │   ├── repository/              # Repository interfaces
    │   │   │   └── usecase/                 # Business logic
    │   │   └── presentation/                # Presentation Layer
    │   │       ├── MainActivity.kt          # Main activity
    │   │       ├── keyboard/                # Keyboard IME (Phase 2)
    │   │       ├── accessibility/           # Accessibility (Phase 3)
    │   │       ├── router/                  # AI Router (Phase 4)
    │   │       ├── settings/                # Settings (Phase 7)
    │   │       ├── screen/                  # Full screens
    │   │       ├── component/               # Reusable components
    │   │       ├── viewmodel/               # ViewModels
    │   │       └── ui/
    │   │           └── theme/
    │   │               ├── Theme.kt
    │   │               ├── Color.kt
    │   │               └── Typography.kt
    │   ├── AndroidManifest.xml
    │   └── res/
    │       ├── values/
    │       │   ├── strings.xml              # String resources
    │       │   ├── colors.xml               # Color definitions
    │       │   └── themes.xml               # Android theme
    │       ├── xml/
    │       │   ├── method.xml               # Keyboard metadata
    │       │   ├── accessibility_config.xml # Accessibility metadata
    │       │   ├── backup_rules.xml         # Backup config
    │       │   └── data_extraction_rules.xml# Data extraction rules
    │       └── drawable/                    # Drawable resources (icons, etc)
    ├── test/
    │   └── java/com/aireplyassistant/       # Unit tests
    └── androidTest/
        └── java/com/aireplyassistant/       # Instrumented tests
```

## Key Directories

### di/ - Dependency Injection
Hilt modules that provide singleton instances and dependencies.

**Files**:
- `AppModule.kt` - Main DI module

### core/ - Utilities
Shared utilities, constants, and extensions used across layers.

**Subdirectories**:
- `constants/` - App-wide constants
- `extensions/` - Kotlin extensions
- `utils/` - Utility functions

### data/ - Data Layer
Repositories, data sources, API clients, and data models.

**Subdirectories**:
- `api/` - Retrofit services and HTTP clients
- `datasource/` - Local and remote data sources
- `mapper/` - Convert between models and entities
- `repository/` - Repository implementations

### domain/ - Domain Layer
Business logic, entities, use cases, and repository interfaces.

**Subdirectories**:
- `model/` - Entities and data models
- `repository/` - Repository interfaces (abstraction)
- `usecase/` - Business logic and workflows

### presentation/ - Presentation Layer
UI, ViewModels, Screens, and Components.

**Subdirectories**:
- `keyboard/` - Custom keyboard implementation
- `accessibility/` - Accessibility service
- `router/` - AI routing logic
- `settings/` - Settings screens
- `screen/` - Full-screen UI
- `component/` - Reusable UI components
- `viewmodel/` - State management
- `ui/theme/` - Compose theming

## File Naming Conventions

| Type | Pattern | Example |
|------|---------|---------|
| Activity | `*Activity.kt` | `MainActivity.kt` |
| ViewModel | `*ViewModel.kt` | `KeyboardViewModel.kt` |
| UseCase | `*UseCase.kt` | `GenerateRepliesUseCase.kt` |
| Repository Interface | `*Repository.kt` | `KeyboardRepository.kt` |
| Repository Impl | `*RepositoryImpl.kt` | `KeyboardRepositoryImpl.kt` |
| Service (Android) | `*Service.kt` | `AIKeyboardService.kt` |
| Composable Screen | `*Screen.kt` | `KeyboardScreen.kt` |
| Composable Component | `*Component.kt` | `ReplyCard.kt` |
| Data Source | `*DataSource.kt` | `AccessibilityDataSource.kt` |
| API Client | `*Client.kt` or `*Service.kt` | `LocalAIClient.kt` |

## Module Dependencies

```
Presentation ──→ Domain ←── Data
      ↓           ↓          ↓
           Core (Utils)
```

### Import Rules

✅ **Allowed**:
- Presentation can import from Domain and Core
- Domain can import from Core
- Data can import from Core
- Any layer can import from Core

❌ **Not Allowed**:
- Domain cannot import from Presentation or Data
- Data cannot import from Presentation
- Core never imports from other layers

## Gradle Configuration

### build.gradle.kts (Root)
- Plugin versions (Kotlin, Android, Hilt)
- Global build settings

### app/build.gradle.kts (App)
- Android SDK configuration
- Kotlin compilation settings
- Jetpack Compose setup
- All dependencies
- Hilt kapt configuration

### gradle.properties
- JVM arguments
- Android project settings
- Kotlin code style

### settings.gradle.kts
- Module definitions
- Repository configurations

## Android Manifest

**Declared Components**:
- `MainActivity` - Main activity
- `AIKeyboardService` - Custom keyboard (Phase 2)
- `AIAccessibilityService` - Accessibility service (Phase 3)

**Permissions**:
- `INTERNET` - Cloud API calls
- `BIND_ACCESSIBILITY_SERVICE` - Accessibility service
- `ACCESS_NETWORK_STATE` - Network checks

## Phase-by-Phase Structure

### Phase 1 ✓
- Project setup
- Gradle configuration
- DI foundation
- Theme setup

### Phase 2 →
- Add: `presentation/keyboard/`
- Add: `domain/usecase/Handle*.kt`
- Add: `data/repository/Keyboard*.kt`

### Phase 3
- Add: `presentation/accessibility/`
- Add: `domain/model/MessageContext.kt`
- Add: `data/datasource/AccessibilityDataSource.kt`

### Phase 4
- Add: `presentation/router/`
- Add: `domain/model/AIMode.kt`
- Add: `domain/usecase/DetermineAIMode*.kt`

### Phase 5
- Add: `data/api/LocalAIClient.kt`
- Add: `data/datasource/LocalAIDataSource.kt`
- Add: `domain/usecase/Generate*.kt`

### Phase 6
- Add: `data/api/CloudAIService.kt`
- Add: `data/api/provider/`
- Add: `domain/usecase/GenerateCloud*.kt`

### Phase 7
- Add: `presentation/settings/`
- Add: `domain/usecase/Save*.kt`
- Add: `data/repository/SettingsRepository*.kt`

### Phase 8
- Add: `test/java/...`
- Add: `androidTest/java/...`

---

**Last Updated**: Phase 1 Complete
**Next Phase**: Phase 2 - Custom Keyboard
