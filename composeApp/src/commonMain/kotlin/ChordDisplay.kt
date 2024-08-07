import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import classes.Guitar

@Composable
fun ChordDisplay(navigationState: String, innerPadding: PaddingValues) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(paddingValues = innerPadding)
    ) {

        var currentGuitar: Guitar by remember { mutableStateOf(Guitar(isDefaultGuitar = true)) }

        GuitarCanvas(
            navigationState = navigationState,
            innerPadding = innerPadding,
            currentGuitar = currentGuitar,
            onGuitarChange = {
                currentGuitar = it
            }
        )

    }
}