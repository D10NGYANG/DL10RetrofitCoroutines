package com.dlong.dl10retrofitcoroutineslib

import android.util.Log
import com.d10ng.datelib.curTime
import com.d10ng.datelib.toDateStr
import com.squareup.moshi.Moshi
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 执行
 * @Author: D10NG
 * @Time: 2021/1/19 1:44 下午
 */
internal class DLResponseCall<S : Any?> constructor(
    private val delegate: Call<S>
) : Call<DLResponse<S>> {

    private val TAG = "DLResponseCall"

    override fun clone(): Call<DLResponse<S>> = DLResponseCall(delegate.clone())

    override fun execute(): Response<DLResponse<S>> {
        throw UnsupportedOperationException("DLResponseCall doesn't support execute")
    }

    override fun enqueue(callback: Callback<DLResponse<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {

                val body = response.body()
                val code = response.code()
                val errorBody = response.errorBody()?.string()

                val moshiBuilder = Moshi.Builder().build()
                val adapter = moshiBuilder.adapter(DLResponse.Error::class.java)
                val error: DLResponse.Error = try {
                    adapter.fromJson(errorBody)!!
                } catch (e: Exception) {
                    DLResponse.Error(e.toString(), code, curTime.toDateStr())
                }

                if (response.isSuccessful) {
                    callback.onResponse(
                        this@DLResponseCall,
                        Response.success(DLResponse(body, code, error))
                    )
                    if (body == null) {
                        Log.e(TAG, "Response is successful but the body is null")
                    }
                } else {
                    callback.onResponse(
                        this@DLResponseCall,
                        Response.success(DLResponse(null, code, error))
                    )
                    Log.e(TAG, "Response is failed, $code, $error")
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                callback.onResponse(
                    this@DLResponseCall,
                    Response.success(DLResponse(null, -1, DLResponse.Error(message = throwable.toString())))
                )
                Log.e(TAG, "Response is failed, $throwable")
            }
        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}