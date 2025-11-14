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
import androidx.lifecycle.viewmodel.compose.viewModel
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

        composable("home") {
            HomeScreen(navController)
        }

        // menerima list string
        composable("second/{items}") { backStackEntry ->
            val items = backStackEntry.arguments?.getString("items") ?: ""
            SecondScreen(navController, items)
        }
    }
}

// 🏠 HOME SCREEN — pakai ViewModel (Commit 4)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }

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

        // Submit → tambah nama
        Button(
            onClick = {
                viewModel.addName(name)
                name = ""
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(text = stringResource(id = R.string.button_click))
        }

        // Finish → navigate sambil kirim list
        Button(
            onClick = {
                val itemsString = viewModel.names.joinToString(", ")
                navController.navigate("second/$itemsString")
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(text = stringResource(id = R.string.button_navigate))
        }

        // List tampilan
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            items(viewModel.names) { item ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}

// 🎉 SECOND SCREEN — tampilkan string result (Commit 4 requirement)
@Composable
fun SecondScreen(navController: NavController, items: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Result Content:",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = items,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 24.dp)
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
