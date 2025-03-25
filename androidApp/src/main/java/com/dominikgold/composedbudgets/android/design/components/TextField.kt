package com.dominikgold.composedbudgets.android.design.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.dominikgold.composedbudgets.android.design.Colors
import com.dominikgold.composedbudgets.android.design.TextStyles
import kotlinx.coroutines.delay

@Suppress("LongParameterList")
@Composable
fun StyledOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    error: String = "",
    textStyle: TextStyle = TextStyles.ParagraphBold,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onFocusChange: (Boolean) -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    focusOnAppearance: Boolean = false,
) {
    val focusRequester = remember { FocusRequester() }
    Column(modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = hint, style = textStyle, color = Colors.TextLight.dynamic())
            },
            isError = error.isNotEmpty(),
            textStyle = textStyle,
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it.isFocused)
                }
                .focusRequester(focusRequester),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = error,
            style = TextStyles.Caption,
            color = Colors.TulipRed.dynamic()
        )
    }
    if (focusOnAppearance) {
        LaunchedEffect(key1 = Unit) {
            @Suppress("MagicNumber") (delay(50))
            focusRequester.requestFocus()
        }
    }
}
