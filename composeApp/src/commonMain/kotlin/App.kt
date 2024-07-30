import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.math.floor

/*
    TODO make the drawer push content right
    TODO decide whether gesture will open drawer? where on screen?
    TODO try emulating on iphone?
    TODO touch listener, touch event, modifier touch listener compose
    TODO x's for each string?
    TODO parity between x / y fret and string spacing
    TODO GIT github
    TODO mutablestatelistof? deal with recomposition and fret memory
    TODO ViewModel
    TODO room and shared preferences for android, sql delite for multiplatform, ORM, wrappers
    TODO FretMemory can calculate x and y?
    TODO horizontal or vertical alignment? horizontal better for reading guitar
*/

const val insetHorizontal = 50f
const val insetVertical = 50f
val settings: Settings = Settings()

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSerializationApi::class,
    ExperimentalSettingsApi::class
)
@Composable
fun App() {


    // TODO play around with multiplatform settings library
    val settingExample = "guitar 1"
    settings.putString("guitar selected", "9")
    val guitarFromPreferences = settings.getString("guitar selected", "default")
    println(guitarFromPreferences)

    val guitarExample: Guitar = Guitar(12)
    // TODO can't encode mutable state
    //settings.encodeValue(Guitar.serializer(), "Current Guitar", guitarExample)




    MaterialTheme() {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()

        // TODO finish navigation drawer
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Text("Drawer title", modifier = Modifier.padding(16.dp))
                    Divider()
                    NavigationDrawerItem(
                        label = { Text(text = "Drawer Item") },
                        selected = false,
                        onClick = { }
                    )
                    // ...other drawer items
                }
            },
            gesturesEnabled = false
        ) {
            Scaffold(
                topBar = {
                    // TODO finish top app bar
                    TopAppBar(
                        actions = {
                            IconButton(onClick = { /* do something */ }) {
                                Icon(Icons.Filled.MoreVert, contentDescription = "Localized description")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }
                            ) {
                                Icon(Icons.Filled.Dehaze, contentDescription = "Localized description")
                            }
                        },
                        title = {
                            Text("Magnum Opus")
                        }
                    )
                },
                // TODO finish bottom bar
                bottomBar = {
                    BottomAppBar(
                        actions = {
                            IconButton(onClick = { /* do something */ }) {
                                Icon(Icons.Filled.Check, contentDescription = "Localized description")
                            }
                            IconButton(onClick = { /* do something */ }) {
                                Icon(
                                    Icons.Filled.Edit,
                                    contentDescription = "Localized description",
                                )
                            }
                            IconButton(onClick = { /* do something */ }) {
                                Icon(
                                    Icons.Filled.Home,
                                    contentDescription = "Localized description",
                                )
                            }
                            IconButton(onClick = { /* do something */ }) {
                                Icon(
                                    Icons.Filled.Star,
                                    contentDescription = "Localized description",
                                )
                            }
                        },
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = { /* do something */ },
                                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                            ) {
                                Icon(Icons.Filled.Add, "Localized description")
                            }
                        }
                    )
                }
            ) { innerPadding ->
                // content on screen
                val navigationState: String = "Home"
                if (navigationState == "Home") {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(paddingValues = innerPadding)
                    ) {

                        // TODO multiple guitars / guitar options in settings
                        var currentGuitar: Guitar by remember { mutableStateOf(Guitar(12, isDefaultGuitar = true)) }

                        GuitarCanvas(
                            innerPadding = innerPadding,
                            currentGuitar = currentGuitar,
                            onGuitarChange = {
                                currentGuitar = it
                            }
                        )

                        Column() {
                            val currentPitches: MutableList<Pitch> = mutableStateListOf()

                            repeat(currentGuitar.numberOfStrings) {stringIndex ->
                                if (currentGuitar.fretMemory[stringIndex].visible) {
                                    currentPitches.add(
                                        Pitch(currentGuitar.strings[stringIndex].tuning.midiValue + currentGuitar.fretMemory[stringIndex].fretSelected)
                                    )
                                }
                            }

                            val currentNoteNames: MutableList<String> = mutableStateListOf()
                            var currentChordName: String = ""
                            var currendChordExtensionsPrefix: String = ""
                            var currentChordExtensions: String = ""

                            if (currentPitches.size > 0) {
                                val currentChord = Chord(currentPitches)
                                currentChordName = currentChord.chosenChordName
                                currendChordExtensionsPrefix = currentChord.chosenChordExtensionsPrefix
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
                                        append(currendChordExtensionsPrefix)
                                        append(currentChordExtensions)
                                    }
                                },
                                fontSize = 20.sp,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GuitarCanvas(
    innerPadding: PaddingValues,
    currentGuitar: Guitar,
    onGuitarChange: (Guitar) -> Unit
) {
    Canvas(modifier = Modifier
        .fillMaxHeight()
        .width((insetHorizontal * 2 + currentGuitar.stringSpacing * 5).dp)
        .padding(bottom = innerPadding.calculateBottomPadding())
        .pointerInput(Unit) {
            detectTapGestures(
                // update fretMemory if the coordinates of a fret are clicked
                onPress = {
                    val pointerX = it.x
                    val pointerY = it.y

                    // determine if you have clicked within range of a string
                    for ((stringIndex, string) in currentGuitar.stringLocations.withIndex()) {
                        if (string.dp.toPx() in pointerX - insetHorizontal.dp.toPx() - currentGuitar.fretSelectionRadius.dp.toPx()
                            ..pointerX - insetHorizontal.dp.toPx() + currentGuitar.fretSelectionRadius.dp.toPx()) {
                            // determine if you have clicked within range of a fret
                            for ((fretIndex, fret) in currentGuitar.fretLocations.withIndex()) {
                                if (fret.dp.toPx() in pointerY - insetVertical.dp.toPx() - currentGuitar.fretSelectionRadius.dp.toPx()
                                    ..pointerY - insetVertical.dp.toPx() + currentGuitar.fretSelectionRadius.dp.toPx()) {
                                    // if you have clicked on a fret that is already selected, then deselect it
                                    if (
                                        currentGuitar.fretMemory[stringIndex].y == fret && currentGuitar.fretMemory[stringIndex].visible
                                    ) {
                                        currentGuitar.fretMemory[stringIndex].x = currentGuitar.stringSpacing * stringIndex
                                        currentGuitar.fretMemory[stringIndex].y = 0f - currentGuitar.fretSpacing * 0.5f
                                        currentGuitar.fretMemory[stringIndex].fretSelected = 0
                                        currentGuitar.fretMemory[stringIndex].visible = false
                                    }
                                    // else store the coordinates of the newly selected fret in fret memory
                                    else {
                                        currentGuitar.fretMemory[stringIndex].x = currentGuitar.stringSpacing * stringIndex
                                        currentGuitar.fretMemory[stringIndex].y = fret
                                        currentGuitar.fretMemory[stringIndex].fretSelected = fretIndex
                                        currentGuitar.fretMemory[stringIndex].visible = true
                                    }
                                }
                            }
                        }
                    }
                    val newGuitar: Guitar = currentGuitar.copy(fretMemory = currentGuitar.fretMemory.toMutableList())
                    onGuitarChange.invoke(newGuitar)
                },
            )
        }
    ) {

        inset(horizontal = insetHorizontal.dp.toPx(), vertical = insetVertical.dp.toPx()) {
            // draw the guitar strings
            repeat(currentGuitar.numberOfStrings) { stringIndex ->
                drawLine(
                    color = currentGuitar.lineColor,
                    start = Offset(
                        x = 0f + currentGuitar.stringSpacing.dp.toPx() * stringIndex,
                        y = 0f
                    ),
                    end = Offset(
                        x = 0f + currentGuitar.stringSpacing.dp.toPx() * stringIndex,
                        // y starts at 2f because otherwise it doesn't quite intersect with frets
                        y = 2f + currentGuitar.fretboardLength.dp.toPx()
                    ),
                    strokeWidth = currentGuitar.strings[stringIndex].thickness.dp.toPx()
                )
            }
            // draw the guitar frets (and guitar nut at the top of the guitar)
            repeat(currentGuitar.numberOfFrets + 1) { fretIndex ->
                drawLine(
                    color = currentGuitar.lineColor,
                    start = Offset(
                        x = 0f,
                        // y starts at 1f because otherwise it doesn't quite intersect with strings
                        y = 1f + currentGuitar.fretSpacing.dp.toPx() * fretIndex
                    ),
                    end = Offset(
                        x = currentGuitar.stringSpacing.dp.toPx() * (currentGuitar.numberOfStrings - 1),
                        // y starts at 1f because otherwise it doesn't quite intersect with strings
                        y = 1f + currentGuitar.fretSpacing.dp.toPx() * fretIndex
                    ),
                    strokeWidth = currentGuitar.fretThickness.dp.toPx()
                )
            }

            // create fret markers
            repeat(currentGuitar.numberOfFrets + 1) { fretIndex ->
                // make fret markers centered if the number of strings is even
                if (currentGuitar.numberOfStrings % 2 == 0) {
                    // draw the fret markers that have a single circle
                    if (
                        fretIndex + 1 in currentGuitar.fretMarkers &&
                        (fretIndex + 1) % 12 != 0
                    ) {
                        drawCircle(
                            currentGuitar.lineColor,
                            radius = currentGuitar.fretMarkerSize.dp.toPx(),
                            center = Offset(
                                x = (currentGuitar.stringSpacing.dp.toPx() * (currentGuitar.numberOfStrings - 1)) / 2,
                                y = currentGuitar.fretSpacing.dp.toPx() * (fretIndex + 0.5).toFloat()
                            )
                        )
                    }
                    // draw the fret markers that have two circles
                    else if (fretIndex + 1 in currentGuitar.fretMarkers && (fretIndex + 1) % 12 == 0) {
                        drawCircle(
                            currentGuitar.lineColor,
                            radius = currentGuitar.fretMarkerSize.dp.toPx(),
                            center = Offset(
                                x = currentGuitar.stringSpacing.dp.toPx() * 1.5.toFloat(),
                                y = currentGuitar.fretSpacing.dp.toPx() * (fretIndex + 0.5).toFloat()
                            )
                        )
                        drawCircle(
                            currentGuitar.lineColor,
                            radius = currentGuitar.fretMarkerSize.dp.toPx(),
                            center = Offset(
                                x = (currentGuitar.stringSpacing.dp.toPx() * (currentGuitar.numberOfStrings - 2))
                                        - 0.5.toFloat() * currentGuitar.stringSpacing.dp.toPx(),
                                y = currentGuitar.fretSpacing.dp.toPx() * (fretIndex + 0.5).toFloat()
                            )
                        )
                    }
                }
                // make markers offset if the number of strings is odd, so that it doesn't overlap a string
                else if (currentGuitar.numberOfStrings % 2 != 0) {
                    // draw the fret markers that have a single circle
                    if (
                        fretIndex + 1 in currentGuitar.fretMarkers &&
                        (fretIndex + 1) % 12 != 0
                    ) {
                        drawCircle(
                            currentGuitar.lineColor,
                            radius = currentGuitar.fretMarkerSize.dp.toPx(),
                            center = Offset(
                                x = currentGuitar.stringSpacing.dp.toPx() / 2,
                                y = currentGuitar.fretSpacing.dp.toPx() * (fretIndex + 0.5).toFloat()
                            )
                        )
                    }
                    // draw the fret markers that have two circles
                    else if (fretIndex + 1 in currentGuitar.fretMarkers && (fretIndex + 1) % 12 == 0) {
                        drawCircle(
                            currentGuitar.lineColor,
                            radius = currentGuitar.fretMarkerSize.dp.toPx(),
                            center = Offset(
                                x = currentGuitar.stringSpacing.dp.toPx() / 2,
                                y = currentGuitar.fretSpacing.dp.toPx() * (fretIndex + 0.5).toFloat()
                            )
                        )
                        drawCircle(
                            currentGuitar.lineColor,
                            radius = currentGuitar.fretMarkerSize.dp.toPx(),
                            center = Offset(
                                x = currentGuitar.stringSpacing.dp.toPx() * 1.5.toFloat(),
                                y = currentGuitar.fretSpacing.dp.toPx() * (fretIndex + 0.5).toFloat()
                            )
                        )
                    }
                }
            }

            repeat(currentGuitar.numberOfStrings) { stringIndex ->
                // create indications of fretted notes
                if (currentGuitar.fretMemory[stringIndex].visible) {
                    drawCircle(
                        currentGuitar.lineColor,
                        radius = currentGuitar.frettedNoteSize.dp.toPx(),
                        style = Stroke(width = currentGuitar.lineThickness.dp.toPx()),
                        center = Offset(
                            x = currentGuitar.fretMemory[stringIndex].x.dp.toPx(),
                            y = currentGuitar.fretMemory[stringIndex].y.dp.toPx()
                        )
                    )
                }
                // create X shapes for strings that aren't being played
                else {
                    val path = Path()
                    path.moveTo(
                        currentGuitar.fretMemory[stringIndex].x.dp.toPx() - currentGuitar.unfrettedSize.dp.toPx(),
                        currentGuitar.fretMemory[stringIndex].y.dp.toPx() - currentGuitar.unfrettedSize.dp.toPx()
                    )
                    path.lineTo(
                        currentGuitar.fretMemory[stringIndex].x.dp.toPx() + currentGuitar.unfrettedSize.dp.toPx(),
                        currentGuitar.fretMemory[stringIndex].y.dp.toPx() + currentGuitar.unfrettedSize.dp.toPx()
                    )
                    path.moveTo(
                        currentGuitar.fretMemory[stringIndex].x.dp.toPx() + currentGuitar.unfrettedSize.dp.toPx(),
                        currentGuitar.fretMemory[stringIndex].y.dp.toPx() - currentGuitar.unfrettedSize.dp.toPx()
                    )
                    path.lineTo(
                        currentGuitar.fretMemory[stringIndex].x.dp.toPx() - currentGuitar.unfrettedSize.dp.toPx(),
                        currentGuitar.fretMemory[stringIndex].y.dp.toPx() + currentGuitar.unfrettedSize.dp.toPx()
                    )
                    path.close()
                    drawPath(path, currentGuitar.lineColor, style = Stroke(width = currentGuitar.lineThickness.dp.toPx()))
                }
            }
        }
    }
}
