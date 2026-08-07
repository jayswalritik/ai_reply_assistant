# Setup Instructions for AI Reply Assistant

Follow these steps to get the project running on your machine.

## Prerequisites

- ✅ Android Studio (latest version)
- ✅ Java 17+ (comes with Android Studio)
- ✅ Git (optional, but recommended)

**Do NOT use VS Code for Android development** - use Android Studio instead.

---

## Step 1: Create Project Structure

In your "Reply Assistant" folder, create these directories:

```bash
mkdir -p app/src/main/kotlin/com/aireplyassistant/di
mkdir -p app/src/main/kotlin/com/aireplyassistant/presentation/ui/theme
mkdir -p app/src/main/res/values
mkdir -p app/src/main/res/xml
mkdir -p app/src/test/java/com/aireplyassistant
mkdir -p app/src/androidTest/java/com/aireplyassistant
```

Or manually create folders in your file explorer.

---

## Step 2: Create Root Files (4 files)

These go in the **"Reply Assistant" root directory** (top level):

### 2.1 `build.gradle.kts`
```gradle
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("com.android.library") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
}

task("clean") {
    delete(rootProject.buildDir)
}
```

### 2.2 `settings.gradle.kts`
```gradle
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AI Reply Assistant"
include(":app")
```

### 2.3 `gradle.properties`
```properties
org.gradle.jvmargs=-Xmx2048m -XX:MaxPermSize=512m
org.gradle.daemon=true
android.useAndroidX=true
android.enableJetifier=true
kotlin.code.style=official
```

### 2.4 `.gitignore`
```
.gradle/
build/
*.gradle.lock
.idea/
*.iml
.DS_Store
*.apk
*.ap_
*.class
bin/
gen/
out/
local.properties
gradle/wrapper/gradle-wrapper.jar
app/build/
*.aar
lint-results.xml
lint-results-debug.html
.DS_Store
Thumbs.db
.vscode/
*.swp
*.swo
*~
.kotlin/
*.kt.bak
**/build/reports/
**/build/test-results/
app/src/main/gen/
```

---

## Step 3: Create App Files (2 files)

These go in **app/** directory:

### 3.1 `app/build.gradle.kts`
[Too long to show here - copy from README output file]

### 3.2 `app/proguard-rules.pro`
[Too long to show here - copy from README output file]

---

## Step 4: Create Manifest

### 4.1 `app/src/main/AndroidManifest.xml`
[Copy from README output file]

---

## Step 5: Create Kotlin Files

These go in **app/src/main/kotlin/com/aireplyassistant/**:

### 5.1 `app/src/main/kotlin/com/aireplyassistant/AIReplyAssistantApp.kt`
[Copy from README output file]

### 5.2 `app/src/main/kotlin/com/aireplyassistant/di/AppModule.kt`
[Copy from README output file]

### 5.3 `app/src/main/kotlin/com/aireplyassistant/presentation/MainActivity.kt`
[Copy from README output file]

### 5.4 Theme Files (3 files)
- `app/src/main/kotlin/com/aireplyassistant/presentation/ui/theme/Theme.kt`
- `app/src/main/kotlin/com/aireplyassistant/presentation/ui/theme/Color.kt`
- `app/src/main/kotlin/com/aireplyassistant/presentation/ui/theme/Typography.kt`

[Copy from README output file]

---

## Step 6: Create Resource Files

These go in **app/src/main/res/**:

### 6.1 `app/src/main/res/values/strings.xml`
[Copy from README output file]

### 6.2 `app/src/main/res/values/colors.xml`
[Copy from README output file]

### 6.3 `app/src/main/res/values/themes.xml`
[Copy from README output file]

### 6.4 XML Config Files (4 files)
- `app/src/main/res/xml/method.xml`
- `app/src/main/res/xml/accessibility_config.xml`
- `app/src/main/res/xml/backup_rules.xml`
- `app/src/main/res/xml/data_extraction_rules.xml`

[Copy from README output file]

---

## Step 7: Create Documentation Files

These go in **root** directory:

- `README.md`
- `DEVELOPMENT.md`
- `PHASE_CHECKLIST.md`
- `PROJECT_STRUCTURE.md`

[Already provided in outputs]

---

## Step 8: Open in Android Studio

1. Close VS Code
2. **Open Android Studio**
3. Click **File** → **Open**
4. Select your "Reply Assistant" folder
5. Click **OK**
6. Wait for Gradle sync (can take 2-3 minutes)
7. You should see "BUILD SUCCESSFUL" at the bottom

---

## Step 9: Verify Build

1. Click **Build** → **Make Project**
2. Watch the console at the bottom
3. Should see: `BUILD SUCCESSFUL`

If you see errors, they're likely:
- Missing Java 17 → Install via Android Studio SDK Manager
- Gradle sync issues → File → Sync Now
- Missing Android SDK → Run SDK Manager and install API 34

---

## Step 10: Run the App

1. Click the **Play** button (or **Shift+F10**)
2. Select an emulator or connected device
3. App should launch and show "Phase 1: Project Setup Complete ✓"

---

## File Reference

**Total Files**: 24
- **Root files**: 8 (gradle configs + docs)
- **App files**: 2
- **Manifest**: 1
- **Kotlin files**: 4
- **Resource files**: 7
- **Config XML**: 4

---

## If Something Goes Wrong

### Error: "Failed to sync Gradle"
- **Solution**: File → Sync Now → Wait

### Error: "Java 17 not found"
- **Solution**: Tools → SDK Manager → SDK Tools → Install Java 17

### Error: "Android API 34 not installed"
- **Solution**: Tools → SDK Manager → API Levels → Install API 34

### Error: "Cannot find symbol"
- **Solution**: Build → Clean Project → Build → Make Project

---

## What's Next?

Once you see **"BUILD SUCCESSFUL"**, you're ready for **Phase 2: Custom Keyboard**.

Let me know when you've completed these steps!

---

## Quick Copy Checklist

- [ ] Created directory structure
- [ ] Created 4 root files (gradle configs + gitignore)
- [ ] Created 2 app files (build.gradle, proguard)
- [ ] Created AndroidManifest.xml
- [ ] Created 4 Kotlin files (App, AppModule, MainActivity, Theme)
- [ ] Created 7 resource files (strings, colors, themes, xml configs)
- [ ] Created 4 documentation files
- [ ] Opened in Android Studio
- [ ] Gradle sync completed
- [ ] Build successful

---

**Next Step**: Once build is successful, reply with:
> "Build successful in Android Studio ✅"

Then I'll start **Phase 2: Custom Keyboard Implementation**.
