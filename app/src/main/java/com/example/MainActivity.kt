package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.data.AppDatabase
import com.example.data.MusicRepository
import com.example.ui.screens.MusicDashboard
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.MusicViewModel
import com.example.ui.viewmodel.MusicViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Room Database & DAOs
        val database = AppDatabase.getDatabase(applicationContext)
        val mediaDao = database.mediaDao()
        val eventDao = database.eventDao()

        // Build MusicRepository
        val repository = MusicRepository(mediaDao, eventDao)

        // Instantiate MusicViewModel safely
        val viewModel = ViewModelProvider(
            this,
            MusicViewModelFactory(repository)
        )[MusicViewModel::class.java]

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MusicDashboard(viewModel = viewModel)
                }
            }
        }
    }
}
