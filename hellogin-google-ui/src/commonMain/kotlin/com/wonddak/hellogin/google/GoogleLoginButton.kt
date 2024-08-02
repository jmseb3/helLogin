package com.wonddak.hellogin.google

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import com.wonddak.hellogin.core.ButtonTheme
import com.wonddak.hellogin.core.ButtonType
import com.wonddak.hellogin.core.getFont
import io.github.jmseb3.hellogin_google_ui.generated.resources.Res
import io.github.jmseb3.hellogin_google_ui.generated.resources.ic_google
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

/**
 * GoogleLoginButton
 * @param[modifier] Modifier
 * @param[type] ButtonType
 * @param[mode] ButtonTheme
 * @param[shape] shape of Button
 * @param[fontSize] size of text
 * @param[tokenResultHandler] if not null, change tokenResultHandler When startLogin
 *
 * @see[ButtonType] type info
 * @see[ButtonTheme] theme info
 */
@Composable
fun GoogleLoginButton(
    modifier: Modifier = Modifier,
    type : ButtonType = ButtonType.WithText("Sign in with Google"),
    mode : ButtonTheme = ButtonTheme.Light,
    shape: Shape = ButtonDefaults.shape,
    fontSize: TextUnit = 14.sp
) {
    val scope = rememberCoroutineScope()
    if (type is ButtonType.WithText) {
        val horizontalPadding = 12.dp
        val iconTextPadding = 10.dp
        Button(
            modifier = modifier.height(44.dp),
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            onClick = {
                scope.launch {
                    GoogleLoginHelper.requestLogin()
                }
            },
            shape = shape,
            colors = getButtonColor(mode),
            border = getBorderStroke(mode),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GoogleIcon()
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
                scope.launch {
                    GoogleLoginHelper.requestLogin()
                }
            },
            shape = shape,
            colors = getButtonColor(mode),
            border = getBorderStroke(mode),
        ) {
            GoogleIcon()
        }
    }

}

@Composable
internal fun GoogleIcon() {
    Image(
        modifier = Modifier.size(20.dp),
        painter = painterResource(Res.drawable.ic_google),
        contentDescription = "googleIcon"
    )
}

private fun getBorderStroke(mode: ButtonTheme): BorderStroke {
    val borderStroke = when (mode) {
        ButtonTheme.Light -> BorderStroke(
            width = 1.dp,
            color = Color(0xFF747775),
        )

        ButtonTheme.Dark -> BorderStroke(
            width = 1.dp,
            color = Color(0xFF8E918F),
        )
    }
    return borderStroke
}

@Composable
private fun getButtonColor(mode: ButtonTheme): ButtonColors {
    val containerColor = when (mode) {
        ButtonTheme.Light -> Color(0xFFFFFFFF)
        ButtonTheme.Dark -> Color(0xFF131314)
    }

    val contentColor = when (mode) {
        ButtonTheme.Light -> Color(0xFF1F1F1F)
        ButtonTheme.Dark -> Color(0xFFE3E3E3)
    }

    return ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = contentColor)
}