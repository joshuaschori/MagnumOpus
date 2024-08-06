import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.lightColors
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import classes.Chord
import classes.Guitar
import classes.Pitch
import com.example.compose.AppTheme
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

/*
    TODO make the drawer push content right
    TODO decide whether gesture will open drawer? where on screen?
    TODO try emulating on iphone?
    TODO touch listener, touch event, modifier touch listener compose
    TODO parity between x / y fret and string spacing
    TODO GIT github
    TODO mutablestatelistof? deal with recomposition and fret memory
    TODO ViewModel
    TODO room and shared preferences for android, sql delite for multiplatform, ORM, wrappers
    TODO classes.FretMemory can calculate x and y?
    TODO horizontal or vertical alignment? horizontal better for reading guitar
    TODO capo ! !!!
    TODO separate fret memory from currentGuitar, most of currentGuitar can be global settings, each page has fretMemory
    TODO add lower string or higher string, to retain other note tunings
    TODO select from named common tunings
    TODO extract string resources
    // TODO play around with multiplatform settings library
    //val settingExample = "guitar 1"
    //settings.putString("guitar selected", "9")
    //val guitarFromPreferences = settings.getString("guitar selected", "default")
    //println(guitarFromPreferences)
    //val guitarExample: Guitar = Guitar(12)
    // TODO can't encode mutable state
    //settings.encodeValue(classes.Guitar.serializer(), "Current classes.Guitar", guitarExample)
    //settings.putString("number of strings", "6")
    // val currentNumberOfStrings = settings.getInt("number of strings", 6)
    // println(currentNumberOfStrings)
*/

const val insetHorizontal = 50f
const val insetVertical = 50f
val settings: Settings = Settings()

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSerializationApi::class,
    ExperimentalSettingsApi::class
)
@Composable
fun App() {

    var navigationState: String by remember { mutableStateOf(settings.getString("startScreen", "Home")) }

    AppTheme() {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()

        // TODO finish navigation drawer
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Text("Magnum Opus", modifier = Modifier.padding(16.dp))
                    Divider()
                    NavigationDrawerItem(
                        label = { Text(text = "Home") },
                        selected = false,
                        onClick = {
                            navigationState = "Home"
                            coroutineScope.launch {
                                drawerState.apply {
                                    if (isOpen) close()
                                }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Chord Display") },
                        selected = false,
                        onClick = {
                            navigationState = "Chord Display"
                            coroutineScope.launch {
                                drawerState.apply {
                                    if (isOpen) close()
                                }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Chord Identification") },
                        selected = false,
                        onClick = {
                            navigationState = "Chord Identification"
                            coroutineScope.launch {
                                drawerState.apply {
                                    if (isOpen) close()
                                }
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Settings") },
                        selected = false,
                        onClick = {
                            navigationState = "Settings"
                            coroutineScope.launch {
                                drawerState.apply {
                                    if (isOpen) close()
                                }
                            }
                        }
                    )
                }
            },
            gesturesEnabled = drawerState.isOpen
        ) {
            Scaffold(
                topBar = {
                    // TODO finish top app bar
                    TopAppBar(
                        /*

                        actions = {
                            IconButton(onClick = { /* do something */ }) {
                                Icon(Icons.Filled.MoreVert, contentDescription = "Localized description")
                            }
                        },

                         */

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
                                Icon(Icons.Filled.Dehaze, contentDescription = "Menu")
                            }
                        },
                        title = {
                            Text(navigationState)
                        }
                    )
                },

                /*

                // TODO finish bottom bar
                bottomBar = {
                    BottomAppBar(
                        actions = {
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
                                Icon(Icons.Filled.Refresh, "Localized description")
                            }
                        }
                    )
                }

                 */

            ) { innerPadding ->
                // content on screen

                when (navigationState) {
                    "Chord Display" -> ChordDisplay(navigationState = navigationState, innerPadding = innerPadding)
                    "Chord Identification" -> ChordIdentification(navigationState = navigationState, innerPadding = innerPadding)
                    "Home" -> Home(navigationState = navigationState, innerPadding = innerPadding)
                    "Settings" -> Settings(navigationState = navigationState, innerPadding = innerPadding)
                }
            }
        }
    }
}