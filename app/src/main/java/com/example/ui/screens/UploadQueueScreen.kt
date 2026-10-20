package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ProjectStatus
import com.example.data.model.VideoProject
import com.example.ui.AutomationUiState
import com.example.ui.MainViewModel
import com.example.ui.theme.*

@Composable
fun UploadQueueScreen(
    uiState: AutomationUiState,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val projectList by viewModel.projects.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp)
    ) {
        // Channel Connection Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(StudioSurface)
                    .border(1.dp, StudioCardBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
                                text = "YouTube Data API v3 Active",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ViralGreen
                            )
                        }

                        Text(
                            text = "Channel: ${uiState.youtubeChannelName}",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }

                    Text(
                        text = "Automated Uploads & Scheduler Hub",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    Text(
                        text = "Videos render automatically with 50+ AI models and queue for peak-hour YouTube publishing with full monetization metadata and policy tags.",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        lineHeight = 15.sp
                    )

                    // Auto-Publish Toggle
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(StudioSurfaceVariant)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Auto-Publish on Schedule", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("Uploads automatically during viewer peak hours", fontSize = 10.sp, color = TextMuted)
                        }

                        Switch(
                            checked = uiState.autoPublishEnabled,
                            onCheckedChange = { viewModel.toggleAutoPublish(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = YouTubeRed,
                                uncheckedThumbColor = TextMuted,
                                uncheckedTrackColor = StudioElevated
                            )
                        )
                    }
                }
            }
        }

        // Section header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ACTIVE AUTOMATION QUEUE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "${projectList.size} Packages",
                    fontSize = 11.sp,
                    color = AiCyan,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Queue Items
        items(projectList, key = { it.id }) { proj ->
            QueueVideoItemCard(
                project = proj,
                onPublishNow = { viewModel.publishImmediatelyToYouTube(proj) }
            )
        }
    }
}

@Composable
fun QueueVideoItemCard(
    project: VideoProject,
    onPublishNow: () -> Unit,
    modifier: Modifier = Modifier
) {
    val statusColor = when (project.status) {
        ProjectStatus.UPLOADED -> ViralGreen
        ProjectStatus.SCHEDULED -> GoldRpm
        ProjectStatus.READY_TO_PUBLISH -> YouTubeRedLight
        ProjectStatus.GENERATING -> AiCyan
        ProjectStatus.DRAFT -> TextMuted
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(StudioSurface)
            .border(1.dp, StudioCardBorder, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // Status row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(statusColor.copy(alpha = 0.2f))
                        .border(1.dp, statusColor.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = project.status.label.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = statusColor
                    )
                }

                Text(
                    text = project.format,
                    fontSize = 10.sp,
                    color = TextSecondary
                )
            }

            // Title
            Text(
                text = project.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            // Details
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(StudioSurfaceVariant)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🕒 ${project.scheduledTime}",
                    fontSize = 10.sp,
                    color = TextSecondary
                )

                Text(
                    text = "💵 Est. RPM: $${project.estimatedRpm}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldRpm
                )
            }

            // Policy and monetization indicators
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("✓ Monetized (Mid-rolls)", fontSize = 9.sp, color = ViralGreen)
                Text("✓ AI Content Disclosed", fontSize = 9.sp, color = AiCyan)
                Text("✓ Not Made for Kids", fontSize = 9.sp, color = TextSecondary)
            }

            // Publish Button if not yet uploaded
            if (project.status != ProjectStatus.UPLOADED) {
                Button(
                    onClick = onPublishNow,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .testTag("publish_now_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = YouTubeRed),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.Upload, contentDescription = "Upload", tint = Color.White, modifier = Modifier.size(16.dp))
                        Text("Publish to YouTube Immediately", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}
