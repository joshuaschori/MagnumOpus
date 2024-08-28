import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import classes.ChordInterpretation
import classes.Guitar
import classes.Pitch

@Composable
fun IntervalDisplay(
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
        if (settings.getInt("number of strings", 6) <= 8) {
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

                IntervalDisplayText(
                    currentGuitar = currentGuitar
                )
            }
        }
        else {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
            ) {

                IntervalDisplayText(
                    currentGuitar = currentGuitar
                )

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
fun IntervalDisplayText(
    currentGuitar: Guitar
) {
    val scrollState = rememberScrollState()
    var rootMenuExpanded by remember { mutableStateOf(false) }
    var rootChosen by remember { mutableStateOf(currentGuitar.intervalDisplayMemory.root) }

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

    val (checkedState0, onStateChange0) = remember { mutableStateOf(currentGuitar.intervalDisplayMemory.checkboxMemory[0]) }
    val (checkedState1, onStateChange1) = remember { mutableStateOf(currentGuitar.intervalDisplayMemory.checkboxMemory[1]) }
    val (checkedState2, onStateChange2) = remember { mutableStateOf(currentGuitar.intervalDisplayMemory.checkboxMemory[2]) }
    val (checkedState3, onStateChange3) = remember { mutableStateOf(currentGuitar.intervalDisplayMemory.checkboxMemory[3]) }
    val (checkedState4, onStateChange4) = remember { mutableStateOf(currentGuitar.intervalDisplayMemory.checkboxMemory[4]) }
    val (checkedState5, onStateChange5) = remember { mutableStateOf(currentGuitar.intervalDisplayMemory.checkboxMemory[5]) }
    val (checkedState6, onStateChange6) = remember { mutableStateOf(currentGuitar.intervalDisplayMemory.checkboxMemory[6]) }
    val (checkedState7, onStateChange7) = remember { mutableStateOf(currentGuitar.intervalDisplayMemory.checkboxMemory[7]) }
    val (checkedState8, onStateChange8) = remember { mutableStateOf(currentGuitar.intervalDisplayMemory.checkboxMemory[8]) }
    val (checkedState9, onStateChange9) = remember { mutableStateOf(currentGuitar.intervalDisplayMemory.checkboxMemory[9]) }
    val (checkedState10, onStateChange10) = remember { mutableStateOf(currentGuitar.intervalDisplayMemory.checkboxMemory[10]) }
    val (checkedState11, onStateChange11) = remember { mutableStateOf(currentGuitar.intervalDisplayMemory.checkboxMemory[11]) }

    val listOfCheckedStates = listOf(
        checkedState0, checkedState1, checkedState2, checkedState3, checkedState4,
        checkedState5, checkedState6, checkedState7, checkedState8, checkedState9,
        checkedState10, checkedState11
    )

    val listOfOnStateChanges = listOf(
        onStateChange0, onStateChange1, onStateChange2, onStateChange3, onStateChange4,
        onStateChange5, onStateChange6, onStateChange7, onStateChange8, onStateChange9,
        onStateChange10, onStateChange11
    )

    val listOfIntervalColors: List<Long> = (0..11).map {
        when (it) {
            0 -> settings.getLong("intervalColor0", 0xffee1b24)
            1 -> settings.getLong("intervalColor1", 0xfff15b22)
            2 -> settings.getLong("intervalColor2", 0xfff68d1e)
            3 -> settings.getLong("intervalColor3", 0xfffdb913)
            4 -> settings.getLong("intervalColor4", 0xfffef100)
            5 -> settings.getLong("intervalColor5", 0xffc9db2a)
            6 -> settings.getLong("intervalColor6", 0xff3ab449)
            7 -> settings.getLong("intervalColor7", 0xff00a89d)
            8 -> settings.getLong("intervalColor8", 0xff0271bd)
            9 -> settings.getLong("intervalColor9", 0xff524ea1)
            10 -> settings.getLong("intervalColor10", 0xff672e91)
            11 -> settings.getLong("intervalColor11", 0xffb72367)
            else -> 0xff000000
        }
    }

    val listOfIntervalNames: List<String> = (0..11).map {
        when (it) {
            0 -> "Root"
            1 -> "♭2"
            2 -> "2"
            3 -> "♭3"
            4 -> "3"
            5 -> "4"
            6 -> "♯4/♭5"
            7 -> "5"
            8 -> "♯5/♭6"
            9 -> "6/\uD834\uDD2B7"
            10 -> "♯6/♭7"
            11 -> "7"
            else -> "error"
        }
    }

    Column(
        modifier = Modifier
            .padding(top = insetVertical.dp)
    ) {
        Text(
            "Root: ",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = insetVertical.dp)
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
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
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
                                    // setting rootChosen to default value of -1 and then to the
                                    // proper pitch here is a work around. without this, it will not
                                    // recompose from enharmonic equivalents
                                    rootChosen = Pitch(-1)
                                    rootChosen = pitch
                                    rootMenuExpanded = false
                                    onStateChange0(true)
                                    currentGuitar.intervalDisplayMemory.checkboxMemory[0] = true
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
            "Intervals: ",
            fontSize = 20.sp,
        )

        Box {
            FilledTonalButton(
                onClick = {
                    typeMenuExpanded = true
                }
            ) {
                Text(
                    "Add from Chord"
                )
            }
            DropdownMenu(
                expanded = typeMenuExpanded,
                onDismissRequest = { typeMenuExpanded = false },
                scrollState = scrollState,
                modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
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
                                    when (type) {
                                        "Major" -> run {
                                            for ((index, state) in listOfOnStateChanges.withIndex()) {
                                                when (index) {
                                                    0,4,7 -> run { state(true); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = true }
                                                    else -> run { state(false); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = false }
                                                }
                                            }
                                        }

                                        "Minor" -> run {
                                            for ((index, state) in listOfOnStateChanges.withIndex()) {
                                                when (index) {
                                                    0,3,7 -> run { state(true); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = true }
                                                    else -> run { state(false); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = false }
                                                }
                                            }
                                        }

                                        "Diminished" -> run {
                                            for ((index, state) in listOfOnStateChanges.withIndex()) {
                                                when (index) {
                                                    0,3,6 -> run { state(true); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = true }
                                                    else -> run { state(false); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = false }
                                                }
                                            }
                                        }

                                        "Dominant" -> run {
                                            for ((index, state) in listOfOnStateChanges.withIndex()) {
                                                when (index) {
                                                    0,4,7,10 -> run { state(true); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = true }
                                                    else -> run { state(false); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = false }
                                                }
                                            }
                                        }

                                        "Augmented" -> run {
                                            for ((index, state) in listOfOnStateChanges.withIndex()) {
                                                when (index) {
                                                    0,4,8 -> run { state(true); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = true }
                                                    else -> run { state(false); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = false }
                                                }
                                            }
                                        }

                                        "Suspended 4th" -> run {
                                            for ((index, state) in listOfOnStateChanges.withIndex()) {
                                                when (index) {
                                                    0,5,7 -> run { state(true); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = true }
                                                    else -> run { state(false); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = false }
                                                }
                                            }
                                        }

                                        "Suspended 2nd" -> run {
                                            for ((index, state) in listOfOnStateChanges.withIndex()) {
                                                when (index) {
                                                    0,2,7 -> run { state(true); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = true }
                                                    else -> run { state(false); currentGuitar.intervalDisplayMemory.checkboxMemory[index] = false }
                                                }
                                            }
                                        }
                                    }
                                    typeMenuExpanded = false
                                },
                                text = { Text(type) }
                            )
                        }
                    }
                }
            }
        }

        // create interval checkboxes
        for (index in 0..11) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .width(100.dp)
                    .toggleable(
                        value = listOfCheckedStates[index],
                        onValueChange = {
                            listOfOnStateChanges[index](!listOfCheckedStates[index])
                            currentGuitar.intervalDisplayMemory.checkboxMemory[index] = !listOfCheckedStates[index]
                        },
                        role = Role.Checkbox
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = listOfCheckedStates[index],
                    colors = CheckboxColors(
                        CheckboxDefaults.colors().checkedCheckmarkColor,
                        CheckboxDefaults.colors().uncheckedCheckmarkColor,
                        Color(listOfIntervalColors[index]),
                        CheckboxDefaults.colors().uncheckedBoxColor,
                        CheckboxDefaults.colors().disabledCheckedBoxColor,
                        CheckboxDefaults.colors().disabledUncheckedBoxColor,
                        CheckboxDefaults.colors().disabledIndeterminateBoxColor,
                        Color(listOfIntervalColors[index]),
                        CheckboxDefaults.colors().uncheckedBorderColor,
                        CheckboxDefaults.colors().disabledBorderColor,
                        CheckboxDefaults.colors().disabledUncheckedBorderColor,
                        CheckboxDefaults.colors().disabledIndeterminateBorderColor
                    ),
                    modifier = Modifier.scale(0.9f),
                    onCheckedChange = null // null recommended for accessibility with screenreaders
                )
                Text(
                    text = listOfIntervalNames[index],
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }

        val currentPitches: MutableList<Pitch> = mutableListOf()

        if (rootChosen.midiValue != -1) {
            currentGuitar.intervalDisplayMemory.root = rootChosen
            for ((index, state) in listOfCheckedStates.withIndex()) {
                if (state) {
                    currentPitches.add(Pitch(rootChosen.midiValue + index))
                }
            }
        }

        if (currentPitches.size > 0) {
            val currentChordInterpretation = ChordInterpretation(
                rootChosen,
                rootChosen,
                currentPitches,
                rootAlreadyHasReading = rootChosen.chosenReading.accidental.type
            )
            currentGuitar.intervalDisplayMemory.pitchClassIntValues.clear()
            currentGuitar.intervalDisplayMemory.noteNames.clear()

            for (pitch in currentChordInterpretation.chosenPitches) {
                currentGuitar.intervalDisplayMemory.pitchClassIntValues.add(pitch.midiValue % 12)
                currentGuitar.intervalDisplayMemory.noteNames.add(pitch.chosenReading.name)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        FilledTonalButton(
            onClick = {
                rootChosen = Pitch(-1)
                currentPitches.clear()
                currentGuitar.intervalDisplayMemory.root = Pitch(-1)
                currentGuitar.intervalDisplayMemory.noteNames.clear()
                currentGuitar.intervalDisplayMemory.pitchClassIntValues.clear()
                for (change in listOfOnStateChanges) {
                    change(false)
                }
            }
        ) {
            Text("Clear Intervals")
        }
    }
}