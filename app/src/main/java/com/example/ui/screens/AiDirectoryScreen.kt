package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AiCategory
import com.example.data.model.AiEngine
import com.example.data.repository.AiDirectoryRepository
import com.example.ui.AppScreen
import com.example.ui.AutomationUiState
import com.example.ui.MainViewModel
import com.example.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AiDirectoryScreen(
    uiState: AutomationUiState,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val filteredEngines = remember(uiState.activeEngineCategory, uiState.searchEngineQuery) {
        val base = if (uiState.searchEngineQuery.isBlank()) {
            AiDirectoryRepository.getEnginesByCategory(uiState.activeEngineCategory)
        } else {
            AiDirectoryRepository.searchEngines(uiState.searchEngineQuery)
        }
        base
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp)
    ) {
        // Search & Category Header
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "50+ AI Engines Directory",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                        Text(
                            text = "52 integrated models for video, voice, scripts & thumbnails",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(StudioElevated)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${filteredEngines.size} Models",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = AiCyan
                        )
                    }
                }

                // Search field
                OutlinedTextField(
                    value = uiState.searchEngineQuery,
                    onValueChange = { viewModel.updateSearchEngineQuery(it) },
                    placeholder = { Text("Search 50+ AI models by name, feature, or vendor...", color = TextMuted, fontSize = 12.sp) },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = TextSecondary, modifier = Modifier.size(18.dp))
                    },
                    trailingIcon = {
                        if (uiState.searchEngineQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.updateSearchEngineQuery("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextSecondary, modifier = Modifier.size(16.dp))
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("ai_search_field"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = YouTubeRed,
                        unfocusedBorderColor = StudioCardBorder,
                        focusedContainerColor = StudioSurface,
                        unfocusedContainerColor = StudioSurface,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                // Category Tabs
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(AiCategory.values()) { cat ->
                        val isSelected = uiState.activeEngineCategory == cat && uiState.searchEngineQuery.isBlank()
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) YouTubeRed else StudioSurface)
                                .border(
                                    1.dp,
                                    if (isSelected) YouTubeRed else StudioCardBorder,
                                    RoundedCornerShape(16.dp)
                                )
                                .clickable {
                                    viewModel.updateSearchEngineQuery("")
                                    viewModel.selectEngineCategory(cat)
                                }
                                .padding(horizontal = 12.dp, vertical = 7.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(text = cat.icon, fontSize = 12.sp)
                                Text(
                                    text = cat.displayName,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else TextSecondary
                                )
                            }
                        }
                    }
                }
            }
        }

        // List of AI Engine Cards
        items(filteredEngines, key = { it.id }) { engine ->
            AiEngineItemCard(
                engine = engine,
                onSelectForPipeline = {
                    when (engine.category) {
                        AiCategory.VIDEO_GEN -> viewModel.selectVideoEngine(engine.name)
                        AiCategory.VOICE_AUDIO -> viewModel.selectVoiceEngine(engine.name)
                        AiCategory.THUMBNAIL_ART -> viewModel.selectThumbnailEngine(engine.name)
                        AiCategory.SCRIPT_HOOKS -> viewModel.selectScriptEngine(engine.name)
                        AiCategory.SEO_VIRAL -> { /* SEO engine selection */ }
                    }
                    viewModel.navigateTo(AppScreen.STUDIO)
                    Toast.makeText(context, "Selected ${engine.name} for Studio Pipeline!", Toast.LENGTH_SHORT).show()
                },
                onCopyPrompt = {
                    clipboardManager.setText(AnnotatedString(engine.samplePrompt))
                    Toast.makeText(context, "Sample prompt copied!", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AiEngineItemCard(
    engine: AiEngine,
    onSelectForPipeline: () -> Unit,
    onCopyPrompt: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(StudioSurface)
            .border(1.dp, StudioCardBorder, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(StudioElevated)
                            .padding(horizontal = 7.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = engine.provider,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                    }

                    Text(
                        text = engine.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                // Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            when (engine.badge) {
                                "RECOMMENDED", "TOP TIER", "HIGHEST CTR" -> YouTubeRed.copy(alpha = 0.2f)
                                "FREE", "FREE TIER" -> ViralGreen.copy(alpha = 0.2f)
                                else -> AiCyan.copy(alpha = 0.2f)
                            }
                        )
                        .border(
                            1.dp,
                            when (engine.badge) {
                                "RECOMMENDED", "TOP TIER", "HIGHEST CTR" -> YouTubeRed
                                "FREE", "FREE TIER" -> ViralGreen
                                else -> AiCyan
                            },
                            RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = engine.badge,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = when (engine.badge) {
                            "RECOMMENDED", "TOP TIER", "HIGHEST CTR" -> YouTubeRedLight
                            "FREE", "FREE TIER" -> ViralGreen
                            else -> AiCyan
                        }
                    )
                }
            }

            Text(
                text = engine.description,
                fontSize = 12.sp,
                color = TextSecondary,
                lineHeight = 16.sp
            )

            // Feature tags
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                engine.features.forEach { f ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(StudioSurfaceVariant)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = "• $f", fontSize = 10.sp, color = TextSecondary)
                    }
                }
            }

            // Specs bar: Speed, Rating, Pricing
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(StudioSurfaceVariant)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "⚡ ${engine.speed}", fontSize = 10.sp, color = GoldRpm, fontWeight = FontWeight.SemiBold)
                Text(text = "⭐ ${engine.qualityRating}/5.0", fontSize = 10.sp, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                Text(text = "💳 ${engine.pricingType}", fontSize = 10.sp, color = TextSecondary)
            }

            // Sample prompt preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(StudioElevated)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "\"${engine.samplePrompt}\"",
                        fontSize = 10.sp,
                        color = TextMuted,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Copy",
                        fontSize = 10.sp,
                        color = AiCyan,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { onCopyPrompt() }
                            .padding(start = 6.dp)
                    )
                }
            }

            // Action
            Button(
                onClick = onSelectForPipeline,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                colors = ButtonDefaults.buttonColors(containerColor = StudioElevated),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(Icons.Default.PlayCircleFilled, contentDescription = "Use", tint = YouTubeRed, modifier = Modifier.size(16.dp))
                    Text(
                        text = "Use ${engine.name.take(18)} in Pipeline",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }
        }
    }
}
