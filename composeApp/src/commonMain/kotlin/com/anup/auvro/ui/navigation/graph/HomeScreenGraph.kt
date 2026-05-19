package com.anup.auvro.ui.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.anup.auvro.ui.navigation.destination.home.AnalyticsDestination
import com.anup.auvro.ui.navigation.destination.home.CreditDestination
import com.anup.auvro.ui.navigation.destination.home.MoodDestination
import com.anup.auvro.ui.navigation.destination.home.NotificationDestination
import com.anup.auvro.ui.navigation.destination.home.RecentlySongsDestination
import com.anup.auvro.ui.navigation.destination.home.SettingsDestination
import com.anup.auvro.ui.screen.home.MoodScreen
import com.anup.auvro.ui.screen.home.NotificationScreen
import com.anup.auvro.ui.screen.home.RecentlySongsScreen
import com.anup.auvro.ui.screen.home.SettingScreen
import com.anup.auvro.ui.screen.home.analytics.AnalyticsScreen
import com.anup.auvro.ui.screen.other.CreditScreen

fun NavGraphBuilder.homeScreenGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<CreditDestination> {
        CreditScreen(
            paddingValues = innerPadding,
            navController = navController,
        )
    }
    composable<MoodDestination> { entry ->
        val params = entry.toRoute<MoodDestination>().params
        MoodScreen(
            navController = navController,
            params = params,
        )
    }
    composable<NotificationDestination> {
        NotificationScreen(
            navController = navController,
        )
    }
    composable<RecentlySongsDestination> {
        RecentlySongsScreen(
            navController = navController,
            innerPadding = innerPadding,
        )
    }
    composable<SettingsDestination> {
        SettingScreen(
            navController = navController,
            innerPadding = innerPadding,
        )
    }
    composable<AnalyticsDestination> {
        AnalyticsScreen(
            navController = navController,
            innerPadding = innerPadding,
        )
    }
}