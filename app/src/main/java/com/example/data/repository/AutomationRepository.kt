package com.example.data.repository

import com.example.data.model.ProjectStatus
import com.example.data.model.VideoProject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AutomationRepository {

    private val _projects = MutableStateFlow<List<VideoProject>>(emptyList())
    val projects: StateFlow<List<VideoProject>> = _projects.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    private val _generationStep = MutableStateFlow("")
    val generationStep: StateFlow<String> = _generationStep.asStateFlow()

    init {
        // Pre-populate with a couple of high-performing ready & scheduled video packages
        val sample1 = GeminiAutomationService.generateSmartFallbackProject(
            topic = "Top 5 AI Tools That Will Replace Programmers in 2026",
            niche = "Tech & AI",
            format = "YouTube Shorts (9:16)",
            tone = "Fast-Paced & Energetic",
            videoEngine = "Google Veo 3.1",
            voiceEngine = "ElevenLabs Turbo v3",
            thumbnailEngine = "Midjourney v6.1"
        ).copy(
            id = "proj-pre-1",
            status = ProjectStatus.SCHEDULED,
            scheduledTime = "Today at 6:30 PM (Peak Viral Window)"
        )

        val sample2 = GeminiAutomationService.generateSmartFallbackProject(
            topic = "The 24-Hour YouTube Automation Cash Flow Blueprint",
            niche = "Finance & Wealth",
            format = "Long-form (16:9)",
            tone = "Authoritative & Documentary",
            videoEngine = "Runway Gen-3 Alpha",
            voiceEngine = "WellSaid Studio",
            thumbnailEngine = "FLUX.1 Pro"
        ).copy(
            id = "proj-pre-2",
            status = ProjectStatus.READY_TO_PUBLISH,
            scheduledTime = "Tomorrow at 11:00 AM"
        )

        val sample3 = GeminiAutomationService.generateSmartFallbackProject(
            topic = "Secret High-Yield Savings Accounts Paying 9.2% APY",
            niche = "Personal Finance",
            format = "Long-form (16:9)",
            tone = "Urgent & Educational",
            videoEngine = "Kling AI 1.5",
            voiceEngine = "ElevenLabs Turbo v3",
            thumbnailEngine = "Midjourney v6.1"
        ).copy(
            id = "proj-pre-3",
            status = ProjectStatus.UPLOADED,
            scheduledTime = "Uploaded Yesterday (54K Views • $1,040 Revenue)"
        )

        _projects.value = listOf(sample1, sample2, sample3)
    }

    fun addProject(project: VideoProject) {
        _projects.value = listOf(project) + _projects.value
    }

    fun updateProjectStatus(projectId: String, newStatus: ProjectStatus) {
        _projects.value = _projects.value.map {
            if (it.id == projectId) it.copy(status = newStatus) else it
        }
    }

    fun deleteProject(projectId: String) {
        _projects.value = _projects.value.filter { it.id != projectId }
    }

    fun setGenerating(generating: Boolean, step: String = "") {
        _isGenerating.value = generating
        _generationStep.value = step
    }
}
