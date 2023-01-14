package com.gita.mobilebankinguicompose.screen.login.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.screen.Screen
import com.gita.mobilebankinguicompose.data.remote.request.login.LoginRequest
import com.gita.mobilebankinguicompose.navigation.AppNavigator
import com.gita.mobilebankinguicompose.repository.AuthRepository
import com.gita.mobilebankinguicompose.screen.register.RegisterScreen
import com.gita.mobilebankinguicompose.screen.verify.VerifyScreen
import com.gita.mobilebankinguicompose.utils.SignType
import com.gita.mobilebankinguicompose.utils.VerifyUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModelImpl @Inject constructor(
    private val repository: AuthRepository,
    private val appNavigator: AppNavigator
) : LoginContract.LoginViewModel, ScreenModel {
    override val uiState = MutableStateFlow(
        LoginContract.UiState(
            phone = "", password = "",
            errorMessage = "",
            progress = false
        )
    )

    override fun onEventDispatcher(intent: LoginContract.LoginIntent) {
        when (intent) {
            LoginContract.LoginIntent.ClickLogin -> {
                   repository.login(LoginRequest(uiState.value.password,"+998"+uiState.value.phone)).onEach {
                     reduce {
                         it.copy(progress = false)
                     }
                       it.onSuccess {
                           println("Success Login")
                           open(VerifyScreen()) }
                       it.onError { t->
                           reduce {
                               it.copy(errorMessage = t.message.toString(), progress = true)
                           }

                       }

                   }.launchIn(coroutineScope)
              }
            LoginContract.LoginIntent.CreateAccount -> {
                open(RegisterScreen())
            }
            is LoginContract.LoginIntent.PasswordEnter -> {
                reduce {
                    it.copy(password = intent.password, progress = false)
                }
            }
            is LoginContract.LoginIntent.PhoneEnter -> {
                reduce {
                    it.copy(phone = intent.phone,progress = false)
                }
            }
        }
    }

    private fun open(screen: Screen) {
        coroutineScope.launch {
            VerifyUtil.signType=SignType.SIGN_IN
            appNavigator.navigationTo(screen)
        }
    }


    private fun reduce(block: (oldState: LoginContract.UiState) -> LoginContract.UiState) {
        val old = uiState.value
        uiState.value = block(old)
    }

}