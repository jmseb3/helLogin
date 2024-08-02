package com.wonddak.hellogin.github

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wonddak.hellogin.core.ButtonTheme
import com.wonddak.hellogin.core.ButtonType
import com.wonddak.hellogin.core.getFont
import io.github.jmseb3.hellogin_github_ui.generated.resources.Res
import io.github.jmseb3.hellogin_github_ui.generated.resources.github_mark
import io.github.jmseb3.hellogin_github_ui.generated.resources.github_mark_white
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

/**
 * GithubLoginButton
 * @param[modifier] Modifier
 * @param[type] ButtonType
 * @param[mode] ButtonTheme
 * @param[shape] shape of Button
 * @param[fontSize] size of text
 *
 * @see[ButtonType] type info
 * @see[ButtonTheme] theme info
 */
@Composable
fun GithubLoginButton(
    modifier: Modifier = Modifier,
    type : ButtonType = ButtonType.WithText("Sign in with GitHub"),
    mode : ButtonTheme = ButtonTheme.Light,
    shape: Shape = ButtonDefaults.shape,
    fontSize: TextUnit = 14.sp,
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
                    GithubLoginHelper.requestLogin()
                }
            },
            shape = shape,
            colors = getButtonColor(mode),
            border = getBorderStroke(mode),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GoogleIcon(mode)
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
                    GithubLoginHelper.requestLogin()
                }
            },
            shape = shape,
            colors = getButtonColor(mode),
            border = getBorderStroke(mode),
        ) {
            GoogleIcon(mode)
        }
    }

}

@Composable
internal fun GoogleIcon(mode: ButtonTheme) {
    val res = when (mode) {
        ButtonTheme.Light -> Res.drawable.github_mark

        ButtonTheme.Dark -> Res.drawable.github_mark_white

    }
    Image(
        modifier = Modifier.size(20.dp),
        painter = painterResource(res),
        contentDescription = "githubIcon"
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