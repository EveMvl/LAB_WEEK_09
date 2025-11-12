package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("second") { SecondScreen(navController) }
    }
}

// 🏠 Home Screen
@Composable
fun HomeScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var items by remember { mutableStateOf(listOf("Tanu", "Tina", "Tono")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(id = R.string.enter_item),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Enter name...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Tombol Submit
        Button(
            onClick = {
                if (name.isNotBlank()) {
                    items = items + name
                    name = ""
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(text = stringResource(id = R.string.button_click))
        }

        // Tombol Navigate
        Button(
            onClick = { navController.navigate("second") },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(text = stringResource(id = R.string.button_navigate))
        }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(items) { item ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

// ✨ Second Screen
@Composable
fun SecondScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Navigation Success!",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(onClick = { navController.navigate("home") }) {
            Text("Back to Home")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        HomeScreen(navController = rememberNavController())
    }
}
