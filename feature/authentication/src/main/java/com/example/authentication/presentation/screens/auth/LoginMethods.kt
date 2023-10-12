package com.example.authentication.presentation.screens.auth

import android.widget.Toast
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.authentication.presentation.components.Hyperlink
import com.example.authentication.presentation.components.PrimaryButton
import com.example.compose.facebook_color
import com.example.compose.google_color
import com.example.compose.seed
import com.example.core.ui.theme.FoodDeliveryTheme
import com.example.fooddelivery.R
import com.example.fooddelivery.presentation.components.SecondaryButton


@Composable
fun LoginMethods(
    navController: NavController,
    onGoogleSignInClick: () -> Unit,
    state: SignInState,
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }
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

                PrimaryButton(
                    text = "Login with Google", onClick = onGoogleSignInClick,
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = google_color
                    ),
                )

                PrimaryButton(
                    text = "Login with Facebook",
                    onClick = {
//                        val intent = Intent(context, FacebookLogin::class.java)
//                        startActivity(context, intent, null)
                        navController.navigate("facebook_login")
                    },
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = facebook_color
                    ),
                )
                PrimaryButton(
                    text = "Login via Email",
                    onClick = {
                        navController.navigate("email_login")
                    },
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = seed
                    ),
                )


                SecondaryButton(text = "Create an account", enabled = true, onClick = {
                    navController.navigate("create_account")
                })

                Hyperlink(
                    text = "By signing up, you are agreeing to our Terms & Conditions",
                    hyperLinkText = "Terms & Conditions",

                    )
            }

        }
    }
}
