package com.dlong.dl10retrofitcoroutineslib

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class DLResponseAdapter<S : Any> constructor(
    private val successType: Type
): CallAdapter<S, Call<DLResponse<S>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<DLResponse<S>> {
        return DLResponseCall(call)
    }
}