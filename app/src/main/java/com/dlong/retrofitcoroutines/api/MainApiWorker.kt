package com.dlong.retrofitcoroutines.api

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class MainApiWorker constructor(
    val api: MainApi
) {

    /**
     * 登陆
     * @param context Context
     * @param username String
     * @param password String
     * @param imei String?
     * @return AppLoginResult
     */
    @SuppressLint("HardwareIds")
    suspend fun appLogin(context: Context, username: String, password: String, imei: String?): String {
        val model = Build.MODEL
        val jsonObj = JSONObject().apply {
            put("username", username)
            put("password", password)
            put("model", model)
            put("uuid", imei)
        }
        val body = jsonObj.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val result = api.appLogin(body)
        Log.e("测试", "result = $result")
        return result.body?: ""
    }
}