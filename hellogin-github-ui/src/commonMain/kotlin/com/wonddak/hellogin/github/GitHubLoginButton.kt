package com.wonddak.hellogin.github

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wonddak.hellogin.core.ButtonTheme
import com.wonddak.hellogin.core.ButtonType
import com.wonddak.hellogin.core.LoginButtonBase
import com.wonddak.hellogin.core.TokenResultHandler
import com.wonddak.hellogin.github.network.model.GithubResult
import io.github.jmseb3.hellogin_github_ui.generated.resources.Res
import io.github.jmseb3.hellogin_github_ui.generated.resources.github_mark
import io.github.jmseb3.hellogin_github_ui.generated.resources.github_mark_white
import org.jetbrains.compose.resources.painterResource

/**
 * GithubLoginButton
 * @param[tokenResultHandler] tokenResultHandler When startLogin
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
    tokenResultHandler: TokenResultHandler<GithubResult>,
    modifier: Modifier = Modifier,
    type : ButtonType = ButtonType.WithText("Sign in with GitHub"),
    mode : ButtonTheme = ButtonTheme.Light,
    shape: Shape = ButtonDefaults.shape,
    fontSize: TextUnit = 14.sp,
) {
    LoginButtonBase(
        GithubLoginHelper,
        tokenResultHandler,
        getButtonColor = {
            val containerColor = when (mode) {
                ButtonTheme.Light -> Color(0xFFFFFFFF)
                ButtonTheme.Dark -> Color(0xFF131314)
            }

            val contentColor = when (mode) {
                ButtonTheme.Light -> Color(0xFF1F1F1F)
                ButtonTheme.Dark -> Color(0xFFE3E3E3)
            }

            ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = contentColor)
        },
        getBorderStroke = {
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
            borderStroke
        },
        getIcon = {
            val res = when (mode) {
                ButtonTheme.Light -> Res.drawable.github_mark

                ButtonTheme.Dark -> Res.drawable.github_mark_white

            }
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(res),
                contentDescription = "githubIcon"
            )
        },
        modifier = modifier,
        type = type,
        shape = shape,
        fontSize = fontSize
    )
}