package com.example.data.model

data class RetentionPoint(
    val timestampSeconds: Int,
    val retentionPercentage: Float, // 0 - 100
    val markerLabel: String? = null // e.g. "Hook End", "Re-hook CTA"
)

data class NicheRpmBenchmark(
    val nicheName: String,
    val averageRpm: Double, // in USD
    val cpmRange: String,
    val competition: String,
    val icon: String
)

data class TopVideoStat(
    val id: String,
    val title: String,
    val views: String,
    val ctr: Double,
    val revenue: Double,
    val rpm: Double,
    val format: String
)

data class ChannelAnalytics(
    val totalSubscribers: Int,
    val subscriberGrowth28d: Int,
    val totalViews28d: Long,
    val viewsGrowthPercent: Double,
    val watchTimeHours28d: Double,
    val averageCtr: Double,
    val averageViewDurationSeconds: Int,
    val estimatedRevenue28d: Double,
    val revenueGrowthPercent: Double,
    val adsenseRevenue: Double,
    val sponsorshipRevenue: Double,
    val affiliateRevenue: Double,
    val membershipRevenue: Double,
    val retentionCurve: List<RetentionPoint>,
    val topVideos: List<TopVideoStat>,
    val nicheBenchmarks: List<NicheRpmBenchmark>
)
