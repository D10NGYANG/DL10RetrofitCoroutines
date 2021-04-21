package com.dlong.retrofitcoroutines.api

import com.dlong.dl10retrofitcoroutineslib.DLResponse
import com.dlong.retrofitcoroutines.bean.SearchUserResult
import okhttp3.RequestBody
import retrofit2.http.*

interface MainApi {

    @GET("searchUser")
    suspend fun searchUser(
        @Query("username") phone: String
    ): DLResponse<SearchUserResult>

    /**
     * 登陆
     * @param body RequestBody
     * @return DLResponse<String>
     */
    @Headers("Content-Type: application/json")
    @POST("/auth/appLogin")
    suspend fun appLogin(
        @Body body: RequestBody
    ): DLResponse<String>
}