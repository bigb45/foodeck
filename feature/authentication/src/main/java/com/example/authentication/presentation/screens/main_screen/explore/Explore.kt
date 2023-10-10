package com.example.authentication.presentation.screens.main_screen.explore

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.gray2
import com.example.core.ui.theme.inter
import com.example.fooddelivery.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Explore(navController: NavController?) {
    val userAddress = "Building 15, Paris St., Tulkarm, Tulkarm"
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = colorScheme.onPrimary
                        )
                        TextButton(onClick = {/*TODO: navigate to address selection and changing screen*/ }) {
                            Text(
                                userAddress,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                style = typography.titleSmall
                            )
                        }
                    }
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorScheme.primary
                )
            )
        }, modifier = Modifier.fillMaxSize()

    ) {
        Column(
            Modifier.padding(it)
        ) {
            Surface(
                color = colorScheme.primary, modifier = Modifier.fillMaxWidth()
            ) {
                var searchQuery by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = searchQuery,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        containerColor = colorScheme.background,

                        ),
                    maxLines = 1,
                    onValueChange = { newSearch ->
                        searchQuery = newSearch
                    },
                    placeholder = {
                        Text(
                            "Search", style = TextStyle(
                                fontSize = 17.sp,
                                lineHeight = 22.sp,
                                fontFamily = inter,
                                fontWeight = FontWeight(400),
                                color = gray2,
                            )
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = gray2
                        )
                    },
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
//                BENTO SECTION


            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                LargeFoodCard("Food", "Order food you like")
            }
        }
    }
}


@Composable
fun LargeFoodCard(title: String, subtitle: String, imageUri: Uri? = null) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(16))
            .fillMaxWidth()
            .height(160.dp)

    ) {
        Surface(
            color = Color.Red, modifier = Modifier.fillMaxSize()
        ) {

        }
        Image(
//            TODO: replace this with dynamic image from api
            painter = painterResource(id = R.drawable.foodimage),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth()
//                TODO: add gradient to image

        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp),
        ) {
            Text(title, style = typography.bodyMedium)
            Text(subtitle, style = typography.bodySmall)
        }
    }

}