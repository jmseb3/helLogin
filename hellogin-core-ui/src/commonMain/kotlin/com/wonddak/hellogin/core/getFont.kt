package com.wonddak.hellogin.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import io.github.jmseb3.hellogin_core_ui.generated.resources.Res
import io.github.jmseb3.hellogin_core_ui.generated.resources.roboto_medium
import org.jetbrains.compose.resources.Font

@Composable
fun getFont() = FontFamily(
    Font(Res.font.roboto_medium, FontWeight.Medium, FontStyle.Normal)
)