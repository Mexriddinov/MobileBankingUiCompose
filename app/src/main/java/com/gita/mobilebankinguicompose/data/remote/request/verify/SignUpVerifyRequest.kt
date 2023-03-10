package com.gita.mobilebankinguicompose.data.remote.request.verify

import com.google.gson.annotations.SerializedName

data class SignUpVerifyRequest(

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("token")
	val token: String
)