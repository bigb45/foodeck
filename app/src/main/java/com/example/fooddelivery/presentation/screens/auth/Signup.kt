package com.example.fooddelivery.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.compose.gray
import com.example.fooddelivery.presentation.ui.theme.FoodDeliveryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Signup(navController: NavController) {
    FoodDeliveryTheme {
        Scaffold(Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = { Text("Create an Account", style = typography.titleMedium) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                })
        }) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(
                    modifier = Modifier
                        .padding(it)
                        .height(14.dp)
                        .fillMaxWidth()
                        .background(gray)
                        .fillMaxWidth()
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(23.dp)


                ) {
                    Text(text = "Input your credentials", style = typography.titleLarge)

                    Column(

                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        var username by remember { mutableStateOf("") }
                        OutlinedTextField(value = username, onValueChange = { newUsername ->
                            username = newUsername
                        }, label = { Text("Username") },

                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(value = username, onValueChange = { newUsername ->
                            username = newUsername
                        }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(value = username, onValueChange = { newUsername ->
                            username = newUsername
                        }, label = { Text("Phone number") },

                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(value = username, onValueChange = { newUsername ->
                            username = newUsername
                        }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth()
                        )

                    }

                }
                Spacer(
                    modifier = Modifier.height(40.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(23.dp)
                ) {
                    Button(
                        onClick = { /*TODO*/ }, modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),

                    ) {
                        Text("Create an Account")
                    }
                    OutlinedButton(
                        onClick = {}, modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Login instead")

                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun SignupPrev() {
    Signup(navController = rememberNavController())
}