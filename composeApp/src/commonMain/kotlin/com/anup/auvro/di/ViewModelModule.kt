package com.anup.auvro.di

import com.anup.auvro.viewModel.AlbumViewModel
import com.anup.auvro.viewModel.AnalyticsViewModel
import com.anup.auvro.viewModel.ArtistViewModel
import com.anup.auvro.viewModel.HomeViewModel
import com.anup.auvro.viewModel.LibraryDynamicPlaylistViewModel
import com.anup.auvro.viewModel.LibraryViewModel
import com.anup.auvro.viewModel.LocalPlaylistViewModel
import com.anup.auvro.viewModel.LogInViewModel
import com.anup.auvro.viewModel.MoodViewModel
import com.anup.auvro.viewModel.MoreAlbumsViewModel
import com.anup.auvro.viewModel.NotificationViewModel
import com.anup.auvro.viewModel.NowPlayingBottomSheetViewModel
import com.anup.auvro.viewModel.PlaylistViewModel
import com.anup.auvro.viewModel.PodcastViewModel
import com.anup.auvro.viewModel.RecentlySongsViewModel
import com.anup.auvro.viewModel.SearchViewModel
import com.anup.auvro.viewModel.SettingsViewModel
import com.anup.auvro.viewModel.SharedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =
    module {
        single {
            SharedViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        single {
            SearchViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            NowPlayingBottomSheetViewModel(
                get(),
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            LibraryViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            LibraryDynamicPlaylistViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            AlbumViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            HomeViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            SettingsViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            ArtistViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            PlaylistViewModel(
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            LogInViewModel(
                get(),
            )
        }
        viewModel {
            PodcastViewModel(
                get(),
            )
        }
        viewModel {
            MoreAlbumsViewModel(
                get(),
            )
        }
        viewModel {
            RecentlySongsViewModel(
                get(),
            )
        }
        viewModel {
            LocalPlaylistViewModel(
                get(),
                get(),
                get(),
            )
        }
        viewModel {
            NotificationViewModel(
                get(),
            )
        }
        viewModel {
            MoodViewModel(
                get(),
                get(),
            )
        }
        viewModel {
            AnalyticsViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
    }