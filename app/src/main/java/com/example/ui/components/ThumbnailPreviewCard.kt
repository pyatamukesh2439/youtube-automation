package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ThumbnailDesign
import com.example.ui.theme.*

@Composable
fun ThumbnailPreviewCard(
    thumbnail: ThumbnailDesign,
    format: String,
    modifier: Modifier = Modifier
) {
    val isShorts = format.contains("Shorts")
    val aspectRatio = if (isShorts) 9f / 14f else 16f / 9f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(StudioSurface)
            .border(1.dp, StudioCardBorder, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Thumbnail",
                        tint = GoldRpm,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "AI Thumbkine & CTR Vision",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                // Predicted CTR badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(ViralGreen.copy(alpha = 0.2f))
                        .border(1.dp, ViralGreen.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "CTR",
                            tint = ViralGreen,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "${thumbnail.predictedCtr}% Predicted CTR",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ViralGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Simulated High-CTR Thumbnail Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF2E1015),
                                Color(0xFF14070A),
                                Color(0xFF0A0A0F)
                            )
                        )
                    )
                    .border(1.dp, YouTubeRed.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                // Background artistic glowing frame
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .align(Alignment.CenterEnd)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    YouTubeRed.copy(alpha = 0.35f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                // High-contrast Headline Text Overlay
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(YouTubeRed)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = thumbnail.headlineText,
                            fontSize = if (isShorts) 18.sp else 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            textAlign = TextAlign.Start
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Black.copy(alpha = 0.75f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "A/B Alt: \"${thumbnail.abTestAlternative}\"",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = GoldRpm
                        )
                    }
                }

                // Style tag top-right
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Black.copy(alpha = 0.8f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = thumbnail.expressionStyle,
                        fontSize = 10.sp,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // AI Prompt detail
            Text(
                text = "Midjourney / FLUX Prompt:",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = thumbnail.prompt,
                fontSize = 11.sp,
                color = TextMuted,
                lineHeight = 15.sp
            )
        }
    }
}
