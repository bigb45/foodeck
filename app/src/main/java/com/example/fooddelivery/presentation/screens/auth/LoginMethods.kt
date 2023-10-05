package com.example.fooddelivery.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compose.facebook_color
import com.example.compose.google_color
import com.example.compose.seed
import com.example.fooddelivery.R
import com.example.fooddelivery.presentation.components.Hyperlink
import com.example.fooddelivery.presentation.ui.theme.FoodDeliveryTheme


@Composable
fun LoginMethods(navController: NavController) {
    FoodDeliveryTheme {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()

        ) {

            Box {
                Image(
                    painter = painterResource(id = R.drawable.loginheader),
                    contentDescription = null,
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
                Row(
                    Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painterResource(id = R.drawable.foodeck), contentDescription = null)
                    Text(
                        text = "Foodeck",
                        style = TextStyle(
                            fontSize = 34.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                        ),

                        )

                }
            }
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,

                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                    .verticalScroll(rememberScrollState(), true)
            ) {

                LoginButton(
                    text = "Login with Google", onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = google_color
                    ),
                )
                LoginButton(
                    text = "Login with Facebook", onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = facebook_color
                    ),
                )
                LoginButton(
                    text = "Login via Email", onClick = {
                                                        navController.navigate("email_login")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = seed
                    ),
                )


                OutlinedButton(modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .fillMaxWidth()
                    .height(62.dp),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        navController.navigate("create_account")
                    }) {
                    Text(
                        text = "Create an account",
                        Modifier.padding(8.dp), style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight(700),
                            textAlign = TextAlign.Center)
                    )
                }

                Hyperlink(
                    text = "By signing up, you are agreeing to our Terms & Conditions",
                    hyperLinkText = "Terms & Conditions",

                )
            }

        }
    }
}


@Composable
fun LoginButton(
    text: String, onClick: () -> Unit, colors: ButtonColors, trailingIcon: ImageVector? = null
) {
    Button(
        onClick,
        colors = colors,
        modifier = Modifier
            .padding(top = 20.dp, bottom = 20.dp)
            .fillMaxWidth()
            .height(62.dp),
        shape = RoundedCornerShape(16.dp)

    ) {
        if (trailingIcon != null) androidx.compose.material3.Icon(
            imageVector = trailingIcon, contentDescription = null
        )
        Text(
            text, Modifier.padding(8.dp), style = TextStyle(
                fontSize = 17.sp,
                fontWeight = FontWeight(700),
                textAlign = TextAlign.Center,
            )
        )
    }

}

