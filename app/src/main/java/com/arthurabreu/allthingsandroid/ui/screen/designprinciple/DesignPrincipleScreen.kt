package com.arthurabreu.allthingsandroid.ui.screen.designprinciple

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppScaffold
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import com.arthurabreu.allthingsandroid.ui.viewmodel.designprinciple.DesignPrincipleViewModel
import com.arthurabreu.commonscreens.ui.composables.shared.PrincipleListComponent
import com.arthurabreu.commonscreens.ui.state.principle.PrincipleUiModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun DesignPrincipleScreen(
    viewModel: DesignPrincipleViewModel = koinViewModel()
) {
    val appNavigator: AppNavigator = koinInject()

    val principleModels = viewModel.principles.map {
        PrincipleUiModel(
            name = it.name,
            description = it.description,
            rightExample = it.rightExample,
            wrongExample = it.wrongExample,
        )
    }

    AppScaffold(title = "Design Principles", onBack = { appNavigator.tryNavigateBack() }) { padding ->
        PrincipleListComponent(principles = principleModels, modifier = Modifier.fillMaxSize())
    }
}