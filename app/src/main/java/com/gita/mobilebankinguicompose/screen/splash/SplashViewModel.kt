package com.gita.mobilebankinguicompose.screen.splash

import com.gita.mobilebankinguicompose.utils.BaseViewModel


interface SplashViewModel : BaseViewModel<Unit, SplashUiState, Nothing>

data class SplashUiState(
    val isOpenLogin: Boolean = false,
    val isOPenMain: Boolean = false
)