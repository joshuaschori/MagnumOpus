import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.Monitor
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
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
    ExperimentalSettingsApi::class, ExperimentalAnimationApi::class
)
@Composable
fun App() {
    var navigationState: String by remember { mutableStateOf(settings.getString("startScreen", "Home")) }
    var currentGuitar: Guitar by remember { mutableStateOf(Guitar(isDefaultGuitar = true)) }

    AppTheme {
        var showSettings by remember { mutableStateOf(false) }
        var animationDone by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                if (!showSettings || (showSettings && !animationDone)) {
                    TopAppBar(
                        title = {
                            Text(navigationState, modifier = Modifier.padding(start = 30.dp))
                        },
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
                            IconButton(
                                onClick = { showSettings = !showSettings }
                            ) {
                                Icon(Icons.Filled.Settings, contentDescription = "Settings")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                    )
                }
                else {
                    AnimatedVisibility(showSettings) {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        showSettings = !showSettings
                                    }
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Menu")
                                }
                            },
                            title = {
                                Text("Settings")
                            },
                        )
                    }
                }
            },

            bottomBar = {
                if (!showSettings || (showSettings && !animationDone)) {
                    Column {
                        HorizontalDivider (
                            color = Color.Gray,
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                        )
                        NavigationBar{
                            NavigationBarItem(
                                icon = { Icon(Icons.Filled.Search, contentDescription = "Identify") },
                                label = { Text("Identify") },
                                selected = navigationState == "Chord Identification",
                                onClick = { navigationState = "Chord Identification" },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.Black,
                                    selectedTextColor = Color.Black,
                                    indicatorColor = Color.White,
                                    unselectedIconColor = Color.Gray,
                                    unselectedTextColor = Color.Gray
                                )
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Filled.Visibility, contentDescription = "Display") },
                                label = { Text("Display") },
                                selected = navigationState == "Interval Display",
                                onClick = { navigationState = "Interval Display" },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.Black,
                                    selectedTextColor = Color.Black,
                                    indicatorColor = Color.White,
                                    unselectedIconColor = Color.Gray,
                                    unselectedTextColor = Color.Gray
                                )
                            )
                        }
                    }
                }
            }

        ) { innerPadding ->
            // content on screen

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                if (!showSettings || (showSettings && !animationDone)) {
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
                    }
                }

                AnimatedVisibility(showSettings) {
                    if (this.transition.currentState == this.transition.targetState) {
                        animationDone = true
                    }
                    Settings(
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