package com.example.ui.screens

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.AnalyticsRepository
import com.example.ui.AutomationUiState
import com.example.ui.MainViewModel
import com.example.ui.components.MetricKpiCard
import com.example.ui.components.RetentionCurveChart
import com.example.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MonetizationScreen(
    uiState: AutomationUiState,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val analytics = AnalyticsRepository.sampleAnalytics
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
    val numberFormat = NumberFormat.getNumberInstance(Locale.US)

    var monthlyViewsSlider by remember { mutableFloatStateOf(uiState.monthlyViewsInput.toFloat()) }
    val currentViews = monthlyViewsSlider.toLong()
    val calculatedEarnings = AnalyticsRepository.calculateEstimatedEarnings(currentViews, uiState.calculatorNicheRpm)
    val calculatedAnnual = calculatedEarnings * 12

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp)
    ) {
        // Monetization Hero Summary
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF281E05),
                                StudioSurface
                            )
                        )
                    )
                    .border(1.dp, GoldRpm.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
                    .padding(18.dp)
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
                            Text("💰", fontSize = 16.sp)
                            Text(
                                text = "ESTIMATED 28-DAY REVENUE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldRpm
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(ViralGreen.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "+${analytics.revenueGrowthPercent}% MoM",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ViralGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = currencyFormat.format(analytics.estimatedRevenue28d),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = TextPrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Automated channels monetized across YouTube AdSense, sponsored integrations & affiliate funnels.",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        lineHeight = 15.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Revenue breakdown pills
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("AdSense (64%)", fontSize = 10.sp, color = TextMuted)
                            Text(currencyFormat.format(analytics.adsenseRevenue), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        }
                        Column {
                            Text("Sponsors (22%)", fontSize = 10.sp, color = TextMuted)
                            Text(currencyFormat.format(analytics.sponsorshipRevenue), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldRpm)
                        }
                        Column {
                            Text("Affiliate (11%)", fontSize = 10.sp, color = TextMuted)
                            Text(currencyFormat.format(analytics.affiliateRevenue), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AiCyan)
                        }
                        Column {
                            Text("Members (3%)", fontSize = 10.sp, color = TextMuted)
                            Text(currencyFormat.format(analytics.membershipRevenue), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = ViralGreen)
                        }
                    }
                }
            }
        }

        // 4 Core Channel Growth KPIs (Free analytics)
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "CHANNEL GROWTH BENCHMARKS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary,
                    letterSpacing = 1.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricKpiCard(
                        title = "Subscribers",
                        value = numberFormat.format(analytics.totalSubscribers),
                        changeText = "+${numberFormat.format(analytics.subscriberGrowth28d)}",
                        isPositive = true,
                        accentColor = YouTubeRed,
                        icon = "👥",
                        modifier = Modifier.weight(1f)
                    )
                    MetricKpiCard(
                        title = "Monthly Views",
                        value = "3.45M",
                        changeText = "+${analytics.viewsGrowthPercent}%",
                        isPositive = true,
                        accentColor = AiCyan,
                        icon = "👁️",
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricKpiCard(
                        title = "Impressions CTR",
                        value = "${analytics.averageCtr}%",
                        changeText = "+2.4% vs Avg",
                        isPositive = true,
                        accentColor = ViralGreen,
                        icon = "🎯",
                        modifier = Modifier.weight(1f)
                    )
                    MetricKpiCard(
                        title = "Avg View Duration",
                        value = "2m 58s",
                        changeText = "+34s longer",
                        isPositive = true,
                        accentColor = GoldRpm,
                        icon = "⏱️",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Interactive Niche RPM / Income Simulator
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(StudioSurface)
                    .border(1.dp, StudioCardBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Niche RPM Revenue Simulator",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Calculate income based on monthly views and niche RPM",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(GoldRpm.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "$${uiState.calculatorNicheRpm} RPM",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldRpm
                            )
                        }
                    }

                    // Select Niche
                    Text("Select Target Niche:", fontSize = 11.sp, color = TextMuted)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(analytics.nicheBenchmarks) { b ->
                            val isSelected = uiState.calculatorNicheRpm == b.averageRpm
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) GoldRpm.copy(alpha = 0.2f) else StudioSurfaceVariant)
                                    .border(
                                        1.dp,
                                        if (isSelected) GoldRpm else StudioCardBorder,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable { viewModel.updateCalculatorRpm(b.averageRpm) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(b.icon, fontSize = 12.sp)
                                    Text(
                                        text = "${b.nicheName.take(16)} ($${b.averageRpm})",
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) GoldRpm else TextSecondary
                                    )
                                }
                            }
                        }
                    }

                    // Views Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Monthly Projected Views:", fontSize = 11.sp, color = TextSecondary)
                        Text(
                            text = "${numberFormat.format(currentViews)} views",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Slider(
                        value = monthlyViewsSlider,
                        onValueChange = {
                            monthlyViewsSlider = it
                            viewModel.updateMonthlyViews(it.toLong())
                        },
                        valueRange = 100000f..10000000f,
                        steps = 99,
                        colors = SliderDefaults.colors(
                            thumbColor = GoldRpm,
                            activeTrackColor = GoldRpm,
                            inactiveTrackColor = StudioCardBorder
                        )
                    )

                    // Output Projected Earnings
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(StudioElevated)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Projected Monthly Income", fontSize = 11.sp, color = TextMuted)
                            Text(
                                text = currencyFormat.format(calculatedEarnings),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = ViralGreen
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text("Annualized Run-Rate", fontSize = 11.sp, color = TextMuted)
                            Text(
                                text = currencyFormat.format(calculatedAnnual),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = GoldRpm
                            )
                        }
                    }
                }
            }
        }

        // Viewer Retention Diagnostic Curve
        item {
            RetentionCurveChart(points = analytics.retentionCurve)
        }

        // Top Performing Automated Videos
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
                        Text(
                            text = "Top Earning Automated Videos",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )

                        Text(
                            text = "Revenue Leaderboard",
                            fontSize = 10.sp,
                            color = TextMuted
                        )
                    }

                    analytics.topVideos.forEach { vid ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(StudioSurfaceVariant)
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = vid.title,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "${vid.views} views • ${vid.ctr}% CTR • $${vid.rpm} RPM",
                                    fontSize = 10.sp,
                                    color = TextMuted
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = currencyFormat.format(vid.revenue),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldRpm
                                )
                                Text(
                                    text = vid.format.take(8),
                                    fontSize = 9.sp,
                                    color = AiCyan
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
