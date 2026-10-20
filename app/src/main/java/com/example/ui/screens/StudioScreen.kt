package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ProjectStatus
import com.example.data.model.VideoProject
import com.example.ui.AutomationUiState
import com.example.ui.MainViewModel
import com.example.ui.components.ThumbnailPreviewCard
import com.example.ui.theme.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudioScreen(
    uiState: AutomationUiState,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val niches = listOf("Tech & AI", "Finance & Wealth", "Personal Finance", "Science & Future", "Shorts / Motivation", "Gaming", "True Crime & Mystery")
    val formats = listOf("YouTube Shorts (9:16)", "Long-form (16:9)")
    val tones = listOf("Fast-Paced & Energetic", "Authoritative & Documentary", "Curious & Mysterious", "Urgent & Educational")

    val videoAiModels = listOf("Google Veo 3.1", "Runway Gen-3 Alpha", "OpenAI Sora 2.0", "Pika 2.0", "Kling AI 1.5", "Luma Dream Machine")
    val voiceAiModels = listOf("ElevenLabs Turbo v3", "Gemini Flash TTS", "Suno v3.5", "WellSaid Studio", "Murf Studio Pro")
    val thumbnailAiModels = listOf("Midjourney v6.1", "FLUX.1 Pro", "Ideogram 2.0", "DALL-E 3 HD", "Recraft v3")

    var showAdvancedAiSettings by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp)
    ) {
        // Hero Header & AI Pipeline Engine Status
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                StudioElevated,
                                StudioSurface
                            )
                        )
                    )
                    .border(1.dp, StudioCardBorder, RoundedCornerShape(18.dp))
                    .padding(16.dp)
            ) {
                Column {
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
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(ViralGreen)
                            )
                            Text(
                                text = "50+ MULTI-AI ENGINE CONNECTED",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = ViralGreen
                            )
                        }

                        Text(
                            text = "Auto-Pilot Mode",
                            fontSize = 11.sp,
                            color = GoldRpm,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Automated YouTube Video Studio",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Generate viral scripts, voiceovers, video scenes, high-CTR thumbkines, and VidIQ-optimized SEO in one tap.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Topic Input Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(StudioSurface)
                    .border(1.dp, StudioCardBorder, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Video Topic or Viral Idea",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    OutlinedTextField(
                        value = uiState.topicInput,
                        onValueChange = { viewModel.updateTopic(it) },
                        placeholder = { Text("e.g. Top 5 AI Tools That Will Replace Programmers in 2026", color = TextMuted) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("topic_input_field"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = YouTubeRed,
                            unfocusedBorderColor = StudioCardBorder,
                            focusedContainerColor = StudioSurfaceVariant,
                            unfocusedContainerColor = StudioSurfaceVariant,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 3
                    )

                    // Format & Niche Chips
                    Text(
                        text = "Target Format",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        formats.forEach { fmt ->
                            val isSelected = uiState.selectedFormat == fmt
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) YouTubeRed.copy(alpha = 0.2f) else StudioSurfaceVariant)
                                    .border(
                                        1.dp,
                                        if (isSelected) YouTubeRed else StudioCardBorder,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { viewModel.updateFormat(fmt) }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = if (fmt.contains("Shorts")) "📱" else "🖥️",
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = fmt,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) Color.White else TextSecondary
                                    )
                                }
                            }
                        }
                    }

                    // Niche selector
                    Text(
                        text = "Channel Niche (High RPM Recommended)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextSecondary
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(niches) { n ->
                            val isSelected = uiState.selectedNiche == n
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isSelected) GoldRpm.copy(alpha = 0.18f) else StudioSurfaceVariant)
                                    .border(
                                        1.dp,
                                        if (isSelected) GoldRpm else StudioCardBorder,
                                        RoundedCornerShape(20.dp)
                                    )
                                    .clickable { viewModel.updateNiche(n) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = n,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) GoldRpm else TextSecondary
                                )
                            }
                        }
                    }

                    // Toggle AI Engine Selection
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showAdvancedAiSettings = !showAdvancedAiSettings }
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "AI Engines",
                                tint = AiCyan,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Select from 50+ AI Engines (${uiState.selectedVideoEngine} + ${uiState.selectedVoiceEngine})",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AiCyan
                            )
                        }

                        Icon(
                            imageVector = if (showAdvancedAiSettings) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Expand",
                            tint = AiCyan,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    AnimatedVisibility(visible = showAdvancedAiSettings) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(StudioSurfaceVariant)
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Video Engine
                            Text("AI Video Generator:", fontSize = 11.sp, color = TextMuted)
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                videoAiModels.forEach { m ->
                                    val isSelected = uiState.selectedVideoEngine == m
                                    AssistChip(
                                        onClick = { viewModel.selectVideoEngine(m) },
                                        label = { Text(m, fontSize = 11.sp, color = if (isSelected) Color.White else TextSecondary) },
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = if (isSelected) YouTubeRed else StudioElevated
                                        )
                                    )
                                }
                            }

                            // Voice Engine
                            Text("AI Voice & Speech:", fontSize = 11.sp, color = TextMuted)
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                voiceAiModels.forEach { v ->
                                    val isSelected = uiState.selectedVoiceEngine == v
                                    AssistChip(
                                        onClick = { viewModel.selectVoiceEngine(v) },
                                        label = { Text(v, fontSize = 11.sp, color = if (isSelected) Color.White else TextSecondary) },
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = if (isSelected) GoldRpmDark else StudioElevated
                                        )
                                    )
                                }
                            }

                            // Thumbnail Engine
                            Text("AI Thumbnail Creator (Thumbkine):", fontSize = 11.sp, color = TextMuted)
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                thumbnailAiModels.forEach { t ->
                                    val isSelected = uiState.selectedThumbnailEngine == t
                                    AssistChip(
                                        onClick = { viewModel.selectThumbnailEngine(t) },
                                        label = { Text(t, fontSize = 11.sp, color = if (isSelected) Color.White else TextSecondary) },
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = if (isSelected) AiIndigo else StudioElevated
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Big Action Button
                    Button(
                        onClick = { viewModel.runAutomationPipeline() },
                        enabled = !uiState.isPipelineRunning && uiState.topicInput.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("run_pipeline_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = YouTubeRed,
                            disabledContainerColor = YouTubeRed.copy(alpha = 0.5f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (uiState.isPipelineRunning) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Running AI Engines...",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = "Run",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "RUN 50+ AI AUTOMATION PIPELINE",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // Live Pipeline Progress Tracker (when running)
        if (uiState.isPipelineRunning) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(StudioSurface)
                        .border(1.dp, YouTubeRed.copy(alpha = 0.6f), RoundedCornerShape(14.dp))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Multi-Model Orchestrator Active",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "${(uiState.pipelineProgress * 100).toInt()}%",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = YouTubeRed
                            )
                        }

                        LinearProgressIndicator(
                            progress = { uiState.pipelineProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = YouTubeRed,
                            trackColor = StudioSurfaceVariant
                        )

                        Text(
                            text = uiState.pipelineStep,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = AiCyan
                        )
                    }
                }
            }
        }

        // Generated Project Details (When available)
        val project = uiState.generatedProject
        if (project != null) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GENERATED VIDEO PACKAGE",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = TextSecondary,
                        letterSpacing = 1.sp
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(ViralGreen.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "Estimated RPM: $${project.estimatedRpm}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ViralGreen
                        )
                    }
                }
            }

            // High-CTR Titles & Alternatives
            item {
                Box(
                    modifier = Modifier
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
                            Text(
                                text = "🏆 Tested High-CTR Title",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldRpm
                            )

                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(project.title))
                                    Toast.makeText(context, "Title copied!", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = TextSecondary, modifier = Modifier.size(16.dp))
                            }
                        }

                        Text(
                            text = project.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )

                        Text(
                            text = "Alternative A/B Titles (VidIQ Ranked):",
                            fontSize = 11.sp,
                            color = TextMuted
                        )

                        project.alternativeTitles.forEachIndexed { idx, alt ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(StudioSurfaceVariant)
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${idx + 1}. $alt",
                                    fontSize = 11.sp,
                                    color = TextSecondary,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "Rank #${idx + 2}",
                                    fontSize = 10.sp,
                                    color = GoldRpm,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Thumbnail / Thumbkine Preview Card
            item {
                ThumbnailPreviewCard(
                    thumbnail = project.thumbnail,
                    format = project.format
                )
            }

            // 3-Second Retention Hook
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFF280B10),
                                    StudioSurface
                                )
                            )
                        )
                        .border(1.dp, YouTubeRed.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("⚡", fontSize = 14.sp)
                            Text(
                                text = "3-Second Retention Hook (Prevents Swipe-Away)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = YouTubeRedLight
                            )
                        }

                        Text(
                            text = "\"${project.hook}\"",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Scene-by-Scene AI Storyboard
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(StudioSurface)
                        .border(1.dp, StudioCardBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🎬 ${project.selectedAiVideoEngine} Scene Storyboard",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "${project.scenes.size} AI Scenes",
                                fontSize = 11.sp,
                                color = AiCyan,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        project.scenes.forEach { scene ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(StudioSurfaceVariant)
                                    .padding(10.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Scene #${scene.sceneNumber} (${scene.durationSeconds}s) • ${scene.cameraAngle}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = AiCyan
                                        )
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(StudioElevated)
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(
                                                text = scene.bRollTag,
                                                fontSize = 9.sp,
                                                color = TextSecondary
                                            )
                                        }
                                    }

                                    Text(
                                        text = "Visual: ${scene.visualPrompt}",
                                        fontSize = 11.sp,
                                        color = TextPrimary
                                    )

                                    Text(
                                        text = "Voiceover: \"${scene.voiceoverText}\"",
                                        fontSize = 10.sp,
                                        color = TextMuted
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Full Narration Script
            item {
                Box(
                    modifier = Modifier
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
                            Text(
                                text = "🎙️ Complete Spoken Script (${project.voiceModel})",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )

                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(project.fullScript))
                                    Toast.makeText(context, "Script copied to clipboard!", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = TextSecondary, modifier = Modifier.size(16.dp))
                            }
                        }

                        Text(
                            text = project.fullScript,
                            fontSize = 12.sp,
                            color = TextSecondary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // VidIQ SEO Package (Tags, Description, Hashtags)
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(StudioSurface)
                        .border(1.dp, StudioCardBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "📈 VidIQ / TubeBuddy SEO Package",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(ViralGreen.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("SEO Score 100/100", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ViralGreen)
                            }
                        }

                        Text(
                            text = "Viral Search Tags:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSecondary
                        )

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            project.tags.forEach { tag ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(StudioSurfaceVariant)
                                        .border(1.dp, StudioCardBorder, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(text = "#$tag", fontSize = 10.sp, color = AiCyan)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "YouTube Description & Timestamps:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextSecondary
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(StudioSurfaceVariant)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = project.description,
                                fontSize = 11.sp,
                                color = TextMuted,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }

            // Schedule & Upload Action Bar
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { viewModel.scheduleProjectToYouTube(project) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("schedule_youtube_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = YouTubeRed),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.CloudUpload, contentDescription = "Upload", tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "SCHEDULE & AUTO-UPLOAD TO YOUTUBE",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            val allExport = "TITLE: ${project.title}\n\nHOOK: ${project.hook}\n\nSCRIPT:\n${project.fullScript}\n\nTAGS: ${project.tags.joinToString(", ")}"
                            clipboardManager.setText(AnnotatedString(allExport))
                            Toast.makeText(context, "Complete package copied to clipboard!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StudioCardBorder),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Export", tint = TextSecondary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Export Video Package (JSON/Text)", fontSize = 12.sp, color = TextSecondary)
                    }
                }
            }
        }
    }
}
