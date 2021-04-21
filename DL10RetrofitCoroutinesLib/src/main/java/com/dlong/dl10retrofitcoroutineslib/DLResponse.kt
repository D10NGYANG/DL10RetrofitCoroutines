package com.dlong.dl10retrofitcoroutineslib

/**
 * 请求结果
 * @Author: D10NG
 * @Time: 2021/1/19 10:03 上午
 */
data class DLResponse<T : Any?>(
    val body: T?,
    val code: Int,
    val errorBody: String?
)