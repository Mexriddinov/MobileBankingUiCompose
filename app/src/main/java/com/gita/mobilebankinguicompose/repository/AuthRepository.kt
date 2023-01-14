package com.gita.mobilebankinguicompose.repository

import com.gita.mobilebankinguicompose.data.remote.request.login.LoginRequest
import com.gita.mobilebankinguicompose.data.remote.request.register.SignUpRequest
import com.gita.mobilebankinguicompose.data.remote.request.resend_verify.ResendVerifyRequestAndResponse
import com.gita.mobilebankinguicompose.data.remote.response.login.LoginResponse
import com.gita.mobilebankinguicompose.data.remote.response.register.SignUpSuccessResponse
import com.gita.mobilebankinguicompose.utils.ResultData
import kotlinx.coroutines.flow.Flow
import com.gita.mobilebankinguicompose.data.remote.response.verify.VerifyResponse

interface AuthRepository {
    fun login(request: LoginRequest):Flow<ResultData<LoginResponse>>
    fun signInVerify(token:String):Flow<ResultData<VerifyResponse>>
    fun signUpVerify(token:String):Flow<ResultData<VerifyResponse>>
    fun signUp(verifyRequest: SignUpRequest):Flow<ResultData<SignUpSuccessResponse>>
    fun signUpResend(): Flow<ResultData<ResendVerifyRequestAndResponse>>
    fun signInResend(): Flow<ResultData<ResendVerifyRequestAndResponse>>
}