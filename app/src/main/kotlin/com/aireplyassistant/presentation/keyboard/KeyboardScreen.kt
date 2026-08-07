package com.aireplyassistant.presentation.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aireplyassistant.presentation.keyboard.components.*

@Composable
fun KeyboardScreen(
    viewModel: KeyboardViewModel,
    onCharacterInput: (String) -> Unit,
    onBackspace: () -> Unit,
    onSpace: () -> Unit,
    onEnter: () -> Unit,
    onAIButtonPressed: () -> Unit,
    onReplySelected: (String) -> Unit
) {
    val keyboardMode by viewModel.keyboardMode.collectAsStateWithLifecycle()
    val isShiftEnabled by viewModel.isShiftEnabled.collectAsStateWithLifecycle()
    val showReplySuggestions by viewModel.showReplySuggestions.collectAsStateWithLifecycle()
    val replySuggestions by viewModel.replySuggestions.collectAsStateWithLifecycle()
    val isLoadingReplies by viewModel.isLoadingReplies.collectAsStateWithLifecycle()
    val recentEmojis by viewModel.recentEmojis.collectAsStateWithLifecycle()
    val showProviderMenu by viewModel.showProviderMenu.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(vertical = 2.dp)
    ) {
        if (showProviderMenu) {
            AIProviderMenu(
                onProviderSelected = { viewModel.onProviderSelected(it) },
                onDismiss = { viewModel.dismissProviderMenu() }
            )
        }

        ReplySuggestionPanel(
            isVisible = showReplySuggestions,
            suggestions = replySuggestions,
            onReplySelected = onReplySelected,
            isLoading = isLoadingReplies
        )

        when (keyboardMode) {
            KeyboardMode.EMOJI -> EmojiPicker(
                recentEmojis = recentEmojis,
                onEmojiSelected = { viewModel.addEmojiToRecents(it); onCharacterInput(it) },
                modifier = Modifier.height(180.dp)
            )
            KeyboardMode.GIF -> GifPicker(onGifSelected = { }, modifier = Modifier.height(180.dp))
            KeyboardMode.ALPHA -> AlphaLayout(isShiftEnabled, onCharacterInput, onBackspace, viewModel::toggleShift)
            KeyboardMode.NUMERIC -> NumericLayout(onCharacterInput, onBackspace, { viewModel.setKeyboardMode(KeyboardMode.SYMBOLS) })
            KeyboardMode.SYMBOLS -> SymbolsLayout(onCharacterInput, onBackspace, { viewModel.setKeyboardMode(KeyboardMode.NUMERIC) })
        }

        BottomRow(
            keyboardMode = keyboardMode,
            onCharacterInput = onCharacterInput,
            onBackspace = onBackspace,
            onSpace = onSpace,
            onEnter = onEnter,
            onAIButtonPressed = onAIButtonPressed,
            onModeSwitch = { viewModel.setKeyboardMode(it) }
        )
    }
}

@Composable
private fun AlphaLayout(isShiftEnabled: Boolean, onCharacterInput: (String) -> Unit, onBackspace: () -> Unit, onShiftToggle: () -> Unit) {
    KeyboardRow(keys = "QWERTYUIOP".map { val d = if (isShiftEnabled) it.toString() else it.lowercaseChar().toString(); KeyboardKeyData(label = d, onClick = { onCharacterInput(d) }) })
    KeyboardRow(keys = "ASDFGHJKL".map { val d = if (isShiftEnabled) it.toString() else it.lowercaseChar().toString(); KeyboardKeyData(label = d, onClick = { onCharacterInput(d) }) }, modifier = Modifier.padding(horizontal = 15.dp))
    KeyboardRow(keys = buildList {
        add(KeyboardKeyData(label = if (isShiftEnabled) "⬆️" else "⇧", isSpecial = true, weight = 1.3f, onClick = onShiftToggle))
        addAll("ZXCVBNM".map { val d = if (isShiftEnabled) it.toString() else it.lowercaseChar().toString(); KeyboardKeyData(label = d, onClick = { onCharacterInput(d) }) })
        add(KeyboardKeyData(label = "⌫", isSpecial = true, weight = 1.3f, onClick = onBackspace, onLongPress = onBackspace))
    })
}

@Composable
private fun NumericLayout(onCharacterInput: (String) -> Unit, onBackspace: () -> Unit, onSymbolsSwitch: () -> Unit) {
    KeyboardRow(keys = "1234567890".map { KeyboardKeyData(label = it.toString(), onClick = { onCharacterInput(it.toString()) }) })
    KeyboardRow(keys = "@#$&*-+()/".map { KeyboardKeyData(label = it.toString(), onClick = { onCharacterInput(it.toString()) }) })
    KeyboardRow(keys = buildList {
        add(KeyboardKeyData(label = "=\\<", isSpecial = true, weight = 1.3f, onClick = onSymbolsSwitch))
        addAll(listOf("\"", "'", ":", ";", "!", ",", "?").map { KeyboardKeyData(label = it, onClick = { onCharacterInput(it) }) })
        add(KeyboardKeyData(label = "⌫", isSpecial = true, weight = 1.3f, onClick = onBackspace, onLongPress = onBackspace))
    })
}

@Composable
private fun SymbolsLayout(onCharacterInput: (String) -> Unit, onBackspace: () -> Unit, onNumericSwitch: () -> Unit) {
    KeyboardRow(keys = "~`|•√π÷×¶".map { KeyboardKeyData(label = it.toString(), onClick = { onCharacterInput(it.toString()) }) })
    KeyboardRow(keys = "£¢€¥^°={}".map { KeyboardKeyData(label = it.toString(), onClick = { onCharacterInput(it.toString()) }) })
    KeyboardRow(keys = buildList {
        add(KeyboardKeyData(label = "?123", isSpecial = true, weight = 1.3f, onClick = onNumericSwitch))
        addAll("\\[]<>_±".map { KeyboardKeyData(label = it.toString(), onClick = { onCharacterInput(it.toString()) }) })
        add(KeyboardKeyData(label = "⌫", isSpecial = true, weight = 1.3f, onClick = onBackspace, onLongPress = onBackspace))
    })
}

@Composable
private fun BottomRow(keyboardMode: KeyboardMode, onCharacterInput: (String) -> Unit, onBackspace: () -> Unit, onSpace: () -> Unit, onEnter: () -> Unit, onAIButtonPressed: () -> Unit, onModeSwitch: (KeyboardMode) -> Unit) {
    KeyboardRow(keys = listOf(
        KeyboardKeyData(label = if (keyboardMode == KeyboardMode.ALPHA) "?123" else "ABC", isSpecial = true, weight = 1.4f, onClick = { onModeSwitch(if (keyboardMode == KeyboardMode.ALPHA) KeyboardMode.NUMERIC else KeyboardMode.ALPHA) }),
        KeyboardKeyData(label = ",", isSpecial = true, weight = 1.1f, isImmediate = false, onClick = { onCharacterInput(",") }, onLongPress = { onModeSwitch(KeyboardMode.EMOJI) }, icon = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "😀", fontSize = 10.sp); Text(text = ",", fontSize = 18.sp, lineHeight = 1.sp)
            }
        }),
        KeyboardKeyData(label = "GIF", isSpecial = true, weight = 1.1f, onClick = { onModeSwitch(KeyboardMode.GIF) }),
        KeyboardKeyData(label = "🤖", weight = 1.3f, backgroundColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary, onClick = onAIButtonPressed),
        KeyboardKeyData(label = " ", weight = 3.5f, onClick = onSpace),
        KeyboardKeyData(label = if (keyboardMode == KeyboardMode.EMOJI || keyboardMode == KeyboardMode.GIF) "⌫" else ".", weight = 1.1f, isSpecial = true, onClick = { if (keyboardMode == KeyboardMode.EMOJI || keyboardMode == KeyboardMode.GIF) onBackspace() else onCharacterInput(".") }, onLongPress = { if (keyboardMode != KeyboardMode.EMOJI && keyboardMode != KeyboardMode.GIF) onCharacterInput("?") }),
        KeyboardKeyData(label = "⏎", isSpecial = true, weight = 1.4f, backgroundColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary, onClick = onEnter)
    ), modifier = Modifier.height(52.dp))
}
