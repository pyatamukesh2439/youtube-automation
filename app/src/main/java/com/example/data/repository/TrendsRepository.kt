package com.example.data.repository

import com.example.data.model.ViralTrend

object TrendsRepository {

    val trendingTopics: List<ViralTrend> = listOf(
        ViralTrend(
            id = "trend-1",
            topic = "Top 5 AI Tools That Will Replace Programmers in 2026",
            niche = "Tech & AI",
            viralScore = 98,
            searchGrowth = "+420% this week",
            competition = "Low",
            estimatedRpm = 16.40,
            recommendedFormat = "YouTube Shorts (9:16)",
            hookIdea = "Stop learning syntax! In 2026, these 5 AI engines write entire Android apps from a single prompt.",
            viralTags = listOf("ai tools", "coding in 2026", "software engineering", "gemini 3", "tech automation"),
            targetAudience = "Tech enthusiasts, aspiring developers, productivity seekers"
        ),
        ViralTrend(
            id = "trend-2",
            topic = "The 24-Hour YouTube Automation Cash Flow Blueprint",
            niche = "Finance & Wealth",
            viralScore = 95,
            searchGrowth = "+310% 24h",
            competition = "Medium",
            estimatedRpm = 24.80,
            recommendedFormat = "Long-form (16:9)",
            hookIdea = "I let 50 AI models run a YouTube channel with zero human intervention for 30 days. Here is the exact bank statement.",
            viralTags = listOf("youtube automation", "faceless channel", "passive income 2026", "make money online", "ai business"),
            targetAudience = "Side hustlers, entrepreneurs, digital nomads"
        ),
        ViralTrend(
            id = "trend-3",
            topic = "Quantum Computing Breakthrough: Why RSA Encryption is Broken",
            niche = "Science & Future",
            viralScore = 92,
            searchGrowth = "+280% 48h",
            competition = "Low",
            estimatedRpm = 19.20,
            recommendedFormat = "Long-form (16:9)",
            hookIdea = "Every password on Earth might become useless by next year. Here is what scientists just discovered.",
            viralTags = listOf("quantum computing", "cybersecurity", "tech news", "future technology", "cryptography"),
            targetAudience = "Science lovers, tech executives, cybersecurity enthusiasts"
        ),
        ViralTrend(
            id = "trend-4",
            topic = "3 Psychology Tricks to Make Anyone Agree With You",
            niche = "Shorts / Motivation",
            viralScore = 96,
            searchGrowth = "+540% this week",
            competition = "Medium",
            estimatedRpm = 6.80,
            recommendedFormat = "YouTube Shorts (9:16)",
            hookIdea = "FBI interrogators use this 3-second pause trick to get anyone to spill the truth. Don't abuse this.",
            viralTags = listOf("dark psychology", "body language", "influence tricks", "viral shorts", "mind hacks"),
            targetAudience = "General public, young adults, self-improvement seekers"
        ),
        ViralTrend(
            id = "trend-5",
            topic = "Secret High-Yield Savings Accounts Paying 9.2% APY",
            niche = "Personal Finance",
            viralScore = 94,
            searchGrowth = "+390% 24h",
            competition = "Low",
            estimatedRpm = 32.50,
            recommendedFormat = "Long-form (16:9)",
            hookIdea = "Your traditional bank is stealing $4,000 from you every year in lost interest. Move your money here instead.",
            viralTags = listOf("high yield savings", "interest rates", "investing 2026", "passive dividends", "personal finance"),
            targetAudience = "Investors, savers, working professionals"
        ),
        ViralTrend(
            id = "trend-6",
            topic = "The Unreal Engine 5.6 Hyper-Realistic Horror Game Drama",
            niche = "Gaming",
            viralScore = 89,
            searchGrowth = "+190% 24h",
            competition = "High",
            estimatedRpm = 5.60,
            recommendedFormat = "YouTube Shorts (9:16)",
            hookIdea = "Gamers thought this trailer was real bodycam footage. The developers had to prove it was CGI.",
            viralTags = listOf("unreal engine 5", "gaming news", "horror games 2026", "bodycam game", "pc gaming"),
            targetAudience = "Gamers, stream watchers, 3D graphics fans"
        ),
        ViralTrend(
            id = "trend-7",
            topic = "The Unsolved Cyber Heist of $800M in 14 Seconds",
            niche = "True Crime & Tech",
            viralScore = 91,
            searchGrowth = "+240% this week",
            competition = "Low",
            estimatedRpm = 14.20,
            recommendedFormat = "Long-form (16:9)",
            hookIdea = "In 2026, an anonymous hacker drained three European banks without triggering a single server alarm. Here is how.",
            viralTags = listOf("true crime", "cyber heist", "documentary", "mystery", "hacker story"),
            targetAudience = "Documentary fans, mystery lovers, tech enthusiasts"
        )
    )

    fun getTrendById(id: String): ViralTrend? {
        return trendingTopics.find { it.id == id }
    }
}
