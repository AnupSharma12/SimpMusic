package com.anup.auvro.ui.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.anup.auvro.expect.ui.PlatformBackdrop
import com.anup.auvro.viewModel.SharedViewModel
import kotlin.reflect.KClass

@Composable
actual fun LiquidGlassAppBottomNavigationBar(
    startDestination: Any,
    navController: NavController,
    backdrop: PlatformBackdrop,
    viewModel: SharedViewModel,
    isScrolledToTop: Boolean,
    onOpenNowPlaying: () -> Unit,
    reloadDestinationIfNeeded: (KClass<*>) -> Unit
) {
}