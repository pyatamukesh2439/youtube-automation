package com.example.data.repository

import com.example.data.model.*

object AnalyticsRepository {

    val sampleAnalytics = ChannelAnalytics(
        totalSubscribers = 148520,
        subscriberGrowth28d = 12430,
        totalViews28d = 3450200L,
        viewsGrowthPercent = 48.6,
        watchTimeHours28d = 164200.0,
        averageCtr = 9.4,
        averageViewDurationSeconds = 178, // 2m 58s overall (Shorts + Long)
        estimatedRevenue28d = 6842.80,
        revenueGrowthPercent = 54.2,
        adsenseRevenue = 4380.00,
        sponsorshipRevenue = 1500.00,
        affiliateRevenue = 740.80,
        membershipRevenue = 222.00,
        retentionCurve = listOf(
            RetentionPoint(0, 100f, "Video Start"),
            RetentionPoint(3, 89f, "Hook Window (Viral)"),
            RetentionPoint(10, 81f, "Intro Bridge"),
            RetentionPoint(25, 74f, "Value Delivery 1"),
            RetentionPoint(45, 68f, "Pattern Interrupt"),
            RetentionPoint(60, 62f, "High Retention"),
            RetentionPoint(80, 56f, "Sponsor Bridge"),
            RetentionPoint(100, 51f, "End Screen CTA")
        ),
        topVideos = listOf(
            TopVideoStat(
                id = "vid-1",
                title = "I Replaced My Job With 50 AI Models in 2026",
                views = "1.2M",
                ctr = 11.8,
                revenue = 2840.50,
                rpm = 23.67,
                format = "16:9 Long-form"
            ),
            TopVideoStat(
                id = "vid-2",
                title = "3 Dark Psychology Tricks Used by Billionaires #shorts",
                views = "890K",
                ctr = 14.2,
                revenue = 312.40,
                rpm = 0.35,
                format = "9:16 Shorts"
            ),
            TopVideoStat(
                id = "vid-3",
                title = "Stop Using ChatGPT: Top 5 Free AI That Shocked Everyone",
                views = "540K",
                ctr = 10.4,
                revenue = 1180.20,
                rpm = 15.20,
                format = "16:9 Long-form"
            ),
            TopVideoStat(
                id = "vid-4",
                title = "The 9.2% Savings Loophole You Didn't Know Existed",
                views = "320K",
                ctr = 9.8,
                revenue = 1040.00,
                rpm = 32.50,
                format = "16:9 Long-form"
            ),
            TopVideoStat(
                id = "vid-5",
                title = "Cyber Heist: How $800M Vanished in 14 Seconds",
                views = "280K",
                ctr = 8.7,
                revenue = 680.00,
                rpm = 14.80,
                format = "16:9 Long-form"
            )
        ),
        nicheBenchmarks = listOf(
            NicheRpmBenchmark("Personal Finance & Crypto", 28.50, "$35 - $75", "High Value", "💰"),
            NicheRpmBenchmark("SaaS, Tech & AI Automation", 16.80, "$20 - $45", "High Value", "🤖"),
            NicheRpmBenchmark("Real Estate & Investing", 22.00, "$25 - $50", "High Value", "🏢"),
            NicheRpmBenchmark("Health, Fitness & Longevity", 11.20, "$15 - $30", "Medium", "💪"),
            NicheRpmBenchmark("True Crime & Documentaries", 9.40, "$12 - $24", "High Views", "🔍"),
            NicheRpmBenchmark("Motivation & Self-Growth", 6.80, "$8 - $18", "Very High Views", "🔥"),
            NicheRpmBenchmark("Gaming & Esports", 4.20, "$5 - $12", "Hyper High Volume", "🎮"),
            NicheRpmBenchmark("YouTube Shorts (All Niches)", 0.22, "$0.10 - $0.45", "Max Viral Speed", "⚡")
        )
    )

    fun calculateEstimatedEarnings(monthlyViews: Long, rpmRate: Double): Double {
        return (monthlyViews / 1000.0) * rpmRate
    }
}
