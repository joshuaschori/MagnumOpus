import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.theme.AppTheme
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

/*
    TODO try emulating on iphone?
    TODO parity between x / y fret and string spacing
    TODO GIT github
    TODO mutableStateListOf? deal with recomposition and fret memory
    TODO ViewModel
    TODO room and shared preferences for android, sql deLite for multiplatform, ORM, wrappers
    TODO classes.FretMemory can calculate x and y?
    TODO horizontal or vertical alignment? horizontal better for reading guitar
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

const val insetHorizontal = 24f
const val insetVertical = 24f
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
                        label = { Text(text = "Interval Display") },
                        selected = false,
                        onClick = {
                            navigationState = "Interval Display"
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
                                Icon(Icons.Filled.MusicNote, contentDescription = "Localized description")
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
                        },
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

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    when (navigationState) {
                        "Interval Display" -> IntervalDisplay(navigationState = navigationState, innerPadding = innerPadding)
                        "Chord Identification" -> ChordIdentification(navigationState = navigationState, innerPadding = innerPadding)
                        "Home" -> Home(navigationState = navigationState, innerPadding = innerPadding)
                        "Settings" -> Settings(innerPadding = innerPadding)
                    }
                }

            }
        }
    }
}