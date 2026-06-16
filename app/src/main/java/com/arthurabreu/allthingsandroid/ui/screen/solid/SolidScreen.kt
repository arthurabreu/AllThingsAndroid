import androidx.compose.runtime.Composable
import com.arthurabreu.allthingsandroid.core.designsystem.component.AppScaffold
import com.arthurabreu.allthingsandroid.core.navigation.AppNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SolidScreen(
    viewModel: SolidViewModel = koinViewModel()
) {
    val appNavigator: AppNavigator = koinInject()

    AppScaffold(title = "SOLID", onBack = { appNavigator.tryNavigateBack() }) {
        SolidComponent(
            principles = viewModel.principles
        )
    }
}