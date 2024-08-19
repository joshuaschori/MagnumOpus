import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import classes.Chord
import classes.Guitar
import classes.Pitch

@Composable
fun ChordIdentification(
    navigationState: String,
    innerPadding: PaddingValues,
    currentGuitar: Guitar,
    onGuitarChange: (Guitar) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(paddingValues = innerPadding)
    ) {
        if (settings.getInt("number of strings", 6) <= 6) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                GuitarCanvas(
                    navigationState = navigationState,
                    innerPadding = innerPadding,
                    currentGuitar = currentGuitar,
                    onGuitarChange = {
                        val newGuitar: Guitar = currentGuitar.copy(fretMemory = currentGuitar.fretMemory.toMutableList())
                        onGuitarChange.invoke(newGuitar)
                    }
                )

                ChordIdentificationText(
                    currentGuitar = currentGuitar
                )
            }
        }
        else {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
            ) {

                Column {
                    ChordIdentificationText(
                        currentGuitar = currentGuitar
                    )
                }

                GuitarCanvas(
                    navigationState = navigationState,
                    innerPadding = innerPadding,
                    currentGuitar = currentGuitar,
                    onGuitarChange = {
                        val newGuitar: Guitar = currentGuitar.copy(fretMemory = currentGuitar.fretMemory.toMutableList())
                        onGuitarChange.invoke(newGuitar)
                    }
                )

            }
        }
    }
}

@Composable
fun ChordIdentificationText(
    currentGuitar: Guitar
) {
    val currentPitches: MutableList<Pitch> = mutableStateListOf()
    repeat(currentGuitar.numberOfStrings) {stringIndex ->
        if (currentGuitar.fretMemory[stringIndex].visible) {
            currentPitches.add(
                Pitch(currentGuitar.strings[stringIndex].tuning.midiValue + currentGuitar.fretMemory[stringIndex].fretSelected)
            )
        }
    }

    val currentNoteNames: MutableList<String> = mutableStateListOf()
    var currentChord = Chord(mutableListOf(Pitch(0)))
    var selectedInterpretationIndex = 0

    if (currentPitches.size > 0) {
        currentChord = Chord(currentPitches)

        for (pitch in currentChord.sortedInterpretationList[selectedInterpretationIndex].chosenPitches) {
            currentNoteNames.add(
                pitch.chosenReading.name
            )
        }
    }

    val superscript = SpanStyle(
        baselineShift = BaselineShift.Superscript,
        fontSize = 12.sp,
        color = Color.Black
    )

    val superscriptSmall = SpanStyle(
        baselineShift = BaselineShift.Superscript,
        fontSize = 10.sp,
        color = Color.Black
    )

    Column(
        modifier = Modifier
            .padding(top = insetVertical.dp)
    ) {
        Text(
            "Notes:",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = insetVertical.dp)
        )

        if (currentPitches.size == 0) {
            Text(
                "Select on Fretboard",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .widthIn(150.dp)
                    .heightIn(40.dp)
            )
        }
        else {
            Text(
                currentNoteNames.joinToString("  "),
                fontSize = 16.sp,
                modifier = Modifier
                    .widthIn(150.dp)
                    .heightIn(40.dp)
            )
        }

        Text(
            "Chord:",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = insetVertical.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (currentPitches.size < 2) {
            Text(
                "Select Notes",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.heightIn(220.dp)
            )
        }
        else {
            Text(
                buildAnnotatedString {
                    append(currentChord.sortedInterpretationList[selectedInterpretationIndex].chordName)
                    withStyle(superscript) {
                        append(currentChord.sortedInterpretationList[selectedInterpretationIndex].extensionsPrefix)
                        append(currentChord.sortedInterpretationList[selectedInterpretationIndex].extensions.joinToString(","))
                    }
                },
                fontSize = 18.sp,
                modifier = Modifier
                    .heightIn(60.dp)
            )
            Text(
                "Other Possible\r\nInterpretations:",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = insetHorizontal.dp)
                    .heightIn(40.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            val listOfAlreadyDisplayedInterpretationRoots: MutableList<Int> = mutableListOf(
                currentChord.sortedInterpretationList[selectedInterpretationIndex].root.midiValue % 12
            )
            for ((index, interpretation) in currentChord.sortedInterpretationList.withIndex()) {
                if (index != selectedInterpretationIndex && (interpretation.root.midiValue % 12) !in listOfAlreadyDisplayedInterpretationRoots) {
                    listOfAlreadyDisplayedInterpretationRoots.add(interpretation.root.midiValue % 12)

                    Text(
                        buildAnnotatedString {
                            append(interpretation.chordName)
                            withStyle(superscriptSmall) {
                                append(interpretation.extensionsPrefix)
                                append(interpretation.extensions.joinToString(","))
                            }
                        },
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(start = insetHorizontal.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        FilledTonalButton(
            onClick = {
                for ((stringIndex) in currentGuitar.fretMemory.withIndex()) {
                    currentGuitar.fretMemory[stringIndex].x = currentGuitar.stringSpacing * stringIndex
                    currentGuitar.fretMemory[stringIndex].y = 0f - currentGuitar.fretSpacing * 0.5f
                    currentGuitar.fretMemory[stringIndex].fretSelected = 0
                    currentGuitar.fretMemory[stringIndex].visible = false
                }
                currentPitches.clear()
            }
        ) {
            Text("Clear Frets")
        }
    }
}