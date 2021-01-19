package com.dlong.retrofitcoroutines.bean

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json
import java.io.Serializable

/**
 * 查找用户结果
 *
 * Author: D10NG
 * Time: 11/27/20 1:48 PM
 */
@JsonClass(generateAdapter = true)
data class SearchUserResult(

    @Json(name = "message")
    var message: String = "",

    @Json(name = "result")
    var result: Result = Result(),

    @Json(name = "status")
    var status: String = ""
): Serializable {

    @JsonClass(generateAdapter = true)
    data class Result(

        /** 头像地址 */
        @Json(name = "avatar")
        var avatarUrl: String = "",

        /** 用户昵称 */
        @Json(name = "name")
        var nick: String = "",

        /** 用户电话号码 */
        @Json(name = "username")
        var phone: String = ""
    )
}