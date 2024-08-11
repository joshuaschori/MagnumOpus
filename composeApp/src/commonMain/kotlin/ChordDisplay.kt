import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
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

// TODO cascading choices other than alterations, 7th and extensions?, default to none? default to major?
// TODO or all intervals?
//  chord name
//  interval names
//  highlight note names on fretboard
//  only make diminished 7th available on diminished quality chord
//  triad or quality?
//  one button for chord type?

@Composable
fun ChordDisplay(navigationState: String, innerPadding: PaddingValues) {
    var currentGuitar: Guitar by remember { mutableStateOf(Guitar(isDefaultGuitar = true)) }

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
                        currentGuitar = it
                    }
                )

                ChordDisplayText(
                    currentGuitar = currentGuitar
                )

            }

        }

        else {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
            ) {

                ChordDisplayText(
                    currentGuitar = currentGuitar
                )

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
    }
}

@Composable
fun ChordDisplayText(
    currentGuitar: Guitar
) {

    val scrollState = rememberScrollState()

    var rootMenuExpanded by remember { mutableStateOf(false) }
    var rootChosen by remember { mutableStateOf(Pitch(-1)) }
    val rootMenuLazyListState = rememberLazyListState()
    val rootMenuItemsPitches = listOf(
        Pitch(8),
        Pitch(9),
        Pitch(10),
        Pitch(10),
        Pitch(11),
        Pitch(0),
        Pitch(1),
        Pitch(1),
        Pitch(2),
        Pitch(3),
        Pitch(3),
        Pitch(4),
        Pitch(5),
        Pitch(6),
        Pitch(6),
        Pitch(7),
        Pitch(8)
    )
    rootMenuItemsPitches[0].chosenReading = rootMenuItemsPitches[0].flatReading
    rootMenuItemsPitches[1].chosenReading = rootMenuItemsPitches[1].naturalReading
    rootMenuItemsPitches[2].chosenReading = rootMenuItemsPitches[2].sharpReading
    rootMenuItemsPitches[3].chosenReading = rootMenuItemsPitches[3].flatReading
    rootMenuItemsPitches[4].chosenReading = rootMenuItemsPitches[4].naturalReading
    rootMenuItemsPitches[5].chosenReading = rootMenuItemsPitches[5].naturalReading
    rootMenuItemsPitches[6].chosenReading = rootMenuItemsPitches[6].sharpReading
    rootMenuItemsPitches[7].chosenReading = rootMenuItemsPitches[7].flatReading
    rootMenuItemsPitches[8].chosenReading = rootMenuItemsPitches[8].naturalReading
    rootMenuItemsPitches[9].chosenReading = rootMenuItemsPitches[9].sharpReading
    rootMenuItemsPitches[10].chosenReading = rootMenuItemsPitches[10].flatReading
    rootMenuItemsPitches[11].chosenReading = rootMenuItemsPitches[11].naturalReading
    rootMenuItemsPitches[12].chosenReading = rootMenuItemsPitches[12].naturalReading
    rootMenuItemsPitches[13].chosenReading = rootMenuItemsPitches[13].sharpReading
    rootMenuItemsPitches[14].chosenReading = rootMenuItemsPitches[14].flatReading
    rootMenuItemsPitches[15].chosenReading = rootMenuItemsPitches[15].naturalReading
    rootMenuItemsPitches[16].chosenReading = rootMenuItemsPitches[16].sharpReading

    var typeMenuExpanded by remember { mutableStateOf(false) }
    var typeChosen by remember { mutableStateOf("") }
    val typeMenuItems = listOf(
        "Major",
        "Minor",
        "Diminished",
        "Dominant",
        "Augmented",
        "Suspended 4th",
        "Suspended 2nd"
    )
    val typeMenuLazyListState = rememberLazyListState()

    val (checkedState0, onStateChange0) = remember { mutableStateOf(false) }
    val (checkedState1, onStateChange1) = remember { mutableStateOf(false) }
    val (checkedState2, onStateChange2) = remember { mutableStateOf(false) }
    val (checkedState3, onStateChange3) = remember { mutableStateOf(false) }
    val (checkedState4, onStateChange4) = remember { mutableStateOf(false) }
    val (checkedState5, onStateChange5) = remember { mutableStateOf(false) }
    val (checkedState6, onStateChange6) = remember { mutableStateOf(false) }
    val (checkedState7, onStateChange7) = remember { mutableStateOf(false) }
    val (checkedState8, onStateChange8) = remember { mutableStateOf(false) }
    val (checkedState9, onStateChange9) = remember { mutableStateOf(false) }
    val (checkedState10, onStateChange10) = remember { mutableStateOf(false) }
    val (checkedState11, onStateChange11) = remember { mutableStateOf(false) }

    // TODO repeated code with ChordIdentification
    val currentTuning: MutableList<Pitch> = mutableStateListOf()
    repeat(currentGuitar.numberOfStrings) {stringIndex ->
        currentTuning.add(
            Pitch(currentGuitar.strings[stringIndex].tuning.midiValue)
        )
    }
    val currentTuningInterpretation = ChordInterpretation(currentTuning[0], currentTuning[0], currentTuning)
    val currentTuningNoteNames: MutableList<String> = mutableStateListOf()
    for (pitch in currentTuningInterpretation.chosenPitches) {
        if (pitch.hasNatural) {
            currentTuningNoteNames.add(
                pitch.naturalReading.name
            )
        }
        else {
            currentTuningNoteNames.add(
                pitch.chosenReading.name
            )
        }
    }

    Column(
        modifier = Modifier
            .padding(top = insetVertical.dp)
    ) {

        Text(
            "Tuning:",
            fontSize = 16.sp,
        )

        Text(
            currentTuningNoteNames.joinToString("  "),
            fontSize = 16.sp,
            modifier = Modifier
                .widthIn(150.dp)
                .heightIn(40.dp)
        )

        Text(
            "Chord Root: ",
            fontSize = 16.sp,
        )

        Box {
            FilledTonalButton(
                onClick = {
                    rootMenuExpanded = true
                }
            ) {
                Text(
                    if (rootChosen.midiValue == -1) {
                        "Select"
                    } else {
                        rootChosen.chosenReading.name
                    }
                )
            }
            DropdownMenu(
                expanded = rootMenuExpanded,
                onDismissRequest = { rootMenuExpanded = false },
                scrollState = scrollState,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                LazyColumn(
                    state = rootMenuLazyListState,
                    modifier = Modifier
                        .width(100f.dp)
                        .height(500f.dp)
                ) {
                    for (pitch in rootMenuItemsPitches) {
                        item {
                            DropdownMenuItem(
                                onClick = {
                                    rootChosen = pitch
                                    rootMenuExpanded = false
                                },
                                text = { Text(pitch.chosenReading.name) },
                                modifier = if (rootChosen.chosenReading.name == pitch.chosenReading.name) {
                                    Modifier.background(MaterialTheme.colorScheme.tertiaryContainer)
                                } else {
                                    Modifier
                                }
                            )
                        }
                    }

                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Chord Type: ",
            fontSize = 16.sp,
        )

        Box {
            FilledTonalButton(
                onClick = {
                    typeMenuExpanded = true
                }
            ) {
                // TODO revert to "Select" if Interval checkboxes change the chord type
                Text(
                    if (typeChosen == "") {
                        "Select"
                    } else {
                        typeChosen
                    }
                )
            }
            DropdownMenu(
                expanded = typeMenuExpanded,
                onDismissRequest = { typeMenuExpanded = false },
                scrollState = scrollState,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                LazyColumn(
                    state = typeMenuLazyListState,
                    modifier = Modifier
                        .width(200f.dp)
                        .height(360f.dp)
                ) {
                    for (type in typeMenuItems) {
                        item {
                            DropdownMenuItem(
                                onClick = {
                                    typeChosen = type
                                    when (typeChosen) {
                                        "Major" -> run {
                                            onStateChange0(true)
                                            onStateChange1(false)
                                            onStateChange2(false)
                                            onStateChange3(false)
                                            onStateChange4(true)
                                            onStateChange5(false)
                                            onStateChange6(false)
                                            onStateChange7(true)
                                            onStateChange8(false)
                                            onStateChange9(false)
                                            onStateChange10(false)
                                            onStateChange11(false)
                                        }

                                        "Minor" -> run {
                                            onStateChange0(true)
                                            onStateChange1(false)
                                            onStateChange2(false)
                                            onStateChange3(true)
                                            onStateChange4(false)
                                            onStateChange5(false)
                                            onStateChange6(false)
                                            onStateChange7(true)
                                            onStateChange8(false)
                                            onStateChange9(false)
                                            onStateChange10(false)
                                            onStateChange11(false)
                                        }

                                        "Diminished" -> run {
                                            onStateChange0(true)
                                            onStateChange1(false)
                                            onStateChange2(false)
                                            onStateChange3(true)
                                            onStateChange4(false)
                                            onStateChange5(false)
                                            onStateChange6(true)
                                            onStateChange7(false)
                                            onStateChange8(false)
                                            onStateChange9(false)
                                            onStateChange10(false)
                                            onStateChange11(false)
                                        }

                                        "Dominant" -> run {
                                            onStateChange0(true)
                                            onStateChange1(false)
                                            onStateChange2(false)
                                            onStateChange3(false)
                                            onStateChange4(true)
                                            onStateChange5(false)
                                            onStateChange6(false)
                                            onStateChange7(true)
                                            onStateChange8(false)
                                            onStateChange9(false)
                                            onStateChange10(true)
                                            onStateChange11(false)
                                        }

                                        "Augmented" -> run {
                                            onStateChange0(true)
                                            onStateChange1(false)
                                            onStateChange2(false)
                                            onStateChange3(false)
                                            onStateChange4(true)
                                            onStateChange5(false)
                                            onStateChange6(false)
                                            onStateChange7(false)
                                            onStateChange8(true)
                                            onStateChange9(false)
                                            onStateChange10(false)
                                            onStateChange11(false)
                                        }

                                        "Suspended 4th" -> run {
                                            onStateChange0(true)
                                            onStateChange1(false)
                                            onStateChange2(false)
                                            onStateChange3(false)
                                            onStateChange4(false)
                                            onStateChange5(true)
                                            onStateChange6(false)
                                            onStateChange7(true)
                                            onStateChange8(false)
                                            onStateChange9(false)
                                            onStateChange10(false)
                                            onStateChange11(false)
                                        }

                                        "Suspended 2nd" -> run {
                                            onStateChange0(true)
                                            onStateChange1(false)
                                            onStateChange2(true)
                                            onStateChange3(false)
                                            onStateChange4(false)
                                            onStateChange5(false)
                                            onStateChange6(false)
                                            onStateChange7(true)
                                            onStateChange8(false)
                                            onStateChange9(false)
                                            onStateChange10(false)
                                            onStateChange11(false)
                                        }
                                    }
                                    typeMenuExpanded = false
                                },
                                text = { Text(type) },
                                modifier = if (typeChosen == type) {
                                    Modifier.background(MaterialTheme.colorScheme.tertiaryContainer)
                                } else {
                                    Modifier
                                }
                            )
                        }
                    }

                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Intervals: ",
            fontSize = 16.sp,
        )

        // TODO refactor this after I learn about view models, state, destructuring

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(100.dp)
                .toggleable(
                    value = checkedState0,
                    onValueChange = { onStateChange0(!checkedState0) },
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState0,
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "Root",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(100.dp)
                .toggleable(
                    value = checkedState1,
                    onValueChange = { onStateChange1(!checkedState1) },
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState1,
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "♭2",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(100.dp)
                .toggleable(
                    value = checkedState2,
                    onValueChange = { onStateChange2(!checkedState2) },
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState2,
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "2",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(100.dp)
                .toggleable(
                    value = checkedState3,
                    onValueChange = { onStateChange3(!checkedState3) },
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState3,
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "♭3",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(100.dp)
                .toggleable(
                    value = checkedState4,
                    onValueChange = { onStateChange4(!checkedState4) },
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState4,
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "3",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(100.dp)
                .toggleable(
                    value = checkedState5,
                    onValueChange = { onStateChange5(!checkedState5) },
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState5,
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "4",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(100.dp)
                .toggleable(
                    value = checkedState6,
                    onValueChange = { onStateChange6(!checkedState6) },
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState6,
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "♯4/♭5",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(100.dp)
                .toggleable(
                    value = checkedState7,
                    onValueChange = { onStateChange7(!checkedState7) },
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState7,
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "5",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(100.dp)
                .toggleable(
                    value = checkedState8,
                    onValueChange = { onStateChange8(!checkedState8) },
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState8,
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "♯5/♭6",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(100.dp)
                .toggleable(
                    value = checkedState9,
                    onValueChange = { onStateChange9(!checkedState9) },
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState9,
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "6/\uD834\uDD2B7",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(100.dp)
                .toggleable(
                    value = checkedState10,
                    onValueChange = { onStateChange10(!checkedState10) },
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState10,
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "♭7",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(100.dp)
                .toggleable(
                    value = checkedState11,
                    onValueChange = { onStateChange11(!checkedState11) },
                    role = Role.Checkbox
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState11,
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "7",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            "Name: ",
            fontSize = 16.sp,
        )

        val currentPitches: MutableList<Pitch> = mutableStateListOf()

        for (pitch in currentTuningInterpretation.chosenPitches) {

            if (pitch.hasNatural) {
                currentTuningNoteNames.add(
                    pitch.naturalReading.name
                )
            }
            else {
                currentTuningNoteNames.add(
                    pitch.chosenReading.name
                )
            }

        }

        val currentNoteNames: MutableList<String> = mutableStateListOf()
        var currentChordName = ""
        var currentChordExtensionsPrefix = ""
        var currentChordExtensions = ""

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

        if (currentPitches.size < 2) {
            androidx.compose.material.Text(
                "Select Chord Root",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.heightIn(20.dp)
            )
            androidx.compose.material.Text(
                "Select Chord Type",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.heightIn(20.dp)
            )
        }
        else {
            androidx.compose.material.Text(
                buildAnnotatedString {
                    append(currentChordName)
                    withStyle(superscript) {
                        append(currentChordExtensionsPrefix)
                        append(currentChordExtensions)
                    }
                },
                fontSize = 20.sp,
                modifier = Modifier.heightIn(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        FilledTonalButton(
            onClick = {
                rootChosen = Pitch(-1)
                typeChosen = ""
                onStateChange0(false)
                onStateChange1(false)
                onStateChange2(false)
                onStateChange3(false)
                onStateChange4(false)
                onStateChange5(false)
                onStateChange6(false)
                onStateChange7(false)
                onStateChange8(false)
                onStateChange9(false)
                onStateChange10(false)
                onStateChange11(false)
            }
        ) {
            Text("Clear Chord")
        }
    }
}