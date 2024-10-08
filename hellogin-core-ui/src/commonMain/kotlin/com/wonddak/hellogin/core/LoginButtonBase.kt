package com.wonddak.hellogin.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource


@Composable
fun <T> LoginButtonBase(
    loginRequester: LoginRequester<T>,
    tokenResultHandler: TokenResultHandler<T>,
    getButtonColor: @Composable () -> ButtonColors,
    getBorderStroke: () -> BorderStroke,
    getIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.WithText("Sign in with GitHub"),
    shape: Shape = ButtonDefaults.shape,
    fontSize: TextUnit = 14.sp,
) {
    val scope = rememberCoroutineScope()

    val requestLogin = {
        scope.launch {
            loginRequester.requestLogin(tokenResultHandler)
        }
    }

    if (type is ButtonType.WithText) {
        val horizontalPadding = 12.dp
        val iconTextPadding = 10.dp
        Button(
            modifier = modifier.height(44.dp),
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            onClick = {
                requestLogin()
            },
            shape = shape,
            colors = getButtonColor(),
            border = getBorderStroke(),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                getIcon()
                Spacer(modifier = Modifier.width(iconTextPadding))
                Text(
                    text = type.text,
                    maxLines = 1,
                    fontSize = fontSize,
                    fontFamily = getFont()
                )
            }
        }
    } else {
        Button(
            modifier = modifier.size(44.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {
                requestLogin()
            },
            shape = shape,
            colors = getButtonColor(),
            border = getBorderStroke(),
        ) {
            getIcon()
        }
    }
}
