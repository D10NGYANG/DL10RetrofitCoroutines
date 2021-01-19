package com.dlong.retrofitcoroutines.api

import com.dlong.dl10retrofitcoroutineslib.DLResponse
import com.dlong.retrofitcoroutines.bean.SearchUserResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {

    @GET("searchUser")
    suspend fun searchUser(
        @Query("username") phone: String
    ): DLResponse<SearchUserResult>
}