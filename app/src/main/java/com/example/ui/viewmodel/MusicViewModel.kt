package com.example.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.ChimalaEvent
import com.example.data.MediaItem
import com.example.data.MusicRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab {
    EXPLORE,   // Nyimbo & Video
    EVENTS,    // Matukio Chimala & Habari
    UPLOAD     // Weka Nyimbo/Video/Matukio
}

enum class MediaFilter {
    ALL,
    SONGS,
    VIDEOS
}

enum class EventFilter {
    ALL,
    CONCERTS,
    NEWS
}

class MusicViewModel(private val repository: MusicRepository) : ViewModel() {

    // Initialize database items if empty
    init {
        viewModelScope.launch {
            repository.prepopulateIfEmpty()
        }
    }

    // Active Screen Tab
    private val _currentTab = MutableStateFlow(AppTab.EXPLORE)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
    }

    // Filters & Search
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _mediaFilter = MutableStateFlow(MediaFilter.ALL)
    val mediaFilter: StateFlow<MediaFilter> = _mediaFilter.asStateFlow()

    private val _eventFilter = MutableStateFlow(EventFilter.ALL)
    val eventFilter: StateFlow<EventFilter> = _eventFilter.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setMediaFilter(filter: MediaFilter) {
        _mediaFilter.value = filter
    }

    fun setEventFilter(filter: EventFilter) {
        _eventFilter.value = filter
    }

    // Raw Streams from Room
    private val _allMedia = repository.allMediaItems
    private val _allEvents = repository.allEvents

    // Filtered / Searched lists for presentation
    val filteredMedia: StateFlow<List<MediaItem>> = combine(
        _allMedia,
        _searchQuery,
        _mediaFilter
    ) { media, search, filter ->
        var list = media
        // Search filter
        if (search.isNotBlank()) {
            list = list.filter {
                it.title.contains(search, ignoreCase = true) ||
                it.artist.contains(search, ignoreCase = true) ||
                it.category.contains(search, ignoreCase = true)
            }
        }
        // Category filter
        when (filter) {
            MediaFilter.SONGS -> list = list.filter { it.type == "SONG" }
            MediaFilter.VIDEOS -> list = list.filter { it.type == "VIDEO" }
            MediaFilter.ALL -> { /* no-op */ }
        }
        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredEvents: StateFlow<List<ChimalaEvent>> = combine(
        _allEvents,
        _searchQuery,
        _eventFilter
    ) { events, search, filter ->
        var list = events
        // Search filter
        if (search.isNotBlank()) {
            list = list.filter {
                it.title.contains(search, ignoreCase = true) ||
                it.description.contains(search, ignoreCase = true) ||
                it.location.contains(search, ignoreCase = true) ||
                it.organizer.contains(search, ignoreCase = true)
            }
        }
        // Sub-type filter
        when (filter) {
            EventFilter.CONCERTS -> list = list.filter { it.type == "EVENT" }
            EventFilter.NEWS -> list = list.filter { it.type == "NEWS" }
            EventFilter.ALL -> { /* no-op */ }
        }
        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    // --- SIMULATED MEDIA PLAYER STATE ---
    private val _currentPlayingMedia = MutableStateFlow<MediaItem?>(null)
    val currentPlayingMedia: StateFlow<MediaItem?> = _currentPlayingMedia.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _playbackProgress = MutableStateFlow(0f) // 0.0 to 1.0
    val playbackProgress: StateFlow<Float> = _playbackProgress.asStateFlow()

    private val _playbackSeconds = MutableStateFlow(0)
    val playbackSeconds: StateFlow<Int> = _playbackSeconds.asStateFlow()

    private val _isMuted = MutableStateFlow(false)
    val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()

    private var playerJob: Job? = null

    fun toggleMute() {
        _isMuted.value = !_isMuted.value
    }

    fun selectMedia(item: MediaItem) {
        viewModelScope.launch {
            // Update views count in repository
            repository.viewMedia(item.id)
            // If selecting a video, we set it as current, simulate playback
            _currentPlayingMedia.value = item
            _isPlaying.value = true
            _playbackProgress.value = 0f
            _playbackSeconds.value = 0
            startPlayerSimulation(item)
        }
    }

    fun togglePlayPause() {
        if (_currentPlayingMedia.value == null) return
        _isPlaying.value = !_isPlaying.value
        if (_isPlaying.value) {
            _currentPlayingMedia.value?.let { startPlayerSimulation(it) }
        } else {
            playerJob?.cancel()
        }
    }

    fun seekTo(progress: Float) {
        _playbackProgress.value = progress.coerceIn(0f, 1f)
        val durationSecs = getDurationInSeconds(_currentPlayingMedia.value?.duration ?: "3:00")
        _playbackSeconds.value = (progress * durationSecs).toInt()
    }

    private fun startPlayerSimulation(item: MediaItem) {
        playerJob?.cancel()
        val totalSeconds = getDurationInSeconds(item.duration)
        playerJob = viewModelScope.launch {
            while (_isPlaying.value && _playbackSeconds.value < totalSeconds) {
                delay(1000)
                _playbackSeconds.value += 1
                _playbackProgress.value = _playbackSeconds.value.toFloat() / totalSeconds.toFloat()
            }
            if (_playbackSeconds.value >= totalSeconds) {
                // Done playing! Play next or stop
                _isPlaying.value = false
                _playbackProgress.value = 0f
                _playbackSeconds.value = 0
            }
        }
    }

    private fun getDurationInSeconds(durationStr: String): Int {
        return try {
            val parts = durationStr.split(":")
            if (parts.size == 2) {
                parts[0].toInt() * 60 + parts[1].toInt()
            } else {
                180 // default 3 minutes
            }
        } catch (e: Exception) {
            180
        }
    }

    fun formatSeconds(seconds: Int): String {
        val mins = seconds / 60
        val secs = seconds % 60
        return String.format("%01d:%02d", mins, secs)
    }

    // --- WRITE ACTIONS (Room) ---

    fun likeMediaItem(item: MediaItem) {
        viewModelScope.launch {
            repository.likeMedia(item.id)
            // Update current playing likes if it's the one liked
            if (_currentPlayingMedia.value?.id == item.id) {
                _currentPlayingMedia.value = _currentPlayingMedia.value?.copy(likes = _currentPlayingMedia.value!!.likes + 1)
            }
        }
    }

    fun likeEventItem(event: ChimalaEvent) {
        viewModelScope.launch {
            repository.likeEvent(event.id)
        }
    }

    fun insertUploadedMedia(
        title: String,
        artist: String,
        type: String, // "SONG" or "VIDEO"
        category: String,
        duration: String,
        lyrics: String
    ) {
        viewModelScope.launch {
            val newItem = MediaItem(
                title = title.trim(),
                artist = artist.trim().ifEmpty { "Wasanii Mbalimbali" },
                type = type,
                category = category.trim().ifEmpty { "Bongo Flava" },
                duration = duration.trim().ifEmpty { "3:20" },
                lyrics = lyrics.trim()
            )
            repository.insertMedia(newItem)
        }
    }

    fun insertUploadedEvent(
        title: String,
        description: String,
        date: String,
        location: String,
        price: String,
        type: String, // "EVENT" or "NEWS"
        organizer: String
    ) {
        viewModelScope.launch {
            val newEvent = ChimalaEvent(
                title = title.trim(),
                description = description.trim(),
                date = date.trim().ifEmpty { "Juni, 2026" },
                location = location.trim().ifEmpty { "Chimala Mbeya" },
                price = price.trim().ifEmpty { "Bure" },
                type = type,
                organizer = organizer.trim().ifEmpty { "Kikundi Chimala" }
            )
            repository.insertEvent(newEvent)
        }
    }

    fun deleteMediaItem(item: MediaItem) {
        viewModelScope.launch {
            repository.deleteMedia(item)
            if (_currentPlayingMedia.value?.id == item.id) {
                _currentPlayingMedia.value = null
                _isPlaying.value = false
                playerJob?.cancel()
            }
        }
    }

    fun deleteEventItem(event: ChimalaEvent) {
        viewModelScope.launch {
            repository.deleteEvent(event)
        }
    }

    override fun onCleared() {
        super.onCleared()
        playerJob?.cancel()
    }
}

class MusicViewModelFactory(private val repository: MusicRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MusicViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MusicViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
