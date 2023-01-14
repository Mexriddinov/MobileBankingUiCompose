package com.gita.mobilebankinguicompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.gita.mobilebankinguicompose.navigation.NavigationHandler
import com.gita.mobilebankinguicompose.screen.splash.SplashScreen
import com.gita.mobilebankinguicompose.ui.theme.MobileBankingUiComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    @Inject
    lateinit var navigationHandler:NavigationHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileBankingUiComposeTheme() {
                // A surface container using the 'background' color from the theme
                Navigator(SplashScreen()) { navigator ->
                    LaunchedEffect(key1 = navigator) {
                        navigationHandler.navigationFlow
                            .onEach { it(navigator) }.collect()
                    }
                    CurrentScreen()
                }
            }
        }
    }
}