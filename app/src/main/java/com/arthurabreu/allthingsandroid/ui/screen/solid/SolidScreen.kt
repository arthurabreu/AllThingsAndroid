import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun SolidScreen(
    viewModel: SolidViewModel = koinViewModel()
) {
    SolidComponent(
        principles = viewModel.principles
    )
}