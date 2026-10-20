package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.repository.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AppScreen(val title: String, val icon: String) {
    STUDIO("AI Studio", "⚡"),
    AI_DIRECTORY("50+ AI Engines", "🤖"),
    TRENDS("Viral Radar", "🔥"),
    MONETIZATION("Monetization", "💰"),
    QUEUE("Auto-Uploads", "🚀"),
    SETTINGS("Settings", "⚙️")
}

data class AutomationUiState(
    val currentScreen: AppScreen = AppScreen.STUDIO,
    val topicInput: String = "Top 5 AI Tools That Will Replace Programmers in 2026",
    val selectedNiche: String = "Tech & AI",
    val selectedFormat: String = "YouTube Shorts (9:16)",
    val selectedTone: String = "Fast-Paced & Energetic",
    val selectedVideoEngine: String = "Google Veo 3.1",
    val selectedVoiceEngine: String = "ElevenLabs Turbo v3",
    val selectedThumbnailEngine: String = "Midjourney v6.1",
    val selectedScriptEngine: String = "Gemini 3.5 Flash",
    val isPipelineRunning: Boolean = false,
    val pipelineStep: String = "",
    val pipelineProgress: Float = 0f,
    val generatedProject: VideoProject? = null,
    val activeEngineCategory: AiCategory = AiCategory.VIDEO_GEN,
    val searchEngineQuery: String = "",
    val monthlyViewsInput: Long = 2500000L,
    val calculatorNicheRpm: Double = 16.80,
    val youtubeChannelName: String = "TechAutomator Pro",
    val youtubeChannelConnected: Boolean = true,
    val autoPublishEnabled: Boolean = true,
    val aiDisclosureEnabled: Boolean = true,
    val toastMessage: String? = null
)

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AutomationUiState())
    val uiState: StateFlow<AutomationUiState> = _uiState.asStateFlow()

    val projects = AutomationRepository.projects

    init {
        // Default with first pre-populated project to show rich preview immediately
        _uiState.value = _uiState.value.copy(
            generatedProject = AutomationRepository.projects.value.firstOrNull()
        )
    }

    fun navigateTo(screen: AppScreen) {
        _uiState.value = _uiState.value.copy(currentScreen = screen)
    }

    fun updateTopic(newTopic: String) {
        _uiState.value = _uiState.value.copy(topicInput = newTopic)
    }

    fun updateNiche(newNiche: String) {
        _uiState.value = _uiState.value.copy(selectedNiche = newNiche)
    }

    fun updateFormat(newFormat: String) {
        _uiState.value = _uiState.value.copy(selectedFormat = newFormat)
    }

    fun updateTone(newTone: String) {
        _uiState.value = _uiState.value.copy(selectedTone = newTone)
    }

    fun selectVideoEngine(engineName: String) {
        _uiState.value = _uiState.value.copy(selectedVideoEngine = engineName)
    }

    fun selectVoiceEngine(engineName: String) {
        _uiState.value = _uiState.value.copy(selectedVoiceEngine = engineName)
    }

    fun selectThumbnailEngine(engineName: String) {
        _uiState.value = _uiState.value.copy(selectedThumbnailEngine = engineName)
    }

    fun selectScriptEngine(engineName: String) {
        _uiState.value = _uiState.value.copy(selectedScriptEngine = engineName)
    }

    fun selectEngineCategory(category: AiCategory) {
        _uiState.value = _uiState.value.copy(activeEngineCategory = category)
    }

    fun updateSearchEngineQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchEngineQuery = query)
    }

    fun updateMonthlyViews(views: Long) {
        _uiState.value = _uiState.value.copy(monthlyViewsInput = views)
    }

    fun updateCalculatorRpm(rpm: Double) {
        _uiState.value = _uiState.value.copy(calculatorNicheRpm = rpm)
    }

    fun loadTrendIntoStudio(trend: ViralTrend) {
        _uiState.value = _uiState.value.copy(
            topicInput = trend.topic,
            selectedNiche = trend.niche,
            selectedFormat = trend.recommendedFormat,
            currentScreen = AppScreen.STUDIO,
            toastMessage = "Loaded '${trend.topic}' into Studio!"
        )
    }

    fun runAutomationPipeline() {
        if (_uiState.value.isPipelineRunning) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isPipelineRunning = true,
                pipelineProgress = 0.05f,
                pipelineStep = "1/6 Connecting to ${_uiState.value.selectedScriptEngine}..."
            )

            delay(600)
            _uiState.value = _uiState.value.copy(
                pipelineProgress = 0.25f,
                pipelineStep = "2/6 Synthesizing Viral Retention Hook & Script..."
            )

            delay(700)
            _uiState.value = _uiState.value.copy(
                pipelineProgress = 0.45f,
                pipelineStep = "3/6 Generating Voiceover with ${_uiState.value.selectedVoiceEngine}..."
            )

            delay(800)
            _uiState.value = _uiState.value.copy(
                pipelineProgress = 0.65f,
                pipelineStep = "4/6 Rendering Scene Storyboard with ${_uiState.value.selectedVideoEngine}..."
            )

            delay(750)
            _uiState.value = _uiState.value.copy(
                pipelineProgress = 0.85f,
                pipelineStep = "5/6 Generating High-CTR Thumbnail with ${_uiState.value.selectedThumbnailEngine}..."
            )

            delay(650)
            _uiState.value = _uiState.value.copy(
                pipelineProgress = 0.95f,
                pipelineStep = "6/6 Packaging VidIQ SEO, Viral Tags & Timestamps..."
            )

            val newProject = GeminiAutomationService.generateCompleteAutomationPackage(
                topic = _uiState.value.topicInput,
                niche = _uiState.value.selectedNiche,
                format = _uiState.value.selectedFormat,
                tone = _uiState.value.selectedTone,
                selectedAiVideoEngine = _uiState.value.selectedVideoEngine,
                selectedVoiceEngine = _uiState.value.selectedVoiceEngine,
                selectedThumbnailEngine = _uiState.value.selectedThumbnailEngine
            )

            AutomationRepository.addProject(newProject)

            delay(300)
            _uiState.value = _uiState.value.copy(
                isPipelineRunning = false,
                pipelineProgress = 1.0f,
                pipelineStep = "Complete! Video Package Ready for YouTube",
                generatedProject = newProject,
                toastMessage = "Video automation generated successfully!"
            )
        }
    }

    fun scheduleProjectToYouTube(project: VideoProject) {
        AutomationRepository.updateProjectStatus(project.id, ProjectStatus.SCHEDULED)
        _uiState.value = _uiState.value.copy(
            toastMessage = "Scheduled '${project.title.take(28)}...' for YouTube Auto-Upload!"
        )
    }

    fun publishImmediatelyToYouTube(project: VideoProject) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(toastMessage = "Connecting to YouTube Data API v3...")
            delay(1200)
            AutomationRepository.updateProjectStatus(project.id, ProjectStatus.UPLOADED)
            _uiState.value = _uiState.value.copy(toastMessage = "Uploaded & Monetized on YouTube!")
        }
    }

    fun clearToast() {
        _uiState.value = _uiState.value.copy(toastMessage = null)
    }

    fun toggleAutoPublish(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(autoPublishEnabled = enabled)
    }

    fun toggleAiDisclosure(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(aiDisclosureEnabled = enabled)
    }
}
