# Development Guidelines

This document outlines the development workflow and best practices for AI Reply Assistant.

## Golden Rules (Never Break)

1. **AI activates only after 🤖 is pressed** – No automatic processing
2. **Manual typing always works** – Keyboard is functional without AI
3. **Never ask for Cloud AI automatically** – Ask user first in Adaptive mode
4. **Never store conversations** – Immediate deletion after reply generation
5. **Keyboard performance is critical** – No lag or interruptions
6. **Accessibility operates on demand** – Never continuous monitoring
7. **V1 supports text messages only** – No images, video, etc.

## Architecture Principles

### Clean Architecture Layers

```
Presentation (UI) → Domain (Logic) → Data (Storage/API)
                ↓
             Core (Utils)
```

**Presentation**: Activities, Composables, ViewModels, State Management
**Domain**: Use Cases, Entities, Repository Interfaces (abstract)
**Data**: Repository Implementations, API Clients, Database
**Core**: Constants, Extensions, Utilities

### Dependency Direction

Dependencies flow **inward** only:
```
Presentation → Domain ← Data
     ↓          ↓        ↓
        Core (Utilities)
```

Domain layer should NEVER depend on Presentation or Data layers.

## File Organization

```
com/aireplyassistant/
├── di/                      # Hilt modules
├── core/                    # Utilities, Constants, Extensions
│   ├── constants/
│   ├── extensions/
│   └── utils/
├── data/                    # Data layer
│   ├── repository/
│   ├── datasource/
│   │   ├── local/
│   │   └── remote/
│   └── mapper/
├── domain/                  # Domain layer
│   ├── model/              # Entities
│   ├── repository/         # Repository interfaces
│   └── usecase/            # Business logic
└── presentation/           # Presentation layer
    ├── screen/             # Full screens
    ├── component/          # Reusable components
    ├── viewmodel/          # ViewModels
    └── ui/
        └── theme/
```

## Naming Conventions

### Files
- **Activity**: `*Activity.kt` (e.g., `MainActivity.kt`)
- **ViewModel**: `*ViewModel.kt` (e.g., `KeyboardViewModel.kt`)
- **UseCase**: `*UseCase.kt` (e.g., `GenerateRepliesUseCase.kt`)
- **Repository**: `*Repository.kt` or `*RepositoryImpl.kt`
- **Service**: `*Service.kt` (e.g., `AIKeyboardService.kt`)

### Classes
- Interfaces: Start with verb or descriptive name (e.g., `ReplyGenerator`, `LocalAIClient`)
- Implementations: `*Impl` suffix (e.g., `ReplyGeneratorImpl`)
- Data classes: `*Data`, `*Response`, `*Request` suffix

### Variables
- Composables: PascalCase (e.g., `ReplyCard()`)
- UI State: `*UiState` suffix (e.g., `ReplyGenerationUiState`)
- Events: `*Event` suffix (e.g., `UserTappedAIButtonEvent`)

## Code Quality Standards

### Must Have
- [x] No placeholder implementations
- [x] No TODOs in production code (track in separate file)
- [x] Meaningful variable/function names
- [x] Small, focused functions (<20 lines preferred)
- [x] Proper error handling
- [x] Dependency injection for all dependencies

### Never Do
- ❌ Tight coupling between layers
- ❌ Direct Activity/Context passing to ViewModels
- ❌ Static references
- ❌ Global variables
- ❌ Hardcoded strings (use String resources)
- ❌ Unhandled exceptions
- ❌ Synchronous network calls

## Testing Strategy

### Unit Tests
- Repository implementations
- Use cases
- ViewModels
- Utilities

### Integration Tests
- Keyboard + Accessibility interaction
- AI Router logic
- API calls

### UI Tests
- Keyboard responsiveness
- Reply insertion
- Settings navigation

## Git Workflow

- Branch per feature: `feature/phase-2-keyboard`, `feature/phase-3-accessibility`
- Commits: `git commit -m "Phase 2: Implement keyboard layout"`
- Small, focused commits for clarity

## Session Workflow

Each Claude session follows this pattern:

1. **Read Specification**
   - Reference `/mnt/project/AI_Reply_Assistant_V1_Master_Specification.pdf`
   - Confirm understanding of current phase

2. **Implement Feature** (one at a time)
   - Create files incrementally
   - Explain every file and why it exists
   - Follow naming conventions
   - Maintain clean architecture

3. **Stop at Milestone**
   - After completing a logical unit (e.g., all keyboard layout files)
   - Present files
   - Wait for user approval before next feature

4. **Never Skip Explanation**
   - Why this class exists
   - How it fits in the architecture
   - Dependencies it has
   - What it's responsible for

## Dependencies

**Production:**
- androidx.core:core-ktx
- androidx.appcompat:appcompat
- androidx.compose.ui:ui
- androidx.material3:material3
- androidx.lifecycle:lifecycle-viewmodel-ktx
- com.google.dagger:hilt-android
- com.squareup.retrofit2:retrofit
- org.jetbrains.kotlinx:kotlinx-coroutines-android
- com.google.code.gson:gson

**Debug/Test:**
- junit
- androidx.test.ext:junit
- androidx.test.espresso:espresso-core
- androidx.compose.ui:ui-test-junit4

## Common Patterns

### Creating a ViewModel
```kotlin
@HiltViewModel
class MyViewModel @Inject constructor(
    private val useCase: MyUseCase
) : ViewModel() {
    // Implementation
}
```

### Creating a Repository
```kotlin
@Singleton
class MyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MyRepository {
    // Implementation
}
```

### Creating a Hilt Module
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object MyModule {
    @Singleton
    @Provides
    fun provideMyDependency(): MyInterface = MyImplementation()
}
```

## Resources

- Master Specification: `/mnt/project/AI_Reply_Assistant_V1_Master_Specification.pdf`
- Design Document: `/mnt/project/AI_Reply_Assistant_V1_Design.pdf`
- Architecture: Clean Architecture principles

---

**Current Phase**: Phase 1 ✓ (Project Setup Complete)
**Next Phase**: Phase 2 (Custom Keyboard)
