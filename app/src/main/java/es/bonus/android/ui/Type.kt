package es.bonus.android.ui

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import es.bonus.android.Fonts

// Set of Material typography styles to start with
val typography = Typography(
        h1 = TextStyle(
                color = Colors.white1,
                fontFamily = Fonts.Inter,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
        ),
        h2 = TextStyle(
                color = Colors.white1,
                fontFamily = Fonts.Inter,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
        ),
        h3 = TextStyle(
                color = Colors.white1,
                fontFamily = Fonts.Inter,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
        ),
        h4 = TextStyle(
                color = Colors.white1,
                fontFamily = Fonts.Inter,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
        ),
        h5 = TextStyle(
                color = Colors.white1,
                fontFamily = Fonts.Inter,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
        ),
        body1 = TextStyle(
                color = Colors.white1,
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
        ),
        body2 = TextStyle(
                color = Colors.white1,
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                fontStyle = FontStyle.Italic
        ),
        button = TextStyle(
                color = Colors.white1,
                fontFamily = Fonts.Inter,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
        ),
        caption = TextStyle(
                color = Colors.accent,
                fontFamily = Fonts.Inter,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
        ),
        subtitle1 = TextStyle(
                color = Colors.white1,
                fontFamily = Fonts.MondaBold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
        ),
        subtitle2 = TextStyle(
                color = Colors.darkBackground,
                fontFamily = Fonts.MondaBold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
        )
)
