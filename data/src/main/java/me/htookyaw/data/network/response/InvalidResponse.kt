package me.htookyaw.data.network.response

import com.google.gson.annotations.SerializedName

data class InvalidResponse(
    @SerializedName("status_message")
    val statusMessage: String,
    @SerializedName("status_code")
    val statusCode: Int
)
