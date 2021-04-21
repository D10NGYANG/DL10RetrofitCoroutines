package com.dlong.retrofitcoroutines

import com.dlong.dl10retrofitcoroutineslib.DLResponseAdapterFactory
import com.dlong.retrofitcoroutines.api.MainApi
import com.dlong.retrofitcoroutines.api.MainApiWorker
import com.dlong.retrofitcoroutines.bean.BaseResult1Adapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * HTTP请求 主入口 管理类
 * Author: D10NG
 * Time: 11/26/20 4:30 PM
 */

/** 接口域名 */
private const val HL_BASE_URL = "https://xxx/"

class HLHttpManager {

    companion object {
        @Volatile
        private var INSTANCE: HLHttpManager? = null

        @JvmStatic
        fun instance() =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: HLHttpManager().also {
                        INSTANCE = it
                    }
                }
    }

    /** 网络请求工具 */
    private var retrofit: Retrofit? = null

    val mainApi: MainApi by lazy {
        retrofit!!.create(MainApi::class.java)
    }

    val mainApiWorker: MainApiWorker by lazy {
        MainApiWorker(retrofit!!.create(MainApi::class.java))
    }

    /**
     * 初始化管理器，必须先初始化才能正常使用
     * @param isDebug Boolean 是否打开打印，true：开启打印；false：关闭打印；默认值：false
     * @param baseUrl String 基础域名
     * @param connectTimeout Long 连接超时时间，单位秒，默认值：10
     * @param readTimeout Long 读取超时时间，单位秒，默认值：10
     * @param writeTimeout Long 写入超时时间，单位秒，默认值：10
     */
    fun initManager(isDebug: Boolean = false, baseUrl: String = HL_BASE_URL,
                    connectTimeout: Long = 10L, readTimeout: Long = 10L, writeTimeout: Long = 10L) {
        // 初始化接收数据格式化工具
        val moshi = Moshi.Builder()
            .add(BaseResult1Adapter())
            .add(KotlinJsonAdapterFactory())
            .build()
        // 初始化拦截器
        val logInterceptor = HttpLoggingInterceptor()
        if (isDebug) {
            // 设置拦截器级别 全打
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            // 设置拦截器级别 无
            logInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        // 初始化网络请求客户端
        val client = OkHttpClient.Builder()
            // 连接超时
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            // 读取超时
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            // 写入超时
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            // 设置拦截器
            .addInterceptor(logInterceptor)
            .build()
        // 初始化网络请求工具
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(DLResponseAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }
}