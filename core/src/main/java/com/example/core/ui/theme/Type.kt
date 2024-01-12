package com.example.core.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddelivery.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
val fontName = GoogleFont("Inter")
val inter = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)
val interBold = FontFamily(
    Font(googleFont = fontName, fontProvider = provider, weight = FontWeight.Bold))

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge =  TextStyle(
        fontSize = 20.sp,
        lineHeight = 24.sp,
        fontFamily = inter,
        fontWeight = FontWeight(700),
        color = Color(0xFF000000),
        letterSpacing = 0.75.sp,
    ),
    titleMedium = TextStyle(
        fontSize = 17.sp,
        lineHeight = 22.sp,
        fontFamily = inter,
        fontWeight = FontWeight(700),
        color = Color(0xFF000000),
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

@Preview
@Composable
fun TextStylePreview() {
    Column(
        modifier = Modifier
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
    Text(text = "The Quick Brown Fox", style = Typography.titleLarge)
    Text(text = "The Quick Brown Fox", style = Typography.titleMedium)
    Text(text = "The Quick Brown Fox", style = Typography.titleSmall)
    Text(text = "The Quick Brown Fox", style = Typography.bodyLarge)
    Text(text = "The Quick Brown Fox", style = Typography.bodyMedium)
    Text(text = "The Quick Brown Fox", style = Typography.bodySmall)
    Text(text = "The Quick Brown Fox", style = Typography.labelLarge)
    Text(text = "The Quick Brown Fox", style = Typography.labelMedium)
    Text(text = "The Quick Brown Fox", style = Typography.labelSmall)

    }
}
