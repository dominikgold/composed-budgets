@file:Suppress("LongParameterList")

package com.dominikgold.composedbudgets.android.design.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominikgold.composedbudgets.android.design.Colors
import com.dominikgold.composedbudgets.android.design.ComposedBudgetsTheme
import com.dominikgold.composedbudgets.android.design.TextStyles

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    showIsLoading: Boolean = false,
    isEnabled: Boolean = !showIsLoading,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled,
        shape = RoundedCornerShape(12.dp),
        elevation = null,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    ) {
        PrimaryButtonContent(leadingIcon, text, trailingIcon, showIsLoading)
    }
}

@Composable
fun IconOnlyButton(
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled,
        shape = RoundedCornerShape(12.dp),
        elevation = null,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Colors.Text.dynamic(),
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Icon(icon, contentDescription = null)
    }
}

@Composable
fun TextOnlyButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled,
        shape = RoundedCornerShape(12.dp),
        elevation = null,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(text = text, style = TextStyles.Subtitle)
    }
}

@Composable
fun FloatingCTA(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    showIsLoading: Boolean = false,
    isEnabled: Boolean = !showIsLoading,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled,
        shape = RoundedCornerShape(percent = 100),
        elevation = ButtonDefaults.elevatedButtonElevation(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    ) {
        PrimaryButtonContent(leadingIcon, text, trailingIcon, showIsLoading)
    }
}

@Composable
fun FullWidthCTA(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    showIsLoading: Boolean = false,
    isEnabled: Boolean = !showIsLoading,
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = isEnabled,
        shape = RoundedCornerShape(percent = 100),
        elevation = null,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    ) {
        PrimaryButtonContent(leadingIcon, text, trailingIcon, showIsLoading)
    }
}

@Composable
fun BorderedButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    showIsLoading: Boolean = false,
    isEnabled: Boolean = !showIsLoading,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled,
        shape = RoundedCornerShape(percent = 100),
        border = BorderStroke(1.dp, Colors.FernGreen.dynamic()),
        colors = ButtonDefaults.outlinedButtonColors(),
        elevation = null,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    ) {
        PrimaryButtonContent(leadingIcon, text, trailingIcon, showIsLoading)
    }
}

@Composable
fun DestructiveButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    showIsLoading: Boolean = false,
    isEnabled: Boolean = !showIsLoading,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = isEnabled,
        shape = RoundedCornerShape(percent = 100),
        border = BorderStroke(1.dp, Colors.FernGreen.dynamic()),
        colors = ButtonDefaults.buttonColors(
            containerColor = Colors.TulipRed.dynamic(),
            contentColor = Colors.DaisyWhite.dynamic(),
            disabledContainerColor = Colors.TextLight.dynamic(),
            disabledContentColor = Colors.DaisyWhite.dynamic(),
        ),
        elevation = null,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    ) {
        PrimaryButtonContent(leadingIcon, text, trailingIcon, showIsLoading)
    }
}

@Composable
private fun PrimaryButtonContent(
    leadingIcon: ImageVector?,
    text: String,
    trailingIcon: ImageVector?,
    showIsLoading: Boolean,
) {
    val density = LocalDensity.current
    SubcomposeLayout { constraints ->
        val textContent = subcompose("textContent") {
            Row(verticalAlignment = Alignment.CenterVertically) {
                leadingIcon?.let {
                    Icon(it, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Text(text = text, style = TextStyles.Subtitle)
                trailingIcon?.let {
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(it, contentDescription = null)
                }
            }
        }[0].measure(constraints)
        val textContentHeightDp = with(density) { textContent.height.toDp() }
        val centeredLoadingIndicator = subcompose("centeredLoadingIndicator") {
            val color = LocalContentColor.current
            CircularProgressIndicator(modifier = Modifier.size(textContentHeightDp), strokeWidth = 2.dp, color = color)
        }[0].measure(constraints)
        layout(textContent.width, textContent.height) {
            if (showIsLoading) {
                centeredLoadingIndicator.place(textContent.width / 2 - textContent.height / 2, 0)
            } else {
                textContent.place(0, 0)
            }
        }
    }
}

@Suppress("StringLiteralDuplication")
@Preview
@Composable
private fun PrimaryButtonPreview() {
    ComposedBudgetsTheme {
        Column {
            PrimaryButton(onClick = { }, text = "Preview")
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryButton(
                onClick = { },
                text = "Preview",
                leadingIcon = Icons.Rounded.Check,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryButton(onClick = { }, text = "Preview", leadingIcon = Icons.Rounded.Check, showIsLoading = true)
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryButton(onClick = { }, text = "Preview", modifier = Modifier.fillMaxWidth(), showIsLoading = true)
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryButton(onClick = { }, text = "Preview", showIsLoading = true, isEnabled = true)
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryButton(
                onClick = { },
                text = "Preview",
                modifier = Modifier.fillMaxWidth(),
                showIsLoading = true,
                isEnabled = true
            )
        }
    }
}

@Suppress("StringLiteralDuplication")
@Preview
@Composable
private fun FullWidthCTAPreview() {
    ComposedBudgetsTheme {
        Column {
            FullWidthCTA(onClick = { }, text = "Preview")
            Spacer(modifier = Modifier.height(8.dp))
            FullWidthCTA(
                onClick = { },
                text = "Preview",
                leadingIcon = Icons.Rounded.Check,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            FullWidthCTA(onClick = { }, text = "Preview", leadingIcon = Icons.Rounded.Check, showIsLoading = true)
            Spacer(modifier = Modifier.height(8.dp))
            FullWidthCTA(onClick = { }, text = "Preview", showIsLoading = true, isEnabled = true)
        }
    }
}

@Suppress("StringLiteralDuplication")
@Preview
@Composable
private fun IconOnlyButtonPreview() {
    ComposedBudgetsTheme {
        Column {
            IconOnlyButton(onClick = {}, icon = Icons.Rounded.Close)
            Spacer(modifier = Modifier.height(8.dp))
            IconOnlyButton(onClick = {}, icon = Icons.Rounded.Close, isEnabled = false)
        }
    }
}
