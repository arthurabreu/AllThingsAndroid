package com.arthurabreu.allthingsandroid.ui.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.arthurabreu.allthingsandroid.domain.model.DomainData
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val domainData = viewModel.observedData.collectAsState(
        initial = DomainData(0, 0, "Default", true)
    )

    Column {
        Button(
            onClick = { viewModel.onProfileClick() }
        ) {
            Text("Go to Profile")
        }
        Button(
            onClick = { viewModel.onSettingsClick() }
        ) {
            Text("Go to Settings")
        }
        Text(text = "\nDomain data from api -> db: \n\n${domainData.value}")
    }
}