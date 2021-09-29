package com.dlong.dl10retrofitcoroutineslib

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json
import java.io.Serializable


/**
 * 请求结果
 * @Author: D10NG
 * @Time: 2021/1/19 10:03 上午
 */
data class DLResponse<T : Any?>(
    val body: T?,
    val code: Int,
    val errorBody: Error
) {
    @JsonClass(generateAdapter = true)
    data class Error(
        @Json(name = "message")
        var message: String = "",
        @Json(name = "status")
        var status: Int = 0,
        @Json(name = "timestamp")
        var timestamp: String = ""
    ): Serializable
}