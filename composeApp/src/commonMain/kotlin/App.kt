import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import classes.Guitar
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import ui.theme.AppTheme

const val insetHorizontal = 24f
const val insetVertical = 24f
val settings: Settings = Settings()

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSerializationApi::class,
    ExperimentalSettingsApi::class
)
@Composable
fun App() {
    var navigationState: String by remember { mutableStateOf(settings.getString("startScreen", "Home")) }
    var currentGuitar: Guitar by remember { mutableStateOf(Guitar(isDefaultGuitar = true)) }

    AppTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val coroutineScope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Text("Magnum Opus", modifier = Modifier.padding(16.dp))
                    HorizontalDivider()
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
                        actions = {

                            Column {
                                Text(
                                    "Tuning:",
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(top = (insetVertical / 2).dp, end = insetHorizontal.dp)
                                )
                                Text(
                                    currentGuitar.currentTuningNoteNames.joinToString("  "),
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .widthIn(150.dp)
                                        .heightIn(40.dp)
                                )
                            }

                            /*


                            IconButton(onClick = { /* do something */ }) {
                                Icon(Icons.Filled.MusicNote, contentDescription = "Localized description")
                            }


                             */
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
                        "Interval Display" -> IntervalDisplay(
                            navigationState = navigationState,
                            innerPadding = innerPadding,
                            currentGuitar = currentGuitar,
                            onGuitarChange = {
                                currentGuitar = it
                            }
                        )
                        "Chord Identification" -> ChordIdentification(
                            navigationState = navigationState,
                            innerPadding = innerPadding,
                            currentGuitar = currentGuitar,
                            onGuitarChange = {
                                currentGuitar = it
                            }
                        )
                        "Home" -> Home(
                            navigationState = navigationState,
                            innerPadding = innerPadding
                        )
                        "Settings" -> Settings(
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
    }
}