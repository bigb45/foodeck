package com.example.fooddelivery.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.seed

@Composable
fun Hyperlink(
    text: String,
    hyperlinks: List<String> = listOf(),
) {
    val hyperlink = "https://example.com"
    val hyperLinkText = "Terms & Conditions"
    val annotatedString = buildAnnotatedString {
        append(text)
        val startIndex = text.indexOf(hyperLinkText)
        val endIndex = startIndex + hyperLinkText.length
        addStyle(
            style = SpanStyle(
                color = seed,
            ), start = startIndex, end = endIndex
        )
        addStringAnnotation(
            tag = "URL", annotation = hyperlink, start = startIndex, end = endIndex
        )

        addStyle(
            style = SpanStyle(
            ), start = 0, end = text.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        text = annotatedString, onClick = {
            annotatedString.getStringAnnotations("URL", it, it).firstOrNull()
                ?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)

                }
        }, modifier = Modifier.padding(8.dp), style = TextStyle(
            fontSize = 17.sp,
            fontWeight = FontWeight(700),
            textAlign = TextAlign.Center,
        )
    )
}
