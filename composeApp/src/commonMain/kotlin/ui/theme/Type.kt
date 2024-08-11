package ui.theme

// Type.kt

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import magnumopus.composeapp.generated.resources.OpenSans_Bold
import magnumopus.composeapp.generated.resources.OpenSans_Italic
import magnumopus.composeapp.generated.resources.OpenSans_Medium
import magnumopus.composeapp.generated.resources.OpenSans_MediumItalic
import magnumopus.composeapp.generated.resources.OpenSans_Regular
import magnumopus.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

// Declare the font families
@Composable
fun AppFont() = FontFamily(
    Font(Res.font.OpenSans_Regular),
    Font(Res.font.OpenSans_Italic, style = FontStyle.Italic),
    Font(Res.font.OpenSans_Medium, FontWeight.Medium),
    Font(Res.font.OpenSans_MediumItalic, FontWeight.Medium, style = FontStyle.Italic),
    Font(Res.font.OpenSans_Bold, FontWeight.Bold),
    Font(Res.font.OpenSans_Italic, FontWeight.Bold, style = FontStyle.Italic)
)


@Composable
fun MyTypography() = Typography().run {
    val fontFamily = AppFont()

    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily),
        displayMedium = displayMedium.copy(fontFamily = fontFamily),
        displaySmall = displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily =  fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
        labelLarge = labelLarge.copy(fontFamily = fontFamily),
        labelMedium = labelMedium.copy(fontFamily = fontFamily),
        labelSmall = labelSmall.copy(fontFamily = fontFamily)
    )
}