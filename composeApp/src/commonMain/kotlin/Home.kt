import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Home(navigationState: String, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(paddingValues = innerPadding)
    ) {
        repeat(6) {
            Row() {
                ElevatedButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        Icons.Outlined.Add,
                        contentDescription = "Add String",
                    )
                }
                ElevatedButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        Icons.Outlined.Remove,
                        contentDescription = "Remove String"
                    )
                }
            }
        }
    }
}