import androidx.compose.foundation.background
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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateListOf
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
import classes.Chord
import classes.ChordInterpretation
import classes.Guitar
import classes.Pitch

@Composable
fun IntervalDisplay(navigationState: String, innerPadding: PaddingValues) {
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
                        currentGuitar = it
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
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = insetVertical.dp)
        )

        Text(
            currentTuningNoteNames.joinToString("  "),
            fontSize = 16.sp,
            modifier = Modifier
                .widthIn(150.dp)
                .heightIn(40.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Root: ",
            fontSize = 20.sp,
        )

        Box {
            Button(
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
                                    // TODO setting rootChosen to default value of -1 and then to the
                                    //  proper pitch here is a work around. without this, it will not
                                    //  recompose from enharmonic equivalents
                                    rootChosen = Pitch(-1)
                                    rootChosen = pitch
                                    rootMenuExpanded = false
                                    onStateChange0(true)
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
                                text = { Text(type) }
                            )
                        }
                    }

                }
            }
        }

        // TODO refactor this after I learn about view models, state, destructuring

        val intervalColor0 = settings.getLong("intervalColor0", 0xffee1b24)
        val intervalColor1 = settings.getLong("intervalColor1", 0xfff15b22)
        val intervalColor2 = settings.getLong("intervalColor2", 0xfff68d1e)
        val intervalColor3 = settings.getLong("intervalColor3", 0xfffdb913)
        val intervalColor4 = settings.getLong("intervalColor4", 0xfffef100)
        val intervalColor5 = settings.getLong("intervalColor5", 0xffc9db2a)
        val intervalColor6 = settings.getLong("intervalColor6", 0xff3ab449)
        val intervalColor7 = settings.getLong("intervalColor7", 0xff00a89d)
        val intervalColor8 = settings.getLong("intervalColor8", 0xff0271bd)
        val intervalColor9 = settings.getLong("intervalColor9", 0xff524ea1)
        val intervalColor10 = settings.getLong("intervalColor10", 0xff672e91)
        val intervalColor11 = settings.getLong("intervalColor11", 0xffb72367)

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
                colors = CheckboxColors(
                    CheckboxDefaults.colors().checkedCheckmarkColor,
                    CheckboxDefaults.colors().uncheckedCheckmarkColor,
                    Color(intervalColor0),
                    CheckboxDefaults.colors().uncheckedBoxColor,
                    CheckboxDefaults.colors().disabledCheckedBoxColor,
                    CheckboxDefaults.colors().disabledUncheckedBoxColor,
                    CheckboxDefaults.colors().disabledIndeterminateBoxColor,
                    Color(intervalColor0),
                    CheckboxDefaults.colors().uncheckedBorderColor,
                    CheckboxDefaults.colors().disabledBorderColor,
                    CheckboxDefaults.colors().disabledUncheckedBorderColor,
                    CheckboxDefaults.colors().disabledIndeterminateBorderColor
                ),
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
                colors = CheckboxColors(
                    CheckboxDefaults.colors().checkedCheckmarkColor,
                    CheckboxDefaults.colors().uncheckedCheckmarkColor,
                    Color(intervalColor1),
                    CheckboxDefaults.colors().uncheckedBoxColor,
                    CheckboxDefaults.colors().disabledCheckedBoxColor,
                    CheckboxDefaults.colors().disabledUncheckedBoxColor,
                    CheckboxDefaults.colors().disabledIndeterminateBoxColor,
                    Color(intervalColor1),
                    CheckboxDefaults.colors().uncheckedBorderColor,
                    CheckboxDefaults.colors().disabledBorderColor,
                    CheckboxDefaults.colors().disabledUncheckedBorderColor,
                    CheckboxDefaults.colors().disabledIndeterminateBorderColor
                ),
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
                colors = CheckboxColors(
                    CheckboxDefaults.colors().checkedCheckmarkColor,
                    CheckboxDefaults.colors().uncheckedCheckmarkColor,
                    Color(intervalColor2),
                    CheckboxDefaults.colors().uncheckedBoxColor,
                    CheckboxDefaults.colors().disabledCheckedBoxColor,
                    CheckboxDefaults.colors().disabledUncheckedBoxColor,
                    CheckboxDefaults.colors().disabledIndeterminateBoxColor,
                    Color(intervalColor2),
                    CheckboxDefaults.colors().uncheckedBorderColor,
                    CheckboxDefaults.colors().disabledBorderColor,
                    CheckboxDefaults.colors().disabledUncheckedBorderColor,
                    CheckboxDefaults.colors().disabledIndeterminateBorderColor
                ),
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
                colors = CheckboxColors(
                    CheckboxDefaults.colors().checkedCheckmarkColor,
                    CheckboxDefaults.colors().uncheckedCheckmarkColor,
                    Color(intervalColor3),
                    CheckboxDefaults.colors().uncheckedBoxColor,
                    CheckboxDefaults.colors().disabledCheckedBoxColor,
                    CheckboxDefaults.colors().disabledUncheckedBoxColor,
                    CheckboxDefaults.colors().disabledIndeterminateBoxColor,
                    Color(intervalColor3),
                    CheckboxDefaults.colors().uncheckedBorderColor,
                    CheckboxDefaults.colors().disabledBorderColor,
                    CheckboxDefaults.colors().disabledUncheckedBorderColor,
                    CheckboxDefaults.colors().disabledIndeterminateBorderColor
                ),
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
                colors = CheckboxColors(
                    CheckboxDefaults.colors().checkedCheckmarkColor,
                    CheckboxDefaults.colors().uncheckedCheckmarkColor,
                    Color(intervalColor4),
                    CheckboxDefaults.colors().uncheckedBoxColor,
                    CheckboxDefaults.colors().disabledCheckedBoxColor,
                    CheckboxDefaults.colors().disabledUncheckedBoxColor,
                    CheckboxDefaults.colors().disabledIndeterminateBoxColor,
                    Color(intervalColor4),
                    CheckboxDefaults.colors().uncheckedBorderColor,
                    CheckboxDefaults.colors().disabledBorderColor,
                    CheckboxDefaults.colors().disabledUncheckedBorderColor,
                    CheckboxDefaults.colors().disabledIndeterminateBorderColor
                ),
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
                colors = CheckboxColors(
                    CheckboxDefaults.colors().checkedCheckmarkColor,
                    CheckboxDefaults.colors().uncheckedCheckmarkColor,
                    Color(intervalColor5),
                    CheckboxDefaults.colors().uncheckedBoxColor,
                    CheckboxDefaults.colors().disabledCheckedBoxColor,
                    CheckboxDefaults.colors().disabledUncheckedBoxColor,
                    CheckboxDefaults.colors().disabledIndeterminateBoxColor,
                    Color(intervalColor5),
                    CheckboxDefaults.colors().uncheckedBorderColor,
                    CheckboxDefaults.colors().disabledBorderColor,
                    CheckboxDefaults.colors().disabledUncheckedBorderColor,
                    CheckboxDefaults.colors().disabledIndeterminateBorderColor
                ),
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
                colors = CheckboxColors(
                    CheckboxDefaults.colors().checkedCheckmarkColor,
                    CheckboxDefaults.colors().uncheckedCheckmarkColor,
                    Color(intervalColor6),
                    CheckboxDefaults.colors().uncheckedBoxColor,
                    CheckboxDefaults.colors().disabledCheckedBoxColor,
                    CheckboxDefaults.colors().disabledUncheckedBoxColor,
                    CheckboxDefaults.colors().disabledIndeterminateBoxColor,
                    Color(intervalColor6),
                    CheckboxDefaults.colors().uncheckedBorderColor,
                    CheckboxDefaults.colors().disabledBorderColor,
                    CheckboxDefaults.colors().disabledUncheckedBorderColor,
                    CheckboxDefaults.colors().disabledIndeterminateBorderColor
                ),
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
                colors = CheckboxColors(
                    CheckboxDefaults.colors().checkedCheckmarkColor,
                    CheckboxDefaults.colors().uncheckedCheckmarkColor,
                    Color(intervalColor7),
                    CheckboxDefaults.colors().uncheckedBoxColor,
                    CheckboxDefaults.colors().disabledCheckedBoxColor,
                    CheckboxDefaults.colors().disabledUncheckedBoxColor,
                    CheckboxDefaults.colors().disabledIndeterminateBoxColor,
                    Color(intervalColor7),
                    CheckboxDefaults.colors().uncheckedBorderColor,
                    CheckboxDefaults.colors().disabledBorderColor,
                    CheckboxDefaults.colors().disabledUncheckedBorderColor,
                    CheckboxDefaults.colors().disabledIndeterminateBorderColor
                ),
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
                colors = CheckboxColors(
                    CheckboxDefaults.colors().checkedCheckmarkColor,
                    CheckboxDefaults.colors().uncheckedCheckmarkColor,
                    Color(intervalColor8),
                    CheckboxDefaults.colors().uncheckedBoxColor,
                    CheckboxDefaults.colors().disabledCheckedBoxColor,
                    CheckboxDefaults.colors().disabledUncheckedBoxColor,
                    CheckboxDefaults.colors().disabledIndeterminateBoxColor,
                    Color(intervalColor8),
                    CheckboxDefaults.colors().uncheckedBorderColor,
                    CheckboxDefaults.colors().disabledBorderColor,
                    CheckboxDefaults.colors().disabledUncheckedBorderColor,
                    CheckboxDefaults.colors().disabledIndeterminateBorderColor
                ),
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
                colors = CheckboxColors(
                    CheckboxDefaults.colors().checkedCheckmarkColor,
                    CheckboxDefaults.colors().uncheckedCheckmarkColor,
                    Color(intervalColor9),
                    CheckboxDefaults.colors().uncheckedBoxColor,
                    CheckboxDefaults.colors().disabledCheckedBoxColor,
                    CheckboxDefaults.colors().disabledUncheckedBoxColor,
                    CheckboxDefaults.colors().disabledIndeterminateBoxColor,
                    Color(intervalColor9),
                    CheckboxDefaults.colors().uncheckedBorderColor,
                    CheckboxDefaults.colors().disabledBorderColor,
                    CheckboxDefaults.colors().disabledUncheckedBorderColor,
                    CheckboxDefaults.colors().disabledIndeterminateBorderColor
                ),
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
                colors = CheckboxColors(
                    CheckboxDefaults.colors().checkedCheckmarkColor,
                    CheckboxDefaults.colors().uncheckedCheckmarkColor,
                    Color(intervalColor10),
                    CheckboxDefaults.colors().uncheckedBoxColor,
                    CheckboxDefaults.colors().disabledCheckedBoxColor,
                    CheckboxDefaults.colors().disabledUncheckedBoxColor,
                    CheckboxDefaults.colors().disabledIndeterminateBoxColor,
                    Color(intervalColor10),
                    CheckboxDefaults.colors().uncheckedBorderColor,
                    CheckboxDefaults.colors().disabledBorderColor,
                    CheckboxDefaults.colors().disabledUncheckedBorderColor,
                    CheckboxDefaults.colors().disabledIndeterminateBorderColor
                ),
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "♯6/♭7",
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
                colors = CheckboxColors(
                    CheckboxDefaults.colors().checkedCheckmarkColor,
                    CheckboxDefaults.colors().uncheckedCheckmarkColor,
                    Color(intervalColor11),
                    CheckboxDefaults.colors().uncheckedBoxColor,
                    CheckboxDefaults.colors().disabledCheckedBoxColor,
                    CheckboxDefaults.colors().disabledUncheckedBoxColor,
                    CheckboxDefaults.colors().disabledIndeterminateBoxColor,
                    Color(intervalColor11),
                    CheckboxDefaults.colors().uncheckedBorderColor,
                    CheckboxDefaults.colors().disabledBorderColor,
                    CheckboxDefaults.colors().disabledUncheckedBorderColor,
                    CheckboxDefaults.colors().disabledIndeterminateBorderColor
                ),
                modifier = Modifier.scale(0.9f),
                onCheckedChange = null // null recommended for accessibility with screenreaders
            )
            Text(
                text = "7",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        val currentPitches: MutableList<Pitch> = mutableListOf()

        val checkedStates = listOf(
            checkedState0, checkedState1, checkedState2, checkedState3, checkedState4,
            checkedState5, checkedState6, checkedState7, checkedState8, checkedState9,
            checkedState10, checkedState11
        )

        if (rootChosen.midiValue != -1) {
            currentGuitar.chordMemory.root = rootChosen
            for ((index, state) in checkedStates.withIndex()) {
                if (state) {
                    currentPitches.add(Pitch(rootChosen.midiValue + index))
                }
            }
        }

        if (currentPitches.size > 0) {

            //val currentChord = Chord(currentPitches)

            val currentChordInterpretation = ChordInterpretation(rootChosen, rootChosen, currentPitches, rootAlreadyHasReading = rootChosen.chosenReading.accidental.type)

            currentGuitar.chordMemory.pitchClassIntValues.clear()
            currentGuitar.chordMemory.noteNames.clear()

            for (pitch in currentChordInterpretation.chosenPitches) {
                currentGuitar.chordMemory.pitchClassIntValues.add(pitch.midiValue % 12)
                currentGuitar.chordMemory.noteNames.add(pitch.chosenReading.name)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        FilledTonalButton(
            onClick = {
                rootChosen = Pitch(-1)
                currentPitches.clear()
                currentGuitar.chordMemory.root = Pitch(-1)
                currentGuitar.chordMemory.noteNames.clear()
                currentGuitar.chordMemory.pitchClassIntValues.clear()
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
            Text("Clear Intervals")
        }
    }
}