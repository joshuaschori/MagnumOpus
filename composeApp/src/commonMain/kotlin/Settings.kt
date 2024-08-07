import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import classes.GuitarString
import classes.Pitch
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SettingsListener
import kotlin.math.roundToInt

// TODO capo? add lower or higher string buttons?
// TODO make scrollable when there's more guitar strings? whole window or that section?
// TODO implement adding and removing strings

@Composable
fun Settings(navigationState: String, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = innerPadding)
            .verticalScroll(rememberScrollState())
    ) {

        val numberOfStrings: Int = settings.getInt("number of strings", 6)

        var expanded by remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()
        val lazyListState = rememberLazyListState()
        val stringMidiValues: MutableList<Int> = mutableStateListOf()

        var sliderPosition by remember { mutableFloatStateOf(settings.getInt("number of frets", 12).toFloat()) }

        val startScreenString: String = settings.getString("startScreen", "Home")
        val radioOptions = listOf("Home", "Chord Display", "Chord Identification", "Settings")
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(startScreenString) }

        // if no tuning settings, populate with default tuning
        if (!settings.hasKey("1 string tuning")) {
            settings.putInt("6 string tuning", 16)
            settings.putInt("5 string tuning", 21)
            settings.putInt("4 string tuning", 26)
            settings.putInt("3 string tuning", 31)
            settings.putInt("2 string tuning", 35)
            settings.putInt("1 string tuning", 40)
        }

        // if list is empty, populate it with tuning midi values
        if (stringMidiValues.isEmpty()) {
            repeat(numberOfStrings) { index ->
                val stringIndex = index + 1

                stringMidiValues.add(settings.getInt("$stringIndex string tuning", 0))
            }
        }

        // if string tuning is flagged to update, update and clear flags
        if (
            settings.getInt("updateString", 0) != 0 &&
            settings.getInt("updateStringMidiValue", 999) != 999
        ) {
            val updateString = settings.getInt("updateString", 0)
            val updateStringMidiValue = settings.getInt("updateStringMidiValue", 999)

            settings.putInt("$updateString string tuning", updateStringMidiValue)
            settings.putInt("updateString", 0)
            settings.putInt("updateStringMidiValue", 999)
        }

        Row {
            Text(
                "Guitar Setup:",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(
                        top = insetVertical.dp,
                        start = insetHorizontal.dp
                    )
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Text(
                "Strings:",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = insetHorizontal.dp * 2)
                //.align(Alignment.CenterVertically)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Column(
                modifier = Modifier
                    .padding(
                        start = insetHorizontal.dp * 2,
                    )
            ) {
                Text(
                    "Add",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                repeat(numberOfStrings) {
                    OutlinedButton(
                        onClick = {

                            if (numberOfStrings < 12) {
                                repeat(numberOfStrings - it) { index ->

                                    val stringNumber = numberOfStrings + 1 - index
                                    val stringPitch = settings.getInt("${stringNumber - 1} string tuning", 0)

                                    settings.putInt("$stringNumber string tuning", stringPitch)
                                }

                                settings.putInt("number of strings", settings.getInt("number of strings", 6) + 1)

                                stringMidiValues.removeAll(stringMidiValues)
                            }

                        }
                    ) {
                        Icon(
                            Icons.Outlined.Add,
                            contentDescription = "Add String",
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column() {
                Text(
                    "Remove",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )

                repeat(numberOfStrings) {
                    OutlinedButton(
                        onClick = {
                            if (numberOfStrings > 3) {
                                repeat(numberOfStrings - it - 1) { index ->

                                    val stringNumber = it + 1 + index
                                    val stringPitch = settings.getInt("${it + 2 + index} string tuning", 0)

                                    settings.putInt("$stringNumber string tuning", stringPitch)
                                }

                                settings.remove("${numberOfStrings} string tuning")
                                settings.putInt("number of strings", settings.getInt("number of strings", 6) - 1)

                                stringMidiValues.removeAll(stringMidiValues)
                            }
                        }
                    ) {
                        Icon(
                            Icons.Outlined.Remove,
                            contentDescription = "Remove String"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(30.dp))

            Column() {

                Text(
                    "Change Tuning:",
                    fontSize = 14.sp
                )

                // create string buttons
                repeat(numberOfStrings) { index ->
                    val stringIndex = index + 1
                    val defaultValue = when (stringIndex) {
                        6 -> 16
                        5 -> 21
                        4 -> 26
                        3 -> 31
                        2 -> 35
                        1 -> 40
                        else -> 0
                    }

                    fun ordinalNumbers(inputNumber: Int): String {
                        val stringSuffix: String = when (inputNumber % 10) {
                            1 -> "st"
                            2 -> "nd"
                            3 -> "rd"
                            else -> "th"
                        }

                        return inputNumber.toString() + stringSuffix
                    }

                    Row {

                        val stringText = ordinalNumbers(stringIndex)

                        Text(
                            "$stringText String: ",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )

                        OutlinedButton(
                            onClick = {
                                expanded = true
                                settings.putInt("updateString", stringIndex)
                                val tempValue = settings.getInt("$stringIndex string tuning", 0)
                                val selectedNote = if (tempValue > 0) {
                                    tempValue
                                } else {
                                    0
                                }
                                settings.putInt("clickedStringPitch", selectedNote)
                            }
                        ) {

                            val stringPitch =
                                Pitch(settings.getInt("$stringIndex string tuning", defaultValue))

                            var stringNoteName = ""
                            if (stringPitch.hasNatural) {
                                stringNoteName =
                                    stringPitch.naturalReading.name + stringPitch.octave
                            } else {
                                stringNoteName =
                                    "${stringPitch.sharpReading.name}/${stringPitch.flatReading.name}${stringPitch.octave}"
                            }

                            Text(stringNoteName)

                        }

                    }
                }

                // menu when string button is clicked
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    scrollState = scrollState,
                ) {

                    val listOfPitches: List<Int> = (0..127).map {
                        it
                    }

                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier
                            .width(200f.dp)
                            .height(500f.dp)
                    ) {
                        itemsIndexed(listOfPitches) { index, _ ->

                            // TODO redundant functionality from before
                            val itemPitch = Pitch(index)
                            var stringNoteName = ""
                            if (itemPitch.hasNatural) {
                                stringNoteName = remember { itemPitch.naturalReading.name + itemPitch.octave }
                            } else {
                                stringNoteName =
                                    remember { "${itemPitch.sharpReading.name}/${itemPitch.flatReading.name}${itemPitch.octave}" }
                            }

                            DropdownMenuItem(
                                onClick = {
                                    settings.putInt("updateStringMidiValue", index)
                                    expanded = false
                                },
                                text = {
                                    Text(
                                        stringNoteName,
                                        fontWeight = if (itemPitch.midiValue == settings.getInt("clickedStringPitch", -1)) {
                                            FontWeight.Bold
                                        }
                                        else {
                                            FontWeight.Normal
                                        }
                                    )
                                },
                                modifier = if (itemPitch.midiValue == settings.getInt("clickedStringPitch", -1)) {
                                    Modifier
                                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                                }
                                else {
                                    Modifier
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                }
                            )
                        }
                    }
                }
                LaunchedEffect(expanded) {
                    if (expanded) {
                        lazyListState.scrollToItem((settings.getInt("clickedStringPitch", 0) - 4))
                    }
                }

            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        Column {

            Row {
                Text(
                    "Number of Frets: ${sliderPosition.toInt()}",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(start = insetHorizontal.dp * 2)
                    //.align(Alignment.CenterVertically)
                )
            }

            Column {
                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it
                        settings.putInt("number of frets", it.toInt())
                    },
                    steps = 10,
                    valueRange = 12f..24f,
                    modifier = Modifier
                        .padding(start = insetHorizontal.dp * 2)
                        .width(350f.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // make radio buttons for start screen
        Row(
            modifier = Modifier
                .padding(
                    start = insetHorizontal.dp
                )
        ) {
        // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
            Column(Modifier.selectableGroup()) {
                Text(
                    "Start Screen:",
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                radioOptions.forEach { text ->
                    Row(
                        Modifier.fillMaxWidth()
                            //.height(56.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    onOptionSelected(text)
                                    settings.putString("startScreen", text)
                                }
                            )
                            .padding(horizontal = insetHorizontal.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(start = 10.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .padding(
                    start = insetHorizontal.dp
                )
        ) {
            Text(
                "Reset:",
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(20.dp))

            FilledTonalButton(
                onClick = {
                    settings.clear()
                    stringMidiValues.removeAll(stringMidiValues)
                    onOptionSelected("Home")
                    sliderPosition = 12f
                }
            ) {
                Text("Restore Defaults")
            }

        }

        Spacer(modifier = Modifier.height(30.dp))

    }
}