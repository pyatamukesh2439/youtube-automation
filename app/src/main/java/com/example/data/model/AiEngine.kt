package com.example.data.model

enum class AiCategory(val displayName: String, val icon: String) {
    VIDEO_GEN("Video Generation", "🎬"),
    THUMBNAIL_ART("Thumbnail & Vision", "🖼️"),
    VOICE_AUDIO("Voice & Audio", "🎙️"),
    SCRIPT_HOOKS("Script & Hooks", "✍️"),
    SEO_VIRAL("SEO & Viral Trends", "📈")
}

data class AiEngine(
    val id: String,
    val name: String,
    val provider: String,
    val category: AiCategory,
    val version: String,
    val description: String,
    val features: List<String>,
    val speed: String, // e.g. "Fast (10s)", "Ultra (2s)"
    val qualityRating: Float, // 1.0 - 5.0
    val pricingType: String, // "Free Tier", "Freemium", "API Paid"
    val samplePrompt: String,
    val badge: String = "ACTIVE"
)
