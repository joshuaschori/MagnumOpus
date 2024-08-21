import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Home(navigationState: String, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(paddingValues = innerPadding)
    ) {
        Text(
            text = "Magnum Opus is a tool for guitarists that is able to identify chord shapes, " +
                    "display scales, and display voicing options for chords using a fretboard " +
                    "interface. The fretboard can be tuned to any tuning, can have a " +
                    "variable number of strings and frets, and provides contextual " +
                    "information about the function of the pitches.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(top = insetVertical.dp)
                .widthIn(200.dp, 350.dp)
        )
        Text(
            text = "Chord Identification",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(top = insetVertical.dp)
                .widthIn(200.dp, 350.dp)
        )
        Text(
            text = "The Chord Identification page allows you to tap on the frets of each of the " +
                    "strings and then displays the most likely reading of the chord implied by " +
                    "the fretted notes. Tap the \"Alternative Readings\" button to view and " +
                    "select other possible readings of the chord based on each of the " +
                    "other notes as the root.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .widthIn(200.dp, 350.dp)
        )
        Text(
            text = "Interval Display",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(top = insetVertical.dp)
                .widthIn(200.dp, 350.dp)
        )
        Text(
            text = "The Interval Display page takes a root note and displays intervals from that " +
                    "note in every octave across the fretboard. Select the intervals for a scale " +
                    "to view it in the selected tuning, or select the intervals that make up a " +
                    "chord to view all of the possible notes within that chord and inform your " +
                    "decision of how to voice that chord.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .widthIn(200.dp, 350.dp)
        )
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(top = insetVertical.dp)
                .widthIn(200.dp, 350.dp)
        )
        Text(
            text = "In the Settings page, set up your guitar to have between 4 and 8 strings " +
                    "of any tuning and between 12 and 24 frets. Select which page you would like " +
                    "to view by default upon opening the app.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .widthIn(200.dp, 350.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}