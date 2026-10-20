package com.example.data.model

enum class ProjectStatus(val label: String) {
    DRAFT("Draft"),
    GENERATING("Generating with 50+ AI"),
    READY_TO_PUBLISH("Ready to Upload"),
    SCHEDULED("Scheduled"),
    UPLOADED("Published on YouTube")
}

data class VideoScene(
    val sceneNumber: Int,
    val durationSeconds: Int,
    val visualPrompt: String,
    val voiceoverText: String,
    val cameraAngle: String,
    val bRollTag: String
)

data class ThumbnailDesign(
    val prompt: String,
    val headlineText: String,
    val expressionStyle: String,
    val colorAccentHex: String,
    val predictedCtr: Double, // e.g. 11.4%
    val abTestAlternative: String
)

data class VideoProject(
    val id: String,
    val title: String,
    val alternativeTitles: List<String>,
    val niche: String,
    val format: String, // "YouTube Shorts" or "Long-form 16:9"
    val hook: String,
    val fullScript: String,
    val scenes: List<VideoScene>,
    val voiceModel: String,
    val selectedAiVideoEngine: String,
    val thumbnail: ThumbnailDesign,
    val description: String,
    val tags: List<String>,
    val hashtags: List<String>,
    val estimatedRpm: Double,
    val status: ProjectStatus,
    val scheduledTime: String,
    val targetChannel: String = "Viral Automator [Main]",
    val monetizationEnabled: Boolean = true,
    val madeForKids: Boolean = false,
    val aiDisclosureChecked: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
