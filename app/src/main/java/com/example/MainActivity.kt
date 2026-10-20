package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
import com.example.ui.MainViewModel
import com.example.ui.components.TopBarHeader
import com.example.ui.screens.*
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                TubeAutomateApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun TubeAutomateApp(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Show toast message if any
    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    // Handle back button on sub-screens to return to Studio
    BackHandler(enabled = uiState.currentScreen != AppScreen.STUDIO) {
        viewModel.navigateTo(AppScreen.STUDIO)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = StudioBackground,
        topBar = {
            TopBarHeader(
                currentScreen = uiState.currentScreen,
                channelName = uiState.youtubeChannelName,
                onSettingsClick = {
                    if (uiState.currentScreen == AppScreen.SETTINGS) {
                        viewModel.navigateTo(AppScreen.STUDIO)
                    } else {
                        viewModel.navigateTo(AppScreen.SETTINGS)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = StudioSurface,
                tonalElevation = 8.dp,
                windowInsets = NavigationBarDefaults.windowInsets,
                modifier = Modifier.testTag("main_bottom_nav")
            ) {
                val navItems = listOf(
                    Triple(AppScreen.STUDIO, "Studio", Icons.Default.Bolt),
                    Triple(AppScreen.AI_DIRECTORY, "50+ AI", Icons.Default.AutoAwesome),
                    Triple(AppScreen.TRENDS, "Trends", Icons.Default.TrendingUp),
                    Triple(AppScreen.MONETIZATION, "Revenue", Icons.Default.MonetizationOn),
                    Triple(AppScreen.QUEUE, "Uploads", Icons.Default.CloudUpload)
                )

                navItems.forEach { (screen, label, icon) ->
                    val isSelected = uiState.currentScreen == screen
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.navigateTo(screen) },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = if (isSelected) Color.White else TextMuted
                            )
                        },
                        label = {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) TextPrimary else TextMuted
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = YouTubeRed,
                            selectedIconColor = Color.White,
                            unselectedIconColor = TextMuted,
                            selectedTextColor = TextPrimary,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_${screen.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentScreen) {
                AppScreen.STUDIO -> StudioScreen(uiState = uiState, viewModel = viewModel)
                AppScreen.AI_DIRECTORY -> AiDirectoryScreen(uiState = uiState, viewModel = viewModel)
                AppScreen.TRENDS -> TrendsRadarScreen(uiState = uiState, viewModel = viewModel)
                AppScreen.MONETIZATION -> MonetizationScreen(uiState = uiState, viewModel = viewModel)
                AppScreen.QUEUE -> UploadQueueScreen(uiState = uiState, viewModel = viewModel)
                AppScreen.SETTINGS -> SettingsScreen(uiState = uiState, viewModel = viewModel)
            }
        }
    }
}
