package com.aireplyassistant.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.aireplyassistant.presentation.ui.theme.AIReplyAssistantTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity - Main entry point for the Android Companion App.
 *
 * Responsibilities:
 * - Displays settings and configuration UI
 * - Manages permissions requests
 * - Provides keyboard/accessibility service setup flows
 *
 * Phase 1 Note: This is a placeholder. Full UI implementation will follow in later phases.
 * Currently shows minimal UI to establish the foundation.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AIReplyAssistantTheme {
                Scaffold { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainScreenContent()
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreenContent() {
    Column {
        Text(
            text = "AI Reply Assistant",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "v1.0.0",
            fontSize = 12.sp
        )
        Text(
            text = "Phase 1: Project Setup Complete ✓"
        )
    }
}
