import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
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
    val scrollState = rememberScrollState()
    val interpretationsLazyListState = rememberLazyListState()
    var interpretationsMenuExpanded by remember { mutableStateOf(false) }

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
    var selectedInterpretationIndex by remember { mutableStateOf(currentGuitar.chordInterpretationMemory.selectedInterpretationIndex) }

    if (currentPitches.size > 0) {
        currentChord = Chord(currentPitches)

        for (pitch in currentChord.sortedInterpretationList[selectedInterpretationIndex].chosenPitches) {
            currentNoteNames.add(
                pitch.chosenReading.name
            )
        }

        currentGuitar.chordInterpretationMemory.root = currentChord.sortedInterpretationList[selectedInterpretationIndex].root
        currentGuitar.chordInterpretationMemory.pitchClassIntValues.clear()
        currentGuitar.chordInterpretationMemory.noteNames.clear()

        for (pitch in currentChord.sortedInterpretationList[selectedInterpretationIndex].chosenPitches) {
            currentGuitar.chordInterpretationMemory.pitchClassIntValues.add(pitch.midiValue % 12)
            currentGuitar.chordInterpretationMemory.noteNames.add(pitch.chosenReading.name)
        }
    }

    // create list of selected pitches without repeats
    val selectedChordPitchClasses: MutableList<Pitch> = mutableListOf()
    val alreadyAddedPitchClasses: MutableList<Int> = mutableListOf()
    for (pitch in currentChord.sortedInterpretationList[selectedInterpretationIndex].chosenPitches) {
        if (pitch.midiValue % 12 !in alreadyAddedPitchClasses) {
            selectedChordPitchClasses.add(pitch)
            alreadyAddedPitchClasses.add(pitch.midiValue % 12)
        }
    }

    // sort the selected chord's pitches in descending order, based on the chord's root as 0
    val selectedPitchClassesSortedByInterval =
        selectedChordPitchClasses.sortedByDescending {
            if ((it.midiValue % 12) - (currentChord.sortedInterpretationList[selectedInterpretationIndex].root.midiValue % 12) >= 0)
            {
                (it.midiValue - (currentChord.sortedInterpretationList[selectedInterpretationIndex].root.midiValue % 12)) % 12
            }
            else {
                (it.midiValue - (currentChord.sortedInterpretationList[selectedInterpretationIndex].root.midiValue % 12) + 12) % 12
            }
        }

    val superscript = SpanStyle(
        baselineShift = BaselineShift.Superscript,
        fontSize = 12.sp,
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
            fontSize = 20.sp
        )

        if (currentPitches.size < 2) {
            Text(
                "Select Notes",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.heightIn(420.dp)
            )
        }
        else {
            Text(
                buildAnnotatedString {
                    // add space for empty extension, for consistent spacing
                    if (currentChord.sortedInterpretationList[selectedInterpretationIndex].extensions.isEmpty()) {
                        append(currentChord.sortedInterpretationList[selectedInterpretationIndex].chordName)
                        withStyle(superscript) {
                            append(" ")
                        }
                    }
                    else {
                        append(currentChord.sortedInterpretationList[selectedInterpretationIndex].chordName)
                        withStyle(superscript) {
                            append(currentChord.sortedInterpretationList[selectedInterpretationIndex].extensionsPrefix)
                            append(currentChord.sortedInterpretationList[selectedInterpretationIndex].extensions.joinToString(","))
                        }
                    }
                },
                fontSize = 18.sp,
                modifier = Modifier
                    .heightIn(80.dp)
            )

            Text(
                "Structure:",
                fontSize = 20.sp,
                modifier = Modifier.heightIn(40.dp)
            )

            Column(
                modifier = Modifier.heightIn(200.dp)
            ) {
                for (pitch in selectedPitchClassesSortedByInterval) {
                    Text(
                        text = when (
                            if ((pitch.midiValue % 12) - (currentChord.sortedInterpretationList[selectedInterpretationIndex].root.midiValue % 12) >= 0)
                            {
                                (pitch.midiValue - (currentChord.sortedInterpretationList[selectedInterpretationIndex].root.midiValue % 12)) % 12
                            }
                            else {
                                (pitch.midiValue - (currentChord.sortedInterpretationList[selectedInterpretationIndex].root.midiValue % 12) + 12) % 12
                            }
                        ) {
                            11 -> "7"
                            10 -> when (currentChord.sortedInterpretationList[selectedInterpretationIndex].intervals[10].letterInterval) {
                                6 -> {
                                    "♭7"
                                }
                                5 -> {
                                    "♯6"
                                }
                                else -> {
                                    "error"
                                }
                            }
                            9 -> when (currentChord.sortedInterpretationList[selectedInterpretationIndex].intervals[9].letterInterval) {
                                6 -> {
                                    "\uD834\uDD2B7"
                                }
                                5 -> {
                                    "6"
                                }
                                else -> {
                                    "error"
                                }
                            }
                            8 -> when (currentChord.sortedInterpretationList[selectedInterpretationIndex].intervals[8].letterInterval) {
                                5 -> {
                                    "♭6"
                                }
                                4 -> {
                                    "♯5"
                                }
                                else -> {
                                    "error"
                                }
                            }
                            7 -> "5"
                            6 -> when (currentChord.sortedInterpretationList[selectedInterpretationIndex].intervals[6].letterInterval) {
                                4 -> {
                                    "♭5"
                                }
                                3 -> {
                                    "♯4"
                                }
                                else -> {
                                    "error"
                                }
                            }
                            5 -> "4"
                            4 -> "3"
                            3 -> when (currentChord.sortedInterpretationList[selectedInterpretationIndex].intervals[3].letterInterval) {
                                2 -> {
                                    "♭3"
                                }
                                1 -> {
                                    "♯2"
                                }
                                else -> {
                                    "error"
                                }
                            }
                            2 -> "2"
                            1 -> "♭2"
                            0 -> "Root"
                            else -> "error"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Box {
                FilledTonalButton(
                    onClick = {
                        interpretationsMenuExpanded = true
                    },
                     modifier = Modifier
                         .heightIn(60.dp)
                ) {
                    Text(
                        "Alternate\r\nReadings"
                    )
                }
                DropdownMenu(
                    expanded = interpretationsMenuExpanded,
                    onDismissRequest = { interpretationsMenuExpanded = false },
                    scrollState = scrollState,
                    modifier = Modifier
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                ) {
                    LazyColumn(
                        state = interpretationsLazyListState,
                        modifier = Modifier
                            .width(200f.dp)
                            .height(360f.dp)
                    ) {
                        val listOfAlreadyDisplayedInterpretationRoots: MutableList<Int> = mutableListOf(
                            currentChord.sortedInterpretationList[selectedInterpretationIndex].root.midiValue % 12
                        )
                        for ((index, interpretation) in currentChord.sortedInterpretationList.withIndex()) {
                            if (index != selectedInterpretationIndex && (interpretation.root.midiValue % 12) !in listOfAlreadyDisplayedInterpretationRoots) {
                                listOfAlreadyDisplayedInterpretationRoots.add(interpretation.root.midiValue % 12)
                                val superscriptSmall = SpanStyle(
                                    baselineShift = BaselineShift.Superscript,
                                    fontSize = (16 - 2 * index).sp,
                                    color = Color.Black
                                )

                                item {
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedInterpretationIndex = index
                                            currentGuitar.chordInterpretationMemory.selectedInterpretationIndex = index
                                            interpretationsMenuExpanded = false
                                            currentGuitar.chordInterpretationMemory.root = currentChord.sortedInterpretationList[index].root
                                        },
                                        text = {
                                            Text(
                                                buildAnnotatedString {
                                                    // add space for empty extension, for consistent spacing
                                                    if (interpretation.extensions.isEmpty()) {
                                                        append(interpretation.chordName)
                                                        withStyle(superscriptSmall) {
                                                            append(" ")
                                                        }
                                                    }
                                                    else {
                                                        append(interpretation.chordName)
                                                        withStyle(superscriptSmall) {
                                                            append(interpretation.extensionsPrefix)
                                                            append(interpretation.extensions.joinToString(","))
                                                        }
                                                    }
                                                },
                                                fontSize = (20 - 2 * index).sp
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
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
                selectedInterpretationIndex = 0
                currentGuitar.chordInterpretationMemory.root = Pitch(-1)
                currentGuitar.chordInterpretationMemory.pitchClassIntValues.clear()
                currentGuitar.chordInterpretationMemory.noteNames.clear()
            }
        ) {
            Text("Clear Frets")
        }
    }
}