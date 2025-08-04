import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arthurabreu.allthingsandroid.data.solid.SolidPrincipleState

@Composable
fun SolidComponent(
    principles: List<SolidPrincipleState>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(principles.size) { index ->
            val principle = principles[index]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(principle.name, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(principle.description, style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Exemplo Correto:", style = MaterialTheme.typography.labelMedium)
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxWidth().padding(4.dp)
                    ) {
                        BasicText(principle.rightExample)
                    }
                    Spacer(Modifier.height(4.dp))
                    Text("Exemplo Errado:", style = MaterialTheme.typography.labelMedium)
                    Surface(
                        color = MaterialTheme.colorScheme.errorContainer,
                        modifier = Modifier.fillMaxWidth().padding(4.dp)
                    ) {
                        BasicText(principle.wrongExample)
                    }
                }
            }
        }
    }
}