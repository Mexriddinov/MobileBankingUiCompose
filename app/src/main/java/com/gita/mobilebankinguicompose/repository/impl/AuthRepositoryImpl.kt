package com.gita.mobilebankinguicompose.repository.impl

import com.gita.mobilebankinguicompose.data.local.MySharedPref
import com.gita.mobilebankinguicompose.data.remote.api.AuthApi
import com.gita.mobilebankinguicompose.data.remote.request.login.LoginRequest
import com.gita.mobilebankinguicompose.data.remote.request.register.SignUpRequest
import com.gita.mobilebankinguicompose.data.remote.request.resend_verify.ResendVerifyRequestAndResponse
import com.gita.mobilebankinguicompose.data.remote.request.verify.SignInVerifyRequest
import com.gita.mobilebankinguicompose.data.remote.request.verify.SignUpVerifyRequest
import com.gita.mobilebankinguicompose.data.remote.response.login.LoginResponse
import com.gita.mobilebankinguicompose.data.remote.response.register.SignUpSuccessResponse
import com.gita.mobilebankinguicompose.repository.AuthRepository
import com.gita.mobilebankinguicompose.utils.ResultData
import com.gita.mobilebankinguicompose.utils.func
import kotlinx.coroutines.flow.flow
import com.gita.mobilebankinguicompose.data.remote.response.verify.VerifyResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(var sharedPref: MySharedPref, var authApi: AuthApi) :
    AuthRepository {
    override fun login(request: LoginRequest) = flow<ResultData<LoginResponse>> {
        val authResponse = authApi.signIn(request).func()
        emit(authResponse)
        authResponse.onSuccess {
            sharedPref.verifyToken = it.token
        }
    }


    override fun signInVerify(code: String) = flow<ResultData<VerifyResponse>> {
        val authResponse =
            authApi.signInVerify(SignInVerifyRequest(code = code, sharedPref.verifyToken)).func()
        emit(authResponse)
        authResponse.onSuccess {
            sharedPref.refreshToken = it.refreshToken
            sharedPref.token = it.accessToken
        }
    }

    override fun signUpVerify(code: String) = flow<ResultData<VerifyResponse>> {
        val authResponse =
            authApi.signUpVerify(SignUpVerifyRequest(code = code, sharedPref.verifyToken)).func()
        emit(authResponse)
        authResponse.onSuccess {
            sharedPref.refreshToken = it.refreshToken
            sharedPref.token = it.accessToken
        }
    }

    override fun signUpResend() = flow<ResultData<ResendVerifyRequestAndResponse>> {
        val authResponse =
            authApi.signUpVerifyResend(ResendVerifyRequestAndResponse(sharedPref.verifyToken))
                .func()
        emit(authResponse)
        authResponse.onSuccess {
            sharedPref.verifyToken = it.token

        }
    }

    override fun signInResend() = flow<ResultData<ResendVerifyRequestAndResponse>> {

        val authResponse =
            authApi.signInVerifyResend(ResendVerifyRequestAndResponse(sharedPref.verifyToken))
                .func()
        emit(authResponse)
        authResponse.onSuccess {
            sharedPref.verifyToken = it.token

        }
    }

    override fun signUp(verifyRequest: SignUpRequest) = flow<ResultData<SignUpSuccessResponse>> {
        val authResponse = authApi.signUp(verifyRequest).func()
        emit(authResponse)
        authResponse.onSuccess {
            sharedPref.verifyToken = it.token

        }
    }

}