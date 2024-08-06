import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import classes.Chord
import classes.ChordInterpretation
import classes.Guitar
import classes.Pitch

@Composable
fun ChordIdentification(navigationState: String, innerPadding: PaddingValues) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(paddingValues = innerPadding)
    ) {

        // TODO multiple guitars / guitar options in settings
        var currentGuitar: Guitar by remember { mutableStateOf(Guitar(isDefaultGuitar = true)) }

        GuitarCanvas(
            navigationState = navigationState,
            innerPadding = innerPadding,
            currentGuitar = currentGuitar,
            onGuitarChange = {
                currentGuitar = it
            }
        )

        Column() {

            val currentTuning: MutableList<Pitch> = mutableStateListOf()
            val currentPitches: MutableList<Pitch> = mutableStateListOf()

            repeat(currentGuitar.numberOfStrings) {stringIndex ->

                currentTuning.add(
                    Pitch(currentGuitar.strings[stringIndex].tuning.midiValue)
                )

                if (currentGuitar.fretMemory[stringIndex].visible) {
                    currentPitches.add(
                        Pitch(currentGuitar.strings[stringIndex].tuning.midiValue + currentGuitar.fretMemory[stringIndex].fretSelected)
                    )
                }

            }

            val currentTuningInterpretation = ChordInterpretation(currentTuning[0], currentTuning[0], currentTuning)
            val currentTuningNoteNames: MutableList<String> = mutableStateListOf()

            for (pitch in currentTuningInterpretation.chosenPitches) {
                currentTuningNoteNames.add(
                    pitch.chosenReading.name
                )
            }

            val currentNoteNames: MutableList<String> = mutableStateListOf()
            var currentChordName: String = ""
            var currentChordExtensionsPrefix: String = ""
            var currentChordExtensions: String = ""

            if (currentPitches.size > 0) {
                val currentChord = Chord(currentPitches)
                currentChordName = currentChord.chosenChordName
                currentChordExtensionsPrefix = currentChord.chosenChordExtensionsPrefix
                currentChordExtensions = currentChord.chosenChordExtensions

                for (pitch in currentChord.chordInterpretationsList[currentChord.chosenInterpretationIndex].chosenPitches) {
                    currentNoteNames.add(
                        pitch.chosenReading.name
                    )
                }
            }

            if (currentPitches.size < 2) {
                currentChordName = ""
            }

            val superscript = SpanStyle(
                baselineShift = BaselineShift.Superscript,
                fontSize = 12.sp,
                color = Color.Black
            )

            Text(
                "Tuning:",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = insetVertical.dp)
            )

            Text(
                currentTuningNoteNames.joinToString("  "),
                fontSize = 20.sp,
                modifier = Modifier
            )

            Text(
                "Notes:",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = insetVertical.dp)
            )

            Text(
                currentNoteNames.joinToString("  "),
                fontSize = 20.sp,
                modifier = Modifier
            )

            Text(
                "Chord:",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = insetVertical.dp)
            )

            Text(
                buildAnnotatedString {
                    append(currentChordName)
                    withStyle(superscript) {
                        append(currentChordExtensionsPrefix)
                        append(currentChordExtensions)
                    }
                },
                fontSize = 20.sp,
                modifier = Modifier
            )
        }
    }
}