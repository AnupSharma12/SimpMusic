package com.anup.auvro.viewModel

import androidx.lifecycle.viewModelScope
import com.anup.common.Config
import com.anup.common.Config.REMOVED_SONG_DATE_TIME
import com.anup.domain.data.entities.ArtistEntity
import com.anup.domain.data.entities.SongEntity
import com.anup.domain.mediaservice.handler.PlaylistType
import com.anup.domain.mediaservice.handler.QueueData
import com.anup.domain.repository.ArtistRepository
import com.anup.domain.repository.SongRepository
import com.anup.domain.utils.toArrayListTrack
import com.anup.domain.utils.toTrack
import com.anup.auvro.ui.screen.library.LibraryDynamicPlaylistType
import com.anup.auvro.viewModel.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import auvro.composeapp.generated.resources.Res
import auvro.composeapp.generated.resources.playlist

class LibraryDynamicPlaylistViewModel(
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
) : BaseViewModel() {
    private val _listFavoriteSong: MutableStateFlow<List<SongEntity>> = MutableStateFlow(emptyList())
    val listFavoriteSong: StateFlow<List<SongEntity>> get() = _listFavoriteSong

    private val _listFollowedArtist: MutableStateFlow<List<ArtistEntity>> = MutableStateFlow(emptyList())
    val listFollowedArtist: StateFlow<List<ArtistEntity>> get() = _listFollowedArtist

    private val _listMostPlayedSong: MutableStateFlow<List<SongEntity>> = MutableStateFlow(emptyList())
    val listMostPlayedSong: StateFlow<List<SongEntity>> get() = _listMostPlayedSong

    private val _listDownloadedSong: MutableStateFlow<List<SongEntity>> = MutableStateFlow(emptyList())
    val listDownloadedSong: StateFlow<List<SongEntity>> get() = _listDownloadedSong

    init {
        getFavoriteSong()
        getFollowedArtist()
        getMostPlayedSong()
        getDownloadedSong()
    }

    private fun getFavoriteSong() {
        viewModelScope.launch {
            songRepository.getLikedSongs().collectLatest { likedSong ->
                _listFavoriteSong.value =
                    likedSong.sortedByDescending {
                        it.favoriteAt ?: REMOVED_SONG_DATE_TIME
                    }
            }
        }
    }

    private fun getFollowedArtist() {
        viewModelScope.launch {
            artistRepository.getFollowedArtists().collectLatest { followedArtist ->
                _listFollowedArtist.value =
                    followedArtist.sortedByDescending {
                        it.followedAt ?: REMOVED_SONG_DATE_TIME
                    }
            }
        }
    }

    private fun getMostPlayedSong() {
        viewModelScope.launch {
            songRepository.getMostPlayedSongs().collectLatest { mostPlayedSong ->
                _listMostPlayedSong.value = mostPlayedSong.sortedByDescending { it.totalPlayTime }
            }
        }
    }

    private fun getDownloadedSong() {
        viewModelScope.launch {
            songRepository.getDownloadedSongs().collectLatest { downloadedSong ->
                _listDownloadedSong.value =
                    (downloadedSong ?: emptyList()).sortedByDescending {
                        it.downloadedAt ?: REMOVED_SONG_DATE_TIME
                    }
            }
        }
    }

    fun playSong(
        videoId: String,
        type: LibraryDynamicPlaylistType,
    ) {
        val (targetList, playTrack) =
            when (type) {
                LibraryDynamicPlaylistType.Favorite -> listFavoriteSong.value to listFavoriteSong.value.find { it.videoId == videoId }
                LibraryDynamicPlaylistType.Downloaded -> listDownloadedSong.value to listDownloadedSong.value.find { it.videoId == videoId }
                LibraryDynamicPlaylistType.Followed -> return
                LibraryDynamicPlaylistType.MostPlayed -> listMostPlayedSong.value to listMostPlayedSong.value.find { it.videoId == videoId }
                else -> return
            }
        if (playTrack == null) return
        setQueueData(
            QueueData.Data(
                listTracks = targetList.toArrayListTrack(),
                firstPlayedTrack = playTrack.toTrack(),
                playlistId = null,
                playlistName = "${
                    getString(
                        Res.string.playlist,
                    )
                } ${getString(type.name())}",
                playlistType = PlaylistType.RADIO,
                continuation = null,
            ),
        )
        loadMediaItem(
            playTrack.toTrack(),
            Config.PLAYLIST_CLICK,
            targetList.indexOf(playTrack).coerceAtLeast(0),
        )
    }

    private fun getSongList(type: LibraryDynamicPlaylistType): List<SongEntity> =
        when (type) {
            LibraryDynamicPlaylistType.Favorite -> listFavoriteSong.value
            LibraryDynamicPlaylistType.Downloaded -> listDownloadedSong.value
            LibraryDynamicPlaylistType.MostPlayed -> listMostPlayedSong.value
            else -> emptyList()
        }

    fun playAll(type: LibraryDynamicPlaylistType) {
        val targetList = getSongList(type)
        val firstTrack = targetList.firstOrNull() ?: return
        setQueueData(
            QueueData.Data(
                listTracks = targetList.toArrayListTrack(),
                firstPlayedTrack = firstTrack.toTrack(),
                playlistId = null,
                playlistName = "${getString(Res.string.playlist)} ${getString(type.name())}",
                playlistType = PlaylistType.RADIO,
                continuation = null,
            ),
        )
        loadMediaItem(
            firstTrack.toTrack(),
            Config.PLAYLIST_CLICK,
            0,
        )
    }

    fun shuffle(type: LibraryDynamicPlaylistType) {
        val targetList = getSongList(type)
        if (targetList.isEmpty()) return
        val shuffledList = targetList.shuffled()
        val firstTrack = shuffledList.first()
        setQueueData(
            QueueData.Data(
                listTracks = shuffledList.toArrayListTrack(),
                firstPlayedTrack = firstTrack.toTrack(),
                playlistId = null,
                playlistName = "${getString(Res.string.playlist)} ${getString(type.name())}",
                playlistType = PlaylistType.RADIO,
                continuation = null,
            ),
        )
        loadMediaItem(
            firstTrack.toTrack(),
            Config.PLAYLIST_CLICK,
            0,
        )
    }
}