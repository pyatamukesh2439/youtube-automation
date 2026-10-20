package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.RetentionPoint
import com.example.ui.theme.*

@Composable
fun MetricKpiCard(
    title: String,
    value: String,
    changeText: String,
    isPositive: Boolean,
    accentColor: Color,
    modifier: Modifier = Modifier,
    icon: String? = null
) {
    Box(
        modifier = modifier
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
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium
                )
                if (icon != null) {
                    Text(text = icon, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = if (isPositive) "▲" else "▼",
                    fontSize = 10.sp,
                    color = if (isPositive) ViralGreen else Color(0xFFFF5252)
                )
                Text(
                    text = changeText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isPositive) ViralGreen else Color(0xFFFF5252)
                )
            }
        }
    }
}

@Composable
fun RetentionCurveChart(
    points: List<RetentionPoint>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(StudioSurface)
            .border(1.dp, StudioCardBorder, RoundedCornerShape(14.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Viewer Retention Curve Diagnostic",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Real second-by-second drop-off points & viral hook zone",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(ViralGreen.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "89% Hook",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ViralGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Canvas Line Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val padding = 20f

                    // Grid lines
                    val steps = 4
                    for (i in 0..steps) {
                        val y = h - (h / steps) * i
                        drawLine(
                            color = StudioCardBorder.copy(alpha = 0.6f),
                            start = Offset(0f, y),
                            end = Offset(w, y),
                            strokeWidth = 1f
                        )
                    }

                    if (points.isNotEmpty()) {
                        val path = Path()
                        val maxTime = (points.lastOrNull()?.timestampSeconds ?: 100).coerceAtLeast(1)

                        points.forEachIndexed { index, pt ->
                            val x = (pt.timestampSeconds.toFloat() / maxTime) * (w - padding * 2) + padding
                            val y = h - (pt.retentionPercentage / 100f) * (h - padding)

                            if (index == 0) {
                                path.moveTo(x, y)
                            } else {
                                path.lineTo(x, y)
                            }

                            // Draw point circle
                            drawCircle(
                                color = if (pt.timestampSeconds <= 3) YouTubeRed else AiCyan,
                                radius = 4.dp.toPx(),
                                center = Offset(x, y)
                            )
                        }

                        drawPath(
                            path = path,
                            color = YouTubeRed,
                            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Key Retention Milestones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("0:03 Hook Window", fontSize = 10.sp, color = TextMuted)
                    Text("89% Kept", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = YouTubeRed)
                }
                Column {
                    Text("0:30 Midway", fontSize = 10.sp, color = TextMuted)
                    Text("74% Kept", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AiCyan)
                }
                Column {
                    Text("End Screen CTA", fontSize = 10.sp, color = TextMuted)
                    Text("51% Finished", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldRpm)
                }
            }
        }
    }
}
