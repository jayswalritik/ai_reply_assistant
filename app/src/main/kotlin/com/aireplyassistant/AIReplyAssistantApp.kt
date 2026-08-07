package com.aireplyassistant

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for AI Reply Assistant.
 *
 * Responsibilities:
 * - Initializes Hilt dependency injection container
 * - Serves as entry point for application-level setup
 *
 * Hilt uses this annotation to generate the component graph at compile time.
 * All Activities, Services, and ViewModels decorated with @AndroidEntryPoint
 * will have dependencies automatically injected.
 */
@HiltAndroidApp
class AIReplyAssistantApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Application initialization - can add logging, crash handlers, etc. here
    }
}
