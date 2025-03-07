package com.example.moodbloom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.moodbloom.navigation.MyAppNavHost




@Composable
internal fun MyApp(
    appState: MyAppState,
) {


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                Modifier.fillMaxSize(),
            ) {
                MyAppNavHost(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.padding(padding)
                        .weight(1f),
                    navController = appState.navController,
                )
            }
        }
    }

}



