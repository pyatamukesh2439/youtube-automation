package com.example.data.model

data class ViralTrend(
    val id: String,
    val topic: String,
    val niche: String,
    val viralScore: Int, // 1 - 100
    val searchGrowth: String, // e.g. "+380% 24h"
    val competition: String, // "Low", "Medium", "High"
    val estimatedRpm: Double, // in USD, e.g. 18.50
    val recommendedFormat: String, // "Shorts (9:16)" or "Long-form (16:9)"
    val hookIdea: String,
    val viralTags: List<String>,
    val targetAudience: String
)
