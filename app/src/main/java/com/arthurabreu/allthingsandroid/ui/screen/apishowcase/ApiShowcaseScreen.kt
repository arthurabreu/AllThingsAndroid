package com.arthurabreu.allthingsandroid.ui.screen.apishowcase

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.arthurabreu.allthingsandroid.commonscreens.R
import com.arthurabreu.allthingsandroid.domain.model.DomainData
import com.arthurabreu.allthingsandroid.ui.viewmodel.apishowcase.ApiShowcaseViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ApiShowcaseScreen(
    viewModel: ApiShowcaseViewModel = koinViewModel()
) {
    val domainData = viewModel.observedData.collectAsState(
        initial = DomainData(0, 0, "Default", true)
    )
    Text(text = stringResource(R.string.domain_data_from_api_db, domainData))
}