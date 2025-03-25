package com.dominikgold.composedbudgets.android.design.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.dominikgold.composedbudgets.android.design.TextStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigationIcon: ImageVector? = null,
    overrideNavigationIconClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {

    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title, style = TextStyles.HeadlineLarge)
        },
        navigationIcon = {
            if (navigationIcon != null) {
                // TODO fix navigation
//                val navController = LocalNavController.current
//                IconOnlyButton(
//                    onClick = {
//                        if (overrideNavigationIconClick != null) {
//                            overrideNavigationIconClick()
//                        } else {
//                            navController.popBackStack()
//                        }
//                    },
//                    icon = navigationIcon,
//                )
            }
        },
        actions = actions,
    )
}
